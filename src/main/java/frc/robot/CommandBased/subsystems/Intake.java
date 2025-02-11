package frc.robot.CommandBased.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private final SparkMax m_arm = new SparkMax(6, MotorType.kBrushless);

    public Intake(){
        m_arm.getEncoder().setPosition(0);
        m_arm.set(0.5);
    }

    public Command armDown(){
        return runOnce(() -> {
            while (m_arm.getEncoder().getPosition() < 0.6){
                m_arm.set(0.4);
            }
            m_arm.stopMotor();
        });
    }
}
