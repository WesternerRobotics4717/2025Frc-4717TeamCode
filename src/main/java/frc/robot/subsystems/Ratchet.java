package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorConstants;

public class Ratchet extends SubsystemBase{
    private Servo climbLock;

    public Ratchet(){
        climbLock = new Servo(ElevatorConstants.LockServoID);
    }
    public Command unlockRatchet(){
        return this.runOnce(() ->{
            climbLock.set(0);
        });
    }

    public Command lockRatchet(){
        return this.runOnce(() ->{
            climbLock.set(0.5);
        });
    }
    @Override
    public void periodic(){
        SmartDashboard.putNumber("servo Pos", climbLock.getPosition());
    }

}
