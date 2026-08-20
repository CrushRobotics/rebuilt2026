// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.HootAutoReplay;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.TimedRobot;


public class Robot extends TimedRobot {
    private Command m_autonomousCommand;


    private static final double TARGET_ROTATIONS = 1.0;

    // private SparkMax m_robotContainer.intake;
    private RelativeEncoder encoder;
    private PIDController pidController;

    

    private final RobotContainer m_robotContainer;

    @Override
    public void robotInit() {
        SparkMax motor = m_robotContainer.intake;
        // motor.restoreFactoryDefaults();

        encoder = motor.getEncoder();


        encoder.setPosition(0);


        pidController = new PIDController(0.1, 0.0, 0.0);
        pidController.setTolerance(0.01);
    }


    /* log and replay timestamp and joystick data */
    private final HootAutoReplay m_timeAndJoystickReplay = new HootAutoReplay()
        .withTimestampReplay()
        .withJoystickReplay();

    private final boolean kUseLimelight = false;

    public Robot() {


                // cam start (backup: CameraServer.startAutomaticCapture();)
       if (RobotBase.isReal()) {
        // CameraServer.startAutomaticCapture(1);
        UsbCamera cam =  CameraServer.startAutomaticCapture();
        cam.setResolution(160, 120);
        cam.setFPS(80);
        
        // MjpegServer server = (MjpegServer) CameraServer.getServer();
        // server.setCompression(80); //mess with //.
       } 
        // .
        



        m_robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        m_timeAndJoystickReplay.update();
        CommandScheduler.getInstance().run();

        /*
         * This example of adding Limelight is very simple and may not be sufficient for on-field use.
         * Users typically need to provide a standard deviation that scales with the distance to target
         * and changes with number of tags available.
         *
         * This example is sufficient to show that vision integration is possible, though exact implementation
         * of how to use vision should be tuned per-robot and to the team's specification.
         */
        if (kUseLimelight) {
            var driveState = m_robotContainer.drivetrain.getState();
            double headingDeg = driveState.Pose.getRotation().getDegrees();
            double omegaRps = Units.radiansToRotations(driveState.Speeds.omegaRadiansPerSecond);

            LimelightHelpers.SetRobotOrientation("limelight", headingDeg, 0, 0, 0, 0, 0);
            var llMeasurement = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight");
            if (llMeasurement != null && llMeasurement.tagCount > 0 && Math.abs(omegaRps) < 2.0) {
                m_robotContainer.drivetrain.addVisionMeasurement(llMeasurement.pose, llMeasurement.timestampSeconds);
            }
        }
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void disabledExit() {}


    @Override
    public void teleopPeriodic() {
        // SparkMax motor = m_robotContainer.intake;
        // double output = pidController.calculate(encoder.getPosition(), TARGET_ROTATIONS);

        // output = Math.max(-0.5, Math.min(0.5, output));

        // // motor.set(output);

        // System.out.printf("Pos: %.2f, Output: %.2f%n", encoder.getPosition(), output);

    }

    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        if (RobotBase.isReal()) {
        SparkMax motor = m_robotContainer.intake;
        double output = pidController.calculate(encoder.getPosition(), TARGET_ROTATIONS);

        output = Math.max(-0.5, Math.min(0.5, output));

        // motor.set(output);

        System.out.printf("Pos: %.2f, Output: %.2f%n", encoder.getPosition(), output);



            motor.set(output);
        }

        if (m_autonomousCommand != null) {
            CommandScheduler.getInstance().schedule(m_autonomousCommand);
        }
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void autonomousExit() {}

    @Override
    public void teleopInit() {

        if(RobotBase.isReal()) {
        pidController.reset();
        }


        if (m_autonomousCommand != null) {
            CommandScheduler.getInstance().cancel(m_autonomousCommand);
        }
    }


    @Override
    public void teleopExit() {
        SparkMax motor = m_robotContainer.intake;
        motor.stopMotor();
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}

    @Override
    public void testExit() {}

    @Override
    public void simulationPeriodic() {}

}