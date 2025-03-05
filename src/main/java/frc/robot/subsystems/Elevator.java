package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Configs;
import frc.robot.Constants;
import frc.robot.Constants.ElevatorConstants.ElevatorSetPoints;

public class Elevator extends SubsystemBase{
    public enum Setpoint{
        kLevel1,
        kLevel2,
        kLevel3,
        kLevel4;
    }

    private SparkMax leftLead;
    private SparkMax rightFollow;

    private RelativeEncoder leadEncoder;
    private RelativeEncoder followEncoder;


    private SparkClosedLoopController leadElevatorPID;
    private SparkClosedLoopController followElevatorPID;

    private double elevatorCurrentTarget = ElevatorSetPoints.kLevel1;
    
    public Elevator(){
        leftLead = new SparkMax(Constants.ElevatorConstants.LeadMotorID, MotorType.kBrushless);
        rightFollow = new SparkMax(Constants.ElevatorConstants.FollowMotorID, MotorType.kBrushless);

        leadEncoder = leftLead.getEncoder();
        followEncoder = rightFollow.getEncoder();

        leadElevatorPID = leftLead.getClosedLoopController();
        followElevatorPID = rightFollow.getClosedLoopController();

        leftLead.configure(
            Configs.CoralConfigs.LeftLeadConfig,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters);
        rightFollow.configure(
            Configs.CoralConfigs.RightFollowConfig,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters);

        leadEncoder.setPosition(0);
        followEncoder.setPosition(0);
        
        
    }   
    private void moveToSetPoint(){
        followElevatorPID.setReference(elevatorCurrentTarget, ControlType.kMAXMotionPositionControl);
        leadElevatorPID.setReference(elevatorCurrentTarget, ControlType.kMAXMotionPositionControl);
    }

    public Command setSetPointCommand(Setpoint setPoint){
        return this.runOnce(
            () ->{
        
        switch(setPoint){
            case kLevel1:
                elevatorCurrentTarget = ElevatorSetPoints.kLevel1;
                
                break;
            case kLevel2:
                elevatorCurrentTarget = ElevatorSetPoints.kLevel2;
                break;
            case kLevel3:
                elevatorCurrentTarget = ElevatorSetPoints.kLevel3;
                break;
            case kLevel4:
                elevatorCurrentTarget = ElevatorSetPoints.kLevel4; 
                break;
            }
        });
    }

    @Override
    public void periodic(){
        moveToSetPoint();

        SmartDashboard.putNumber("ElevatorTarget", elevatorCurrentTarget);
        SmartDashboard.putNumber("ElevatorPosition", leadEncoder.getPosition());
        SmartDashboard.putNumber("follow", followEncoder.getPosition());
    }
    
}
