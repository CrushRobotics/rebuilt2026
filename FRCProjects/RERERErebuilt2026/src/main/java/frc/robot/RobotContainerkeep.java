// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot;
// // import frc.robot.subsystems.slowsl;

// // import frc.robot.subsystems.PIDtest;
// import com.ctre.phoenix6.hardware.TalonFX;
// import com.ctre.phoenix6.Orchestra;
// import com.ctre.phoenix6.controls.DutyCycleOut;



// // //cam import
// //  import edu.wpi.first.cameraserver.CameraServer;
// //  import edu.wpi.first.cscore.UsbCamera;
// // import edu.wpi.first.cscore.VideoCamera;
// // import edu.wpi.first.cscore.MjpegServer;
// // //.
// import com.ctre.phoenix6.swerve.SwerveRequest;
// import edu.wpi.first.math.MathUtil;
// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Pose3d;

// import com.revrobotics.RelativeEncoder;
// import com.revrobotics.spark.SparkClosedLoopController;
// import com.revrobotics.spark.SparkMax;
// import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.revrobotics.spark.config.SparkMaxConfig;

// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.RobotBase;
// import edu.wpi.first.wpilibj.GenericHID.RumbleType;
// import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// import edu.wpi.first.wpilibj2.command.Command;


// import static edu.wpi.first.units.Units.*;

// import java.util.Arrays;
// import java.util.Collection;


// // import com.revrobotics.SparkMaxPIDController;

// import frc.robot.LimelightHelpers;
// import com.revrobotics.spark.SparkClosedLoopController;
// import com.revrobotics.spark.SparkBase.ControlType;
// // import com.revrobotics.spark.RelativeEncoder;
// import com.revrobotics.spark.config.SparkBaseConfig;
// import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
// import com.ctre.phoenix6.hardware.TalonFX;
// import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
// import com.ctre.phoenix6.swerve.SwerveRequest;

// import com.pathplanner.lib.auto.AutoBuilder;
// import com.pathplanner.lib.commands.FollowPathCommand;
// import com.pathplanner.lib.commands.PathPlannerAuto;

// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.CommandScheduler;
// import edu.wpi.first.wpilibj2.command.Commands;
// import edu.wpi.first.wpilibj2.command.InstantCommand;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import edu.wpi.first.wpilibj2.command.WaitCommand;
// import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
// import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
// import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

// import frc.robot.generated.TunerConstants;
// import frc.robot.subsystems.CommandSwerveDrivetrain;
// // import frc.robot.subsystems.PIDtest;
// import edu.wpi.first.wpilibj.GenericHID.RumbleType;
// import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

// public class RobotContainer {
   

//     // private final PIDtest PIDtest = new PIDtest();
//         // private final slowsl slowsl = new slowsl();


//     // private final slowsl exampleCommand = new slowsl();



	
//     // public PIDtest PIDtest() {
//     //     return PIDtest;
//     // }
//     // public slowsl slowsl() {
//     //     return slowsl;
//     // }
// 	// PIDtest PIDtest;









//     // private static final int kFrontLeftDriveMotorId = 4;
//     // private static final int kFrontLeftSteerMotorId = 3;
//     //     private static final int kFrontRightDriveMotorId = 6;
//     // private static final int kFrontRightSteerMotorId = 5;
//     // private static final int kBackLeftDriveMotorId = 8;
//     // private static final int kBackLeftSteerMotorId = 7;
//     //     private static final int kBackRightDriveMotorId = 2;
//     // private static final int kBackRightSteerMotorId = 1;





// // Collection<TalonFX> motors = Arrays.asList(motor1, motor2);
// // orchestra = new Orchestra(motors);


// // private TalonFX krakenMotor;

// // krakenMotor = new TalonFX(kBackLeftDriveMotorId);


// // Orchestra orchestra = new Orchestra();
// // orchestra.addInstrument(krakenMotor);








// // Pose3d target = LimelightHelpers.getTargetPose3d_RobotSpace("limelight");
// // public void rotateforclimb() {


// // double sidewaysError = target.getY();
// // double forw = target.getX();
// // double tx = LimelightHelpers.getTX("limelight");

// // double kPStrafe = 1.0;
// // double kPRotation = 0.5;
// // double kPForw = 1.0;

// // double desiredDistance = 0.6096; 

// // double strafe = MathUtil.clamp(
// //     -sidewaysError * kPStrafe,
// //     -1.5,
// //     1.5
// // );

// // double rotation = MathUtil.clamp(
// //     -tx * kPRotation,
// //     -2.0,
// //     2.0
// // );

// // double forward = (forw > desiredDistance + 0.02)
// //     ? MathUtil.clamp(
// //         (forw - desiredDistance) * kPForw,
// //         0,
// //         1.5
// //     )
// //     : 0;

// // if (forw <= desiredDistance + 0.02) {
// //     liftMotor.set(1);
// // }

// // drivetrain.applyRequest(() ->
// //     forwardStraight
// //         .withVelocityX(forward)
// //         .withVelocityY(strafe)
// //         .withRotationalRate(rotation)
// // );
// // }




// double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);


// // double MaxSpeed = 0.4 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);



// public void init() {

//     if(SmartDashboard.getBoolean("DB/Button 1", true)){
//   final double MaxSpeed = 0.45 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);    
//     }else{

//   final double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);


//     }

// }

//     // private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
//     private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

//     /* Setting up bindings for necessary control of the swerve drive platform */
//     private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()// fieldcentric or robotcentric
//             .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.05) // Add a 10% deadband
//             .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
//     private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
//     private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
//     public final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric() //public for subsystem
//             .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

//     private final Telemetry logger = new Telemetry(MaxSpeed);

//     private CommandXboxController joystick = new CommandXboxController(0);
    
//     // private void slow() {
// // private CommandXboxController joystick = new CommandXboxController(0);




//     // }
    
//     private final CommandXboxController player2 = new CommandXboxController(1);
 
 
//     public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

//     /* Path follower */
//     // private final SendableChooser<Command> autoChooser;






// public void testLimelight() {
//     boolean hasTarget =
//         LimelightHelpers.getTV("limelight");

//     double tx =
//         LimelightHelpers.getTX("limelight");

//     double ty =
//         LimelightHelpers.getTY("limelight");

//     System.out.println("Has Target: " + hasTarget);
//     System.out.println("TX: " + tx);
//     System.out.println("TY: " + ty);

// SmartDashboard.putBoolean(
//     "LL Has Target",
//     LimelightHelpers.getTV("limelight")
// );

// SmartDashboard.putNumber(
//     "LL TX",
//     LimelightHelpers.getTX("limelight")
// );

// SmartDashboard.putNumber(
//     "LL TY",
//     LimelightHelpers.getTY("limelight")
// );

// }





//     public RobotContainer() {




      
               
//         // SmartDashboard.putString("DB/String 0", "Tests");
//         // String autoName = SmartDashboard.getString("DB/String 0", "Tests");
          
//         // SmartDashboard.putData("Auto Mode", autoChooser);
        
//         // SmartDashboard.putString("DB/String 0", "");
//         // System.out.println(AutoBuilder.getAllAutoNames());
//         configureBindings();

        
//         CommandScheduler.getInstance().schedule(FollowPathCommand.warmupCommand());


// joystick.rightTrigger(0.05).whileTrue(
//     Commands.run(
//         () -> {
//             double trigger = joystick.getRightTriggerAxis();

//             double speed = (trigger <= 0.75)
//                 ? (trigger / 0.75) * 1
//                 : 1;
//             {
//     liftMotor.set(speed);
// }

//         }
//     ).finallyDo(interrupted -> stopLift())
// );


// joystick.leftTrigger(0.05).whileTrue(
//     Commands.run(
//         () -> {
//             double trigger = joystick.getLeftTriggerAxis();

//             double speed = (trigger <= 0.75)
//                 ? (trigger / 0.75) * 1
//                 : 1;

//            {
//     liftMotor.set(-speed);
// }
//         }
//     ).finallyDo(interrupted -> stopLift())
// );






//     }


//   private final SparkMax intake = new SparkMax(14, MotorType.kBrushless);
//  private final SparkMax liftMotor = new SparkMax(1, MotorType.kBrushless);

// // private SparkMax intake = PIDtest.intake;






// private void stopLift() {
//      liftMotor.set(0.0);
// }




// private void up() {
//      intake.set(1);
//  }
// private void down() {
//     intake.set(-1);
// }
// private void stopintake() {
//      intake.set(0.0);
//  }



// // public void setup() {


// //  SparkMax autointake = new SparkMax(14, MotorType.kBrushless);
// //  SparkClosedLoopController m_controller = autointake.getClosedLoopController();
// //  RelativeEncoder m_encoder = autointake.getEncoder();

// //  SparkMaxConfig config = new SparkMaxConfig();
// //  config.idleMode(IdleMode.kBrake);
// //  config.closedLoop
// //     .p(0.1)
// //     .i(0.0)
// //     .d(0.005)
// //     .outputRange(-0.6, 0.6);

// // autointake.configure(config, SparkMax.ResetMode.kResetSafeParameters, SparkMax.PersistMode.kNoPersistParameters);


// // m_encoder.setPosition(0.0);

// // m_controller.setReference(5.0, ControlType.kPosition);

// // }

//     //             joystick.whileTrue(
//     //     Commands.startEnd(
//     //         this::down,
//     //         this::stopLift
//     //     )
//     // );

    
            


//  private final CommandXboxController xbox = new CommandXboxController(0);

//     private void stoprum() {
//     xbox.setRumble(RumbleType.kBothRumble, 0.0);
//     }

// public void startrum() {
// xbox.setRumble(RumbleType.kBothRumble, 1.0);
// }

// public void halfsec() {
//     new SequentialCommandGroup(
//     new InstantCommand(this::startrum),
//     new WaitCommand(0.5),
//     new InstantCommand(this::stoprum)
// ).schedule();

// }










// // public void up() {
// //      intake.set(0.5);
// //  }
// // public void down() {
// //     intake.set(-0.5);
// // }
// // public void stopintake() {
// //      intake.set(0.0);
// //  }




// //     public void robotcentric() {
// // forwardStraight.withVelocityX(0).withVelocityY(0).withRotationalRate(-0.5)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
// //     }


//         double yl = joystick.getLeftY();
//         double xl = joystick.getLeftX();
//         double yr = joystick.getRightY();
//         double xr = joystick.getRightX();
//     private void configureBindings() {



//         //added deadzone to prevent stickdrift *hopefully*
// 		yl = Math.abs(yl)<0.05?0:yl;
// 		xl = Math.abs(xl)<0.05?0:xl;
//         yr = Math.abs(yr)<0.05?0:yr;
// 		xr = Math.abs(xr)<0.05?0:xr;
//         // Note that X is defined as forward according to WPILib convention,
//         // and Y is defined as to the left according to WPILib convention.
//         drivetrain.setDefaultCommand(
//             // Drivetrain will execute this command periodically
//             drivetrain.applyRequest(() ->
//                 forwardStraight.withVelocityX(-joystick.getLeftY() * MaxSpeed) //yl // use drive instead
//                     .withVelocityY(-joystick.getLeftX() * MaxSpeed) //xl
//                     .withRotationalRate(-joystick.getRightX() * MaxAngularRate) //xr
//             )
//         );

//         // Idle while the robot is disabled. This ensures the configured
//         // neutral mode is applied to the drive motors while disabled.
//         final var idle = new SwerveRequest.Idle();
//         RobotModeTriggers.disabled().whileTrue(
//             drivetrain.applyRequest(() -> idle).ignoringDisable(true) //change to brake maybe???
//         );

//         joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));

// // public void driftfix() {
// //     Commands.run(
// //         () -> {
// //             double trigger = joystick.getLeftY();

// //             double speed = (trigger <= 0.05)
// //                 ? (trigger / 0.75) * 1
// //                 : 0;

// //            {
// //     // liftMotor.set(-speed);
// // }
// //         }
// // // halfsec();
// //             drivetrain.applyRequest(() ->
// //                 forwardStraight.withVelocityX(0) //yl // use drive instead
// //                     .withVelocityY(0) //xl
// //                     .withRotationalRate(0)); //xr
// //         }
// //     );




// joystick.rightBumper().whileTrue(
//     Commands.run(
//         () -> {
//             up();
//         }
//     ).finallyDo(interrupted -> stopintake())
// );  
// joystick.leftBumper().whileTrue(
//     Commands.run(
//         () -> {
//             down();
//         }
//     ).finallyDo(interrupted -> stopintake())
// );  


// // joystick.y().onTrue(
// //     // Commands.runOnce(() -> PIDtest.move(4))
// //     // O.play()
// // );  






//         // joystick.b().whileTrue(drivetrain.applyRequest(() ->
//         //     point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
//         // ));


//         // xbox.x().onTrue(new InstantCommand(this::startrum));
//         // xbox.b().onTrue(new InstantCommand(this::startrum));
//         // xbox.a().onTrue(new InstantCommand(this::startrum));
//         // xbox.povRight().onTrue(new InstantCommand(this::startrum));
//         // xbox.povLeft().onTrue(new InstantCommand(this::startrum));
//         // xbox.povDown().onTrue(new InstantCommand(this::startrum));
//         // xbox.povUp().onTrue(new InstantCommand(this::startrum));
//         // xbox.rightStick().onTrue(new InstantCommand(this::stoprum));
//         // xbox.leftStick().onTrue(new InstantCommand(this::startrum));
//         // xbox.leftBumper().onTrue(new InstantCommand(this::startrum));
//         // xbox.rightBumper().onTrue(new InstantCommand(this::startrum));
//         // xbox.rightTrigger(0.05).onTrue(new InstantCommand(this::startrum));
//         // xbox.leftTrigger(0.05).onTrue(new InstantCommand(this::startrum));
//         // xbox.y().onTrue(new InstantCommand(this::startrum));
//         // joystick.povUp().whileTrue(drivetrain.applyRequest(() ->
//         //     forwardStraight.withVelocityX(0.5).withVelocityY(0))
//         // );
//         // joystick.povDown().whileTrue(drivetrain.applyRequest(() ->
//         //     forwardStraight.withVelocityX(-0.5).withVelocityY(0))
//         // );
//     joystick.povUp().whileTrue(drivetrain.applyRequest(() ->
//         forwardStraight.withVelocityX(0.5).withVelocityY(0))
//     );
//     joystick.povDown().whileTrue(drivetrain.applyRequest(() ->
//         forwardStraight.withVelocityX(-0.5).withVelocityY(0))
//     );
//     joystick.povLeft().whileTrue(drivetrain.applyRequest(() ->
//         forwardStraight.withVelocityX(0).withVelocityY(0.5))
//     );
//     joystick.povRight().whileTrue(drivetrain.applyRequest(() ->
//         forwardStraight.withVelocityX(0).withVelocityY(-0.5))
//     );
//     joystick.x().whileTrue(drivetrain.applyRequest(() ->
//         forwardStraight.withVelocityX(0).withVelocityY(0).withRotationalRate(0.5)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
//     );
//     joystick.b().whileTrue(drivetrain.applyRequest(() ->
//         forwardStraight.withVelocityX(0).withVelocityY(0).withRotationalRate(-0.5)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
//     );
    


// // joystick.x().whileTrue(new InstantCommand(this::test));








// // joystick.x().onTrue(
// //     drivetrain.applyRequest(() ->
// //         forwardStraight
// //             .withVelocityX(0)
// //             .withVelocityY(0)
// //             .withRotationalRate(3)
// //     )
// // );







//     //  joystick.rightTrigger().whileTrue(
//     //     Commands.startEnd(
//     //         this::up,
//     //         this::stopLift
//     //     )
//     // );


//         // Run SysId routines when holding back/start and X/Y.
//         // Note that each routine should be run exactly once in a single log.




//         // joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
//         // joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
//         // joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
//         // joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));




//         // Reset the field-centric heading on left bumper press.
//         // joystick.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));



















//         drivetrain.registerTelemetry(logger::telemeterize);
//     }


// public Command getAutonomousCommand() {
//     String autoName = SmartDashboard.getString("DB/String 0", "");
   
//     return new PathPlannerAuto(autoName);
// }
// }
