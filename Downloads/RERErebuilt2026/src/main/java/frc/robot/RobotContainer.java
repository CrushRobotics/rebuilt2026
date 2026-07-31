// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.util.concurrent.DelayQueue;

import edu.wpi.first.wpilibj2.command.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Dynamic;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
// import frc.robot.subsystems.LiftSubsystem;
import edu.wpi.first.wpilibj.Timer;



public class RobotContainer {

     boolean emergencyStop = false;   
public boolean getEmergencyStop() {
    return emergencyStop;
}

public void periodic() {
    emcheck();
    // SmartDashboard.putNumber("time", currentTime);
}
// final double currentTime = Utils.getCurrentTimeSeconds();

//  private final LiftSubsystem LiftSubsystem = new LiftSubsystem();
 
    Command camtest = new InstantCommand(() -> {
        System.out.println(CameraServer.getServer());
    });

    
    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandXboxController joystick = new CommandXboxController(0);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    private final double kLoadThresholdAmps = 37.0; 

    public RobotContainer() {
        configureBindings();
        configureButtonBindings();
         SmartDashboard.putData("getcam", camtest);
        }

        private final SparkMax liftMotor = new SparkMax(1, MotorType.kBrushless);
        public void emcheck() {
            // SmartDashboard.putString("current", "w");

    double currentAmps = liftMotor.getOutputCurrent();

// if (currentAmps > kLoadThresholdAmps && !emergencyStop) {
//     System.out.println("EM stop Current: " + currentAmps + "A");
//     emergencyStop = true;
//     liftMotor.set(0.0);
//     halfsec();
// }

// if (emergencyStop) {
//     liftMotor.set(0.0);
// }


double loadStartTime = -1;
double referenceAmps = 0;

if (currentAmps > kLoadThresholdAmps && !emergencyStop) {

    if (loadStartTime < 0) {
        loadStartTime = Timer.getFPGATimestamp();
        referenceAmps = currentAmps;
    }

    if (Math.abs(currentAmps - referenceAmps) > 3.0) {
        loadStartTime = Timer.getFPGATimestamp();
        referenceAmps = currentAmps;
    }

    if (Timer.getFPGATimestamp() - loadStartTime >= 1.0) {
    System.out.println("EM stop Current: " + currentAmps + "A");
    emergencyStop = true;
    liftMotor.set(0.0);
    halfsec();
    }

} else {
    loadStartTime = -1;
}









}

public Command ret() {
    return new InstantCommand(this::emcheck);
}
public double getliftcurrent() {
 
    return liftMotor.getOutputCurrent();
}









    
    //addsparkmotor
    // private final SparkMax liftMotor = new SparkMax(20, MotorType.kBrushless);
    //.
    
    
    private void configureButtonBindings() {

    //new JoystickButton(controller, XboxController.Button.kLeftBumper.value)
    //.whenPressed(new DownCommand());

    //new JoystickButton(controller, XboxController.Button.kRightBumper.value)
    //.whenPressed(new UpCommand());

    //joystick.leftBumper().onTrue(new Down());
    //joystick.rightBumper().onTrue(new Up());

    // joystick.leftBumper().whileTrue(
    //     Commands.startEnd(
    //         this::down,
    //         this::stopLift
    //     )
    // );

    // joystick.rightTrigger().whileTrue(
    //     Commands.startEnd(
    //         this::up,
    //         this::stopLift
    //     )
    // );



joystick.rightTrigger(0.05).whileTrue(
    Commands.run(
        () -> {
            double trigger = joystick.getRightTriggerAxis();

            double speed = (trigger <= 0.75)
                ? (trigger / 0.75) * 1
                : 1;
            if (!emergencyStop) {
    liftMotor.set(speed);
}

        }
    ).finallyDo(interrupted -> stopLift())
);
joystick.leftTrigger(0.05).whileTrue(
    Commands.run(
        () -> {
            double trigger = joystick.getLeftTriggerAxis();

            double speed = (trigger <= 0.75)
                ? (trigger / 0.75) * 1 //test with 0.3 next
                : 1;

           if (!emergencyStop) {
    liftMotor.set(-speed);
}

        }
    ).finallyDo(interrupted -> stopLift())
);

// xbox.y().whileTrue(new InstantCommand(() -> emergencyStop = true));
xbox.y().onTrue(new InstantCommand(() -> {
    emergencyStop = false;
    System.out.println("Y");
    halfsec();
}));
xbox.x().onTrue(new InstantCommand(() -> emergencyStop = true));

checkem();


//   private final CommandXboxController xbox = new CommandXboxController(0);

    }
    
    // JoystickButton.b().whileTrue(stopLift());

 private final CommandXboxController xbox = new CommandXboxController(0);


public Object periodic;

public void halfsec() {
    new SequentialCommandGroup(
    new InstantCommand(this::whynot),
    new WaitCommand(0.5),
    new InstantCommand(this::stoprum)
).schedule();

}




private void checkem() {
Commands.repeatingSequence(
    new InstantCommand(this::emcheck),
    new WaitCommand(0.1)
).schedule();


}


// private void up() {
//     liftMotor.set(0.5); 
// }

// private void down() {
//     liftMotor.set(-0.5);
// }
private void stoprum() {
xbox.setRumble(RumbleType.kBothRumble, 0.0);
}
private void stopLift() {
     liftMotor.set(0.0);
 }

private void showB() {

    System.out.println("B");
}


// private void up() {

// }
// private void down() {

// }
private void whynot() {
 xbox.setRumble(RumbleType.kBothRumble, 1.0);

}
private void test() {
    emergencyStop = true;
}
    // private final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
    private void configureBindings() {
        // xbox.b().whileTrue(new RunCommand(() -> stopLift()));
        xbox.b().whileTrue(new RunCommand(this::stopLift));
        // xbox.x().whileTrue(new RunCommand(this::whynot));
        xbox.b().whileTrue(new RunCommand(this::showB));
        // xbox.x().whileTrue();
       xbox.x().onTrue(new InstantCommand(() -> emergencyStop = true));
        //  xbox.y().onTrue(new InstantCommand(this::test));
        // xbox.y().whileTrue(new InstantCommand(LiftSubsystem::emergencyStop)); 
        




        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        joystick.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // Reset the field-centric heading on left bumper press.
        joystick.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {


        //auto
        try{
            //load from gui name
            PathPlannerPath path = PathPlannerPath.fromPathFile("Example Path");

    
            return AutoBuilder.followPath(path);
            } catch (Exception e) {
            DriverStation.reportError("Big oops: " + e.getMessage(), e.getStackTrace());
            return Commands.none();
        }
        //.



    }


// public void checkLiftCurrent() {
//     double currentAmps = liftMotor.getOutputCurrent();

//     if (currentAmps > kLoadThresholdAmps) {
//         System.out.println("EM stop Current: " + currentAmps + "A");
//         liftMotor.set(0.0);
//     }
// }

}
