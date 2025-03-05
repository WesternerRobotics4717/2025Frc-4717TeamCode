package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
//import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.IOConstatnts;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Elevator.Setpoint;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
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
    //private final JoystickButton dampen = new JoystickButton(driver, XboxController.Button.kRightBumper.value);

    /*Operator Buttions */
    private final JoystickButton elevatorL1 = new JoystickButton(operator, IOConstatnts.kLevel1IO);
    private final JoystickButton elevatorL2 = new JoystickButton(operator, IOConstatnts.kLevel2IO);
    private final JoystickButton elevatorL3 = new JoystickButton(operator, IOConstatnts.kLevel3IO);
    private final JoystickButton elevatorL4 = new JoystickButton(operator, IOConstatnts.kLevel4IO);
    private final JoystickButton intakeCoral = new JoystickButton(operator, 2);
    private final JoystickButton outtakeCoral = new JoystickButton(operator, 1);


    /* Subsystems */
    private final PoseEstimator s_PoseEstimator = new PoseEstimator();
    private final Swerve s_Swerve = new Swerve(s_PoseEstimator);
    private final Elevator s_Elevator = new Elevator();
    private final Intake s_Intake = new Intake();
    private final LED s_led = new LED();
    //private final Vision s_Vision = new Vision(s_PoseEstimator);

    /* AutoChooser */

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
        s_led.setDefaultCommand(s_led.setPurple());
       // s_Intake.setDefaultCommand(s_Intake.intakeCoral());
    
        
        // Configure the button bindings
        configureButtonBindings();
    }

    
    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroHeading()));

        elevatorL1.onTrue(s_Elevator.setSetPointCommand(Setpoint.kLevel1));
        elevatorL2.onTrue(s_Elevator.setSetPointCommand(Setpoint.kLevel2));
        elevatorL3.onTrue(s_Elevator.setSetPointCommand(Setpoint.kLevel3));
        elevatorL4.onTrue(s_Elevator.setSetPointCommand(Setpoint.kLevel4));
        intakeCoral.onTrue(s_Intake.intakeCoral());
        intakeCoral.onFalse(s_Intake.stopIntake());
        intakeCoral.onTrue(s_led.setRed());
        outtakeCoral.onTrue(s_Intake.shootOut());
        outtakeCoral.onTrue(s_led.setGreen());
        outtakeCoral.onFalse(s_Intake.stopIntake());


    }

    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return null;
    }
}