package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.IOConstatnts;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Elevator.Setpoint;
//import frc.robot.subsystems.Algae.ScorePoints;
//import frc.robot.subsystems.Elevator.Setpoint;
import frc.robot.subsystems.LED.Colors;

public class RobotContainer {
    /* Controllers */
    private final Joystick driver = new Joystick(0);
    private final Joystick operator = new Joystick(1);

   /* Driver Controls */
	private final int translationAxis = 1;
	private final int strafeAxis = 0;
	private final int rotationAxis = 4;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton alineLeft = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton alineRight = new JoystickButton(driver, XboxController.Button.kRightBumper.value);

    private final JoystickButton lock = new JoystickButton(driver, XboxController.Button.kLeftStick.value);
    //private final JoystickButton dampen = new JoystickButton(driver, XboxController.Button.kRightBumper.value);

    /*Operator Buttions */
     private final JoystickButton elevatorIntake = new JoystickButton(driver, XboxController.Button.kA.value);
     private final JoystickButton elevatorMax = new JoystickButton(driver, XboxController.Button.kB.value);
     private final JoystickButton elevatorLevel1 = new JoystickButton(driver, XboxController.Button.kX.value);
     private final JoystickButton elevatorLevel2 = new JoystickButton(driver, XboxController.Button.kY.value);
     private final JoystickButton playMusic = new JoystickButton(driver, XboxController.Button.kStart.value);
     //private final JoystickButton intakeCoral = new JoystickButton(operator, 2);


    /* Subsystems */
    private final PoseEstimator s_PoseEstimator = new PoseEstimator();
    private final Swerve s_Swerve = new Swerve(s_PoseEstimator);
    private final Elevator s_Elevator = new Elevator();
    private final Ratchet s_Ratchet = new  Ratchet();
    //private final Intake s_Intake = new Intake();

    /* AutoChooser */
    private final SendableChooser<Command> autoChooser;

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        s_Swerve.setDefaultCommand(
            new SwerveCommand(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> false,
                () -> false,
                () -> 0,
                () -> alineLeft.getAsBoolean(),
                () -> alineRight.getAsBoolean()
            )
        );
        //s_Ratchet.setDefaultCommand(s_Ratchet.unlockRatchet());
        // Configure the button bindings
        configureButtonBindings();

        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Choser", autoChooser);

    }

    
    private void configureButtonBindings() {

        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroHeading()));
        lock.onTrue(s_Ratchet.lockRatchet());
       

         elevatorIntake.onTrue(s_Elevator.setSetPointCommand(Setpoint.kIntakeL));
         elevatorLevel1.onTrue(s_Elevator.setSetPointCommand(Setpoint.kLevel1));
         elevatorLevel2.onTrue(s_Elevator.setSetPointCommand(Setpoint.kLevel2));
         elevatorMax.onTrue(s_Elevator.setSetPointCommand(Setpoint.kMax));

         playMusic.onTrue(s_Elevator.playStuff());
         //playMusic.onFalse(s_Elevator.stopPlay());
         /*intakeCoral.onTrue(s_Intake.intakeCoral());
         intakeCoral.onFalse(s_Intake.stopIntake());*/
        
        // outtakeCoral.onTrue(s_Intake.shootOut());
    }

    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        //return new PathPlannerAuto("Example Auto");
        return new SwerveCommand(s_Swerve, ()-> -0.4, () -> 0, () -> 0, () -> false, () -> false, () -> 0, () -> false, () -> false).withTimeout(1).andThen(new SwerveCommand(s_Swerve, ()-> 0, () -> 0, () -> 0, () -> false, () -> false, () -> 0, () -> false, () -> false));//.withTimeout(1).andThen(s_Intake.shootOut()).withTimeout(3).andThen(s_Intake.stopIntake());

       // return autoChooser.getSelected();
    }
}