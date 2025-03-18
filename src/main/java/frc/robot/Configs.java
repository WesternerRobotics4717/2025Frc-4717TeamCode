package frc.robot;

import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.revrobotics.spark.config.SparkMaxConfig;

public final class Configs {
    public static final class ElevatorConfigs{
        public static final TalonFXConfiguration elevatorConfigs = new TalonFXConfiguration();
        static{
            elevatorConfigs.Slot0.kP = 2.4;
            elevatorConfigs.Slot0.kI = 0;
            elevatorConfigs.Slot0.kD = 0.1;

            elevatorConfigs.Voltage.withPeakForwardVoltage(Volts.of(8)).withPeakReverseVoltage(Volts.of(-8));

            elevatorConfigs.Slot1.kP = 60;
            elevatorConfigs.Slot1.kI = 0;
            elevatorConfigs.Slot1.kD = 6;

            elevatorConfigs.TorqueCurrent.withPeakForwardTorqueCurrent(Amps.of(120)).withPeakReverseTorqueCurrent(Amps.of(-120));
        }
    }
    public static final class IntakeConfig{
        public static final SparkMaxConfig IntakeConfig = new SparkMaxConfig();
        static {
             IntakeConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(30).voltageCompensation(12);

             IntakeConfig
                .closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .p(0.005)
                .d(0)
                .outputRange(-1, 1)
                .maxMotion 
                .maxVelocity(4200)
                .maxAcceleration(6000)
                .allowedClosedLoopError(0.01);

        }
    }
    public static final class AlgaeConfig{
        public static final SparkMaxConfig AlgaeArmConfig = new SparkMaxConfig();
        public static final SparkMaxConfig AlgeaWheelConfig = new SparkMaxConfig();

        static {
            AlgaeArmConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(40).voltageCompensation(12);
            AlgeaWheelConfig.idleMode(IdleMode.kCoast).smartCurrentLimit(40).voltageCompensation(12);

            AlgaeArmConfig
                .closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .p(0.01)
                .d(0.00)
                .outputRange(-1, 1)
                .maxMotion
                .maxVelocity(4200)
                .maxAcceleration(6000)
                .allowedClosedLoopError(.01);
        }
    }
}
