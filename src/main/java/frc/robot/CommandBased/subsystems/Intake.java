package frc.robot.CommandBased.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends SubsystemBase {
    private final SparkMax m_arm = new SparkMax(4, MotorType.kBrushless);
    private final Timer intakeTimer = new Timer();

    public Intake(){
        m_arm.getEncoder().setPosition(0);

        setDefaultCommand(
            runOnce(() -> {
                m_arm.stopMotor();
            })
            .andThen(() -> {}).withName("Idle")
        );
    }

    public Command armDown(){
        return runOnce(() -> {
            while (m_arm.getEncoder().getPosition() < 0.6){
                m_arm.set(0.4);
            }
            m_arm.stopMotor();
        
            SmartDashboard.putString("Arm Position", "down");
        });
    }

    public Command armUp(){
        return runOnce(() -> {
            while (m_arm.getEncoder().getPosition() > 0.1){
                m_arm.set(-0.4);
            }
            m_arm.stopMotor();

            SmartDashboard.putString("Arm Position", "up");
        });
    }

    public void armControl(double speed) {
        m_arm.set(speed);
    }
}
