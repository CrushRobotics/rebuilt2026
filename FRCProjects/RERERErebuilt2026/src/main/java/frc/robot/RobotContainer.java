// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.io.ObjectInputFilter.Config;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import com.revrobotics.spark.config.*;
import com.revrobotics.spark.config.EncoderConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SoftLimitConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfigAccessor;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.FollowPathCommand;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class RobotContainer {
    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
    private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric()
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private final Telemetry logger = new Telemetry(MaxSpeed);

public final SparkMax intake = new SparkMax(14, MotorType.kBrushless);
 private final SparkMax liftMotor = new SparkMax(2, MotorType.kBrushless);

private final SparkBaseConfig config = new SparkMaxConfig();
RelativeEncoder encoder = liftMotor.getEncoder();

public void reset() {
encoder.setPosition(0.0);

}


public void yes() {
drivetrain.applyRequest(() -> brake);
} 
private void stopLift() {
     liftMotor.set(0.0);
}
private void up() {
     intake.set(1);
 }
private void down() {
    intake.set(-1);
}
private void stopintake() {
     intake.set(0.0);
 }

    private void stoprum() {
    joystick.setRumble(RumbleType.kBothRumble, 0.0);
    }

public void startrum() {
joystick.setRumble(RumbleType.kBothRumble, 1.0);
}

public void halfsec() {
    new SequentialCommandGroup(
    new InstantCommand(this::startrum),
    new WaitCommand(0.5),
    new InstantCommand(this::stoprum)
).schedule();





}











    public CommandXboxController joystick = new CommandXboxController(0);
    private final CommandXboxController player2 = new CommandXboxController(1);


    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    /* Path follower */
    private final SendableChooser<Command> autoChooser;

private final DigitalInput reset = new DigitalInput(0);

    public RobotContainer() {



double climbdia = 2; //diameter of drum
double ratio = 25; //gear ratio
double climbin = 8; //height in inch

double distperrot = climbdia * Math.PI;

double climbrot = distperrot / ratio;

config.encoder
    .positionConversionFactor(climbrot)
    .velocityConversionFactor(climbrot / 60);


config.softLimit
        .reverseSoftLimit(0)
        .reverseSoftLimitEnabled(true)
        .forwardSoftLimit(climbin)
        .forwardSoftLimitEnabled(true);

config.idleMode(IdleMode.kBrake);

liftMotor.configure(config, SparkMax.ResetMode.kResetSafeParameters, SparkMax.PersistMode.kNoPersistParameters);
        autoChooser = AutoBuilder.buildAutoChooser("Tests");
        SmartDashboard.putData("Auto Mode", autoChooser);

        configureBindings();





        

        // Warmup PathPlanner to avoid Java pauses
        CommandScheduler.getInstance().schedule(FollowPathCommand.warmupCommand());
    }

    private void configureBindings() {


Trigger codetrig = new Trigger(() -> !reset.get()); 

codetrig.onTrue(Commands.run(() -> {reset();}));

player2.leftBumper().whileTrue(
    Commands.run(
        () -> {
            up();
        }
    ).finallyDo(interrupted -> stopintake())
);  
player2.rightBumper().whileTrue(
    Commands.run(
        () -> {
            down();
        }
    ).finallyDo(interrupted -> stopintake())
);  




player2.rightTrigger(0.05).whileTrue(
    Commands.run(
        () -> {
            double trigger = player2.getRightTriggerAxis();

            double speed = (trigger <= 0.75)
                ? (trigger / 0.75) * 1
                : 1;

           {
    liftMotor.set(-speed);
}
        }
    ).finallyDo(interrupted -> stopLift())
);



        double brakepsy = joystick.getLeftY();
        double brakepsx = joystick.getLeftY();
            boolean byny = (brakepsy <= 0.05)
                ? true
                : false;
 
            boolean bynx = (brakepsx <= 0.05)
                ? true
                : false;
 



boolean moving = (byny == false || bynx == false) 
    ? false
    : true;

while (moving == false) {
    drivetrain.applyRequest(() -> brake); 
}
//         double brakeps = joystick.getLeftY();
//             double byn = (brakeps <= 0.05)
//                 ? 0
//                 : 1;
 

//  while (byn == 1) {
//     yes();
//  }
        // byn.whileFalse(drivetrain.applyRequest(() -> brake));

        // joystick.getLeftY().whileFalse(drivetrain.applyRequest(() -> brake));



        // joystick.getLeftY().whileFalse(drivetrain.applyRequest(() -> brake));




    joystick.povUp().whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0.5).withVelocityY(0))
    );
    joystick.povDown().whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(-0.5).withVelocityY(0))
    );
    joystick.povLeft().whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0).withVelocityY(0.5))
    );
    joystick.povRight().whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0).withVelocityY(-0.5))
    );
    joystick.x().whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0).withVelocityY(0).withRotationalRate(1.0)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );
    joystick.b().whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0).withVelocityY(0).withRotationalRate(-1.0)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );


//begin precise movement while turning using adapted code
//forward, turn right
joystick.povUp().and(joystick.b()).whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0.5).withVelocityY(0).withRotationalRate(-1.0)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );
//forward, turn left
joystick.povUp().and(joystick.x()).whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0.5).withVelocityY(0).withRotationalRate(1.0)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );
//back, turn right
joystick.povDown().and(joystick.b()).whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(-0.5).withVelocityY(0).withRotationalRate(-1.0)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );
//back, turn left    
joystick.povDown().and(joystick.x()).whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(-0.5).withVelocityY(0).withRotationalRate(1.0)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );
/////////////////////////////////////////////////////begin turn
//left, turn right
joystick.povLeft().and(joystick.b()).whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0).withVelocityY(0.5).withRotationalRate(-1.0)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );
//left, turn left
joystick.povLeft().and(joystick.x()).whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0).withVelocityY(0.5).withRotationalRate(1.0)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );
//right, turn right
joystick.povRight().and(joystick.b()).whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0).withVelocityY(-0.5).withRotationalRate(-1.0)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );
//right, turn left
joystick.povRight().and(joystick.x()).whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0).withVelocityY(-0.5).withRotationalRate(1.0)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );

//////////////////////////////////////////////////////sideways
//up, right
joystick.povUpRight().whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0.5).withVelocityY(-0.5).withRotationalRate(0))
    );
//up, left
joystick.povUpLeft().whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0.5).withVelocityY(0.5).withRotationalRate(0))
    );
//down, right
joystick.povDownRight().whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(-0.5).withVelocityY(-0.5).withRotationalRate(0))
    );
//down, left
joystick.povDownLeft().whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(-0.5).withVelocityY(0.5).withRotationalRate(0))
    );
////////////////////////////////////////////////////sidways up + turning
//up, right, right
joystick.povUpRight().and(joystick.b()).whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0.5).withVelocityY(-0.5).withRotationalRate(-1)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );
//up, right, left
joystick.povUpRight().and(joystick.x()).whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0.5).withVelocityY(-0.5).withRotationalRate(1)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );

//up, left, right
joystick.povUpLeft().and(joystick.b()).whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0.5).withVelocityY(0.5).withRotationalRate(-1)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );
//up, left, left
joystick.povUpLeft().and(joystick.x()).whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(0.5).withVelocityY(0.5).withRotationalRate(1)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );
////////////////////////////////sideways down + turning
//down, right, right
joystick.povDownRight().and(joystick.b()).whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(-0.5).withVelocityY(-0.5).withRotationalRate(-1)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );
//down, right, left
joystick.povDownRight().and(joystick.x()).whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(-0.5).withVelocityY(-0.5).withRotationalRate(1)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );

//down, left, right
joystick.povDownLeft().and(joystick.b()).whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(-0.5).withVelocityY(0.5).withRotationalRate(-1)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );
//down, left, left
joystick.povDownLeft().and(joystick.x()).whileTrue(drivetrain.applyRequest(() ->
        forwardStraight.withVelocityX(-0.5).withVelocityY(0.5).withRotationalRate(1)).finallyDo(interrupted -> forwardStraight.withRotationalRate(0))
    );
/////////////done
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                // forwardStraight.withVelocityX(-joystick.getLeftY() * MaxSpeed) //robot centric //fix drifting
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) //field centric
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
        // joystick.b().whileTrue(drivetrain.applyRequest(() ->
        //     point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        // ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // Reset the field-centric heading on left bumper press.
        joystick.y().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {
     String autoName = SmartDashboard.getString("DB/String 0", "");
   
    return new PathPlannerAuto(autoName);
    }
}