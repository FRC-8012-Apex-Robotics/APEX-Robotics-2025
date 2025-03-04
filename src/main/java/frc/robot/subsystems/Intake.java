package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends SubsystemBase {
    private final SparkMax m_arm = new SparkMax(4, MotorType.kBrushless);
    private final SparkMax m_intake = new SparkMax(5, MotorType.kBrushless);
    private final Timer intakeTimer = new Timer();
    private final PIDController armPID = new PIDController(2, 0, 0.001);
    private final PIDController spinPID = new PIDController(0.000005, 0, 0);


    public Intake(){
        m_arm.getEncoder().setPosition(0);
        m_arm.getAlternateEncoder().setPosition(0);
        
        
        SmartDashboard.putNumber("Intake Speed", 0.5);
        SmartDashboard.putNumber("Arm Speed", 0.4);
        SmartDashboard.putNumber("Arm Position", 0);
        

        /*setDefaultCommand(
            runOnce(() -> {
                m_arm.stopMotor();
            })
            .andThen(() -> {}).withName("Idle")
        );*/
        setDefaultCommand(
            run(() ->{
                SmartDashboard.putNumber("Alternate Encoder", m_arm.getAlternateEncoder().getPosition());

                SmartDashboard.putNumber("Arm Current Position", m_arm.getAlternateEncoder().getPosition());
                m_intake.stopMotor();
                SmartDashboard.putNumber("PID Setpoint", armPID.getSetpoint());

                double pidCalc = MathUtil.clamp(
                    armPID.calculate(m_arm.getAlternateEncoder().getPosition(), SmartDashboard.getNumber("Arm Position", 0.1)),
                    -SmartDashboard.getNumber("Arm Speed", 0.4), 
                    SmartDashboard.getNumber("Arm Speed", 0.4)
                );

                SmartDashboard.putNumber("PID Calculation", pidCalc);
                m_arm.set(pidCalc);

                SmartDashboard.putNumber("Intake Current Velocity", m_intake.getEncoder().getVelocity());

                double spinCalc = spinPID.calculate(
                    m_intake.getEncoder().getVelocity(), 
                    SmartDashboard.getNumber("Intake Set Velocity", 0)
                );
                
                SmartDashboard.putNumber("PID Velocity", spinCalc/5400);

                m_intake.set(0);
            })
        );
    }

    public Command armDown(){
        /*return run(() -> m_arm.set(SmartDashboard.getNumber("Arm Speed", 0.4)))
        .until(() -> m_arm.getEncoder().getPosition() > 0.6)
        .finallyDo(() -> {
            m_arm.stopMotor();
            SmartDashboard.putString("Arm Position", "down"); 
        });*/
        return runOnce(() -> {
            SmartDashboard.putNumber("Arm Position", 0.39);
        });
    }

    public Command armUp(){
        /*return run(() -> m_arm.set(-SmartDashboard.getNumber("Arm Speed",0.4)))
        .until(() -> m_arm.getEncoder().getPosition() < 0.1)
        .finallyDo(() -> {
            m_arm.stopMotor();
            SmartDashboard.putString("Arm Position", "up");
        });*/
        return runOnce(() -> {
            SmartDashboard.putNumber("Arm Position", 0.05);
        });
    }

    public void resetArmPosition() {
        m_arm.getAlternateEncoder().setPosition(0);
    }

    public Command spinIntakeIn() {
        return run(() -> {
            
            SmartDashboard.putNumber("Velocity", m_intake.getEncoder().getVelocity());
            SmartDashboard.putNumber("Intake Set Velocity", -2600);
            
            /*m_intake.set(-0.5);*/
        }).finallyDo(() -> SmartDashboard.putNumber("Intake Set Velocity", 0) /*m_intake.stopMotor()*/);
    }
    public Command spinIntakeOut() {
        return run(() -> {

            SmartDashboard.putNumber("Velocity", m_intake.getEncoder().getVelocity());
            SmartDashboard.putNumber("Intake Set Velocity", 2600);

            /*m_intake.set(0.5);*/
        }).finallyDo(() -> SmartDashboard.putNumber("Intake Set Velocity", 0) /*m_intake.stopMotor()*/);
    }
}
