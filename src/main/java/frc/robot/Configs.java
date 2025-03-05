package frc.robot;

import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public final class Configs {

    public static final class CoralConfigs{
        
        //elevator
        public static final SparkMaxConfig LeftLeadConfig = new SparkMaxConfig();
        public static final SparkMaxConfig RightFollowConfig = new SparkMaxConfig();
;
        static{
            LeftLeadConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(35).voltageCompensation(12);

            LeftLeadConfig
                .closedLoop 
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .p(0.08)
                .d(0.1)
                .outputRange(-1, 1)
                .maxMotion 
                .maxVelocity(4200)
                .maxAcceleration(6000)
                .allowedClosedLoopError(0.01);
            
            RightFollowConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(35).voltageCompensation(12).inverted(true);

            RightFollowConfig
                .closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .p(0.08)
                .d(0.1)
                .outputRange(-1, 1)
                .maxMotion 
                .maxVelocity(4200)
                .maxAcceleration(6000)
                .allowedClosedLoopError(0.01);
        }
    }
    public static final class IntakeConfig{
        public static final SparkMaxConfig IntakeConfig = new SparkMaxConfig();
        static {
             IntakeConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(50).voltageCompensation(12);

             IntakeConfig
                .closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .p(0.01)
                .d(0)
                .outputRange(-1, 1)
                .maxMotion 
                .maxVelocity(4200)
                .maxAcceleration(6000)
                .allowedClosedLoopError(0.01);

        }
    }
}
