// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//cam import
 import edu.wpi.first.cameraserver.CameraServer;
 import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoCamera;
import edu.wpi.first.cscore.MjpegServer;
//.

import org.opencv.video.Video;

import com.ctre.phoenix6.HootAutoReplay;
import com.fasterxml.jackson.annotation.JacksonInject.Value;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;

//auto
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.wpilibj.RobotBase;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

//.
public class Robot extends TimedRobot {

    private Command m_autonomousCommand;

    private final RobotContainer m_robotContainer;

    /* log and replay timestamp and joystick data */
    private final HootAutoReplay m_timeAndJoystickReplay = new HootAutoReplay()
        .withTimestampReplay()
        .withJoystickReplay();

    public Robot() {



        // only for sim

    // private CvSource simCamera;

    // @Override
    // public void robotInit() {
    //     if (RobotBase.isSimulation()) {
    //         simCamera = CameraServer.putVideo("Sim Camera", 640, 480);
    //     }
    // }

    // @Override
    // public void simulationPeriodic() {
    //     if (simCamera != null) {
    //         Mat image = new Mat(
    //             480,
    //             640,
    //             CvType.CV_8UC3,
    //             new Scalar(0, 255, 0)
    //         );

    //         simCamera.putFrame(image);
    //     }
    // }
        //.





        // cam start (backup: CameraServer.startAutomaticCapture();)
       if (RobotBase.isReal()) {
        CameraServer.startAutomaticCapture(1);
        UsbCamera cam =  CameraServer.startAutomaticCapture();
        cam.setResolution(160, 120);
        cam.setFPS(80);
        
        MjpegServer server = (MjpegServer) CameraServer.getServer();
        server.setCompression(80); //mess with //.
       } 
        // .
        
       m_robotContainer = new RobotContainer();
    }
    // public boolean emergencyStop = false;
    // private final SparkMax liftMotor = new SparkMax(20, MotorType.kBrushless);
    @Override
    public void robotPeriodic() {
        m_robotContainer.periodic();
        // SmartDashboard.putNumber("", emergencyStop);
        m_timeAndJoystickReplay.update();
        CommandScheduler.getInstance().run(); 
    SmartDashboard.putBoolean(
    "TripleT",
    m_robotContainer.getEmergencyStop()
    );
SmartDashboard.putNumber("current", m_robotContainer.getliftcurrent());
m_robotContainer.ret();  
 
 SmartDashboard.putString("current", "w");
}



    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void disabledExit() {}

    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();

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
        if (m_autonomousCommand != null) {
            m_robotContainer.halfsec();
            CommandScheduler.getInstance().cancel(m_autonomousCommand);
        }
    }
    
    // private final SparkMax liftMotor = new SparkMax(20, MotorType.kBrushless);
    // private final double kLoadThresholdAmps = 30.0; 

  private void halfsec() {
   m_robotContainer.halfsec();
  }


    @Override
    public void teleopPeriodic() {
    

//  SmartDashboard.putBoolean("TripleT", emergencyStop);
        

    }

    @Override
    public void teleopExit() {}

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
