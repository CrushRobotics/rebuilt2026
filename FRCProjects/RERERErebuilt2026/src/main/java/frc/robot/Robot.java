// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// import com.ctre.phoenix6.HootAutoReplay;
import com.ctre.phoenix6.Orchestra;
// import com.ctre.phoenix6.SignalLogger;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.TimedRobot;
// import com.ctre.phoenix6.HootAutoReplay;
import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.StatusCode;

public class Robot extends TimedRobot {









    private Command m_autonomousCommand;


    private static final double TARGET_ROTATIONS = -1.87;

    // private SparkMax m_robotContainer.intake;
    private RelativeEncoder encoder;
    private PIDController pidController;

    

    private final RobotContainer m_robotContainer;




    private static final int kFrontLeftDriveMotorId = 4;
    private static final int kFrontLeftSteerMotorId = 3;
    private static final int kFrontRightDriveMotorId = 6;
    private static final int kFrontRightSteerMotorId = 5;
    private static final int kBackLeftDriveMotorId = 8;
    private static final int kBackLeftSteerMotorId = 7;
    private static final int kBackRightDriveMotorId = 2;
    private static final int kBackRightSteerMotorId = 1;





Orchestra O = new Orchestra();

    @Override
    public void robotInit() {

            // SignalLogger.deleteAll(); //RIP pheonix 5 :sob:


// orchestra
    O = new Orchestra();

    TalonFX FLD;
    TalonFX FLS;
    TalonFX FRD;
    TalonFX FRS;
    TalonFX BLD;
    TalonFX BLS;
    TalonFX BRD;
    TalonFX BRS;


        FLD = new TalonFX(kFrontLeftDriveMotorId);
        FLS = new TalonFX(kFrontLeftSteerMotorId);
        FRD = new TalonFX(kFrontRightDriveMotorId);
        FRS = new TalonFX(kFrontRightSteerMotorId);
        BLD = new TalonFX(kBackLeftDriveMotorId);
        BLS = new TalonFX(kBackLeftSteerMotorId);
        BRD = new TalonFX(kBackRightDriveMotorId);
        BRS = new TalonFX(kBackRightSteerMotorId);
    
        O.addInstrument(FLD);
        O.addInstrument(FLS);
        O.addInstrument(FRD);
        O.addInstrument(FRS);
        O.addInstrument(BLD);
        O.addInstrument(BLS);
        O.addInstrument(BRD);
        O.addInstrument(BRS);

    

        ///home/lvuser/deploy/
        // 
       StatusCode status = O.loadMusic("thunderstruck.chrp");
       status = O.play();
       
if (!status.isOK()) {
    System.out.println("ORCHESTRA LOAD FAILED: " + status);
} else {
    System.out.println("ORCHESTRA MUSIC LOADED");
    O.play();
}

//orchestra







        SparkMax motor = m_robotContainer.intake;
        // motor.restoreFactoryDefaults(); //Figure out later | not fully needed i *think*

        encoder = motor.getEncoder();


        encoder.setPosition(0);


        pidController = new PIDController(0.1, 0.0, 0.0);
        pidController.setTolerance(0.01);
    }

    //reAdd for comp
        /* log and replay timestamp and joystick data */
        // private final HootAutoReplay m_timeAndJoystickReplay = new HootAutoReplay()
        // .withTimestampReplay()
        // .withJoystickReplay();

    private final boolean kUseLimelight = false;

    public Robot() {


        // cam start
       if (RobotBase.isReal()) {
        UsbCamera benjaminnetanyahu =  CameraServer.startAutomaticCapture();
        benjaminnetanyahu.setResolution(160, 120);
        benjaminnetanyahu.setFPS(80);
       } 
        // .
        



        m_robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        // m_timeAndJoystickReplay.update();
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

        // System.out.printf("Pos: %.2f, Output: %.2f%n", encoder.getPosition(), output);

    }



    private void stopintake() {
       SparkMax motor = m_robotContainer.intake;
        double output = pidController.calculate(encoder.getPosition(), TARGET_ROTATIONS);

        output = Math.max(-0.1, Math.min(0.1, output));

        motor.set(output);
    }

public void startintake() {
        SparkMax motor = m_robotContainer.intake;

        motor.set(0);
}

public void pain() {
    new SequentialCommandGroup(
    new InstantCommand(this::startintake),
    new WaitCommand(0.67), //Adjust tmr
    new InstantCommand(this::stopintake)
).schedule();

}


    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        if (RobotBase.isReal()) {
 
        pain();

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