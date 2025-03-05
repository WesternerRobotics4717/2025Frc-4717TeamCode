package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LED extends SubsystemBase{
    private Spark leds;

    public LED(){
        this.leds = new Spark(1);
    }

    public Command setBlue(){
        return this.runOnce(() ->{
            leds.set(0.87);
        });
        
    }

    public Command setRed(){
        return this.runOnce(
            () ->{
                leds.set(0.61);
            });
       
    }

    public Command setGreen(){
        return this.runOnce(
            () ->{
                leds.set(0.77);
            });
    }
    public Command setPurple(){
        return this.runOnce(
            () ->{
                leds.set(0.91);
            });
    }
}
