package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LED extends SubsystemBase{
    private Spark leds;
    private double m_color = 0.0;

    public enum Colors{
        kRed,
        kGreen,
        kBlue,
        kPurple;
    }

    public LED(){
        this.leds = new Spark(1);
    }
    public void setColor(Colors colors){
        
            switch (colors) {
                case kRed:
                    m_color = 0.61;
                    break;
                case kBlue:
                    m_color = 0.85;
                    break;
                case kGreen:
                    m_color = 0.71;
                    break;
                case kPurple:
                    m_color = 0.91;
                    break;
                default:
                    m_color = 0.67;
                    break;
               }
    }

    @Override
    public void periodic(){
        leds.set(m_color);

    }
}
