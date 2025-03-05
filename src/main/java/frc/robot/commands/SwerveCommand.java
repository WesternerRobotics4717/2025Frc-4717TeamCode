package frc.robot.commands;

import frc.robot.Constants;
//import frc.robot.States;
import frc.robot.subsystems.Swerve;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.util.LimelightHelpers;
//import edu.wpi.first.math.util.Units;


public class SwerveCommand extends Command {    
    private Swerve s_Swerve;    
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private DoubleSupplier dynamicHeadingSup;
    private BooleanSupplier robotCentricSup;
    private BooleanSupplier dampenSup;
    private PIDController rotationController;
    private Boolean fieldRelative;

    private BooleanSupplier aprilAlineLeft;
    private BooleanSupplier aprilAlineRight;
    

    public SwerveCommand(Swerve s_Swerve, DoubleSupplier translationSup, DoubleSupplier strafeSup, DoubleSupplier rotationSup, BooleanSupplier robotCentricSup, BooleanSupplier dampen, DoubleSupplier dynamicHeadingSup, BooleanSupplier m_aprilAlineLeft, BooleanSupplier m_aprilAlineRight) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        //TODO: Tune heading PID
        rotationController = new PIDController(Constants.Swerve.HeadingKP, Constants.Swerve.HeadingKI, Constants.Swerve.HeadingKD );
        rotationController.enableContinuousInput(-Math.PI, Math.PI);
        rotationController.setTolerance(Constants.Swerve.HeadingTolerence);

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.robotCentricSup = robotCentricSup;
        this.dampenSup = dampen;
        this.dynamicHeadingSup = dynamicHeadingSup;
        this.fieldRelative = true;
        this.aprilAlineLeft = m_aprilAlineLeft;
        this.aprilAlineRight = m_aprilAlineRight;
    }

    @Override
    public void execute() {
        /* Get Values, Deadband, Dampen */
        double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), Constants.stickDeadband) * (dampenSup.getAsBoolean() ? 0.2 : 1);
        double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), Constants.stickDeadband) * (dampenSup.getAsBoolean() ? 0.2 : 1);
        double rotationVal = MathUtil.applyDeadband(rotationSup.getAsDouble(), Constants.stickDeadband) * (dampenSup.getAsBoolean() ? 0.2 : 1);
        //TODO: Add code for dynamic heading- the supplier is a placeholder right now
        //double dynamicHeading = dynamicHeadingSup.getAsDouble();

        rotationVal = rotationVal * Constants.Swerve.maxAngularVelocity;

        if(this.aprilAlineLeft.getAsBoolean()){

            strafeVal = (16 - LimelightHelpers.getTX("limelight-mine"))* 0.01;
            SmartDashboard.putNumber("Strafe val", strafeVal);
            SmartDashboard.putNumber("TX", LimelightHelpers.getTX("limelight-mine"));
            fieldRelative = false;
        }else if(this.aprilAlineRight.getAsBoolean()){
            strafeVal = (23 + LimelightHelpers.getTX("limelight-mine"))* -0.01;
            SmartDashboard.putNumber("Strafe val", strafeVal);
            SmartDashboard.putNumber("TX", LimelightHelpers.getTX("limelight-mine"));
            fieldRelative = false; 
        }
        else{
            fieldRelative = true;
        }

        /* Drive */
        s_Swerve.drive(
            new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed), 
            rotationVal,
            fieldRelative, 
            true
        );
        
    }
}