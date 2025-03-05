package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.IntakeConstants.IntakeSpeeds;
import frc.robot.Configs;

public class Intake extends SubsystemBase{
    public enum Speeds{
        In,
        Shoot,
        Hold;
    }
    private SparkMax intake;
    private DigitalInput coral;

    private RelativeEncoder intakeEncoder;

    private SparkClosedLoopController intakePID;

    private double intakeTarget = IntakeSpeeds.stop;



    public Intake(){
        intake = new SparkMax(IntakeConstants.IntakeMotorID, MotorType.kBrushless);
        intakeEncoder = intake.getEncoder();
        intakePID = intake.getClosedLoopController();
        coral = new DigitalInput(IntakeConstants.CoralSensorID);
        intake.configure(
            Configs.IntakeConfig.IntakeConfig,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters);   
            
        intakeEncoder.setPosition(0);
    }

    public Command intakeCoral(){
        return this.run(
            () ->{
                if (coral.get()){
                    intake.set(IntakeSpeeds.intakeSpeed);
                }else{
                    intake.set(IntakeSpeeds.stop);
                }
            }
        );
    }

    public Command shootOut(){
        return this.run(
            () ->{
                intake.set(IntakeSpeeds.shootSpeed);
            });
    }
    public Command stopIntake(){
        return this.runOnce(
            () ->{
                intake.set(IntakeSpeeds.stop);
        });
    }


    public boolean isCoralDetected(){
        return !coral.get();
    }

     @Override
    public void periodic(){
        SmartDashboard.putBoolean("coral", coral.get());
        SmartDashboard.putNumber("intakeSpeed", intakeEncoder.getPosition());
    }
}
