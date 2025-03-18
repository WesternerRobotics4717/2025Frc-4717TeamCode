package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.PositionTorqueCurrentFOC;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Configs;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.ElevatorConstants.ElevatorSetPoints;

public class Elevator extends SubsystemBase{
    public enum Setpoint{
        kIntakeL,
        kLevel1,
        kLevel2,
        kMax;
    }

    private TalonFX liftMotor;
    
    private Orchestra orchestra;

    private PositionVoltage poseVoltage = new PositionVoltage(0).withSlot(0);

    private PositionTorqueCurrentFOC poseTouque = new PositionTorqueCurrentFOC(0).withSlot(1);

    private NeutralOut m_break = new NeutralOut();
  
    

    private double elevatorCurrentTarget = ElevatorSetPoints.kIntakeL;
    
    public Elevator(){
        liftMotor = new TalonFX(ElevatorConstants.LiftMotorID);
        
        liftMotor.getConfigurator().apply(Configs.ElevatorConfigs.elevatorConfigs);
       

       orchestra = new Orchestra();
       orchestra.addInstrument(liftMotor);
       orchestra.loadMusic("doom.chrp");
        
       liftMotor.setPosition(0);
    }   

    
    public void moveToSetPoint(){
        liftMotor.setControl(poseVoltage.withPosition(elevatorCurrentTarget));
        //liftMotor.setControl(poseTouque.withPosition(elevatorCurrentTarget));
        //liftMotor.setPosition(elevatorCurrentTarget);
    }

    public Command setSetPointCommand(Setpoint setPoint){
        return this.runOnce(
            () ->{
        switch(setPoint){
            case kIntakeL:
                elevatorCurrentTarget = ElevatorSetPoints.kIntakeL;
                break;
            case kLevel1:
                elevatorCurrentTarget = ElevatorSetPoints.kLevel1;
                break;
            case kLevel2:
                elevatorCurrentTarget = ElevatorSetPoints.kLevel2;
                break;
            case kMax:
                elevatorCurrentTarget = ElevatorSetPoints.kMax;
                break;
        }
        });
    }

    public Command playStuff(){
        return this.runOnce(() ->{
            orchestra.play();
        });
    }
    public Command stopPlay(){
        return this.runOnce(() ->{
            orchestra.stop();
        });
    }
    

    @Override
    public void periodic(){
        SmartDashboard.putNumber("elevator Pos", liftMotor.getPosition().getValueAsDouble());
        SmartDashboard.putNumber("elevator Target", elevatorCurrentTarget);
        moveToSetPoint();
    }
    
}
