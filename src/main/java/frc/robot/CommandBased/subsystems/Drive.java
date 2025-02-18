package frc.robot.CommandBased.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;

public class Drive extends SubsystemBase{
    private final Timer autoTimer = new Timer();
    private final TalonFX m_leftDrive = new TalonFX(0);
    private final TalonFX m_rightDrive = new TalonFX(1);
    private final TalonFX m_leftFollow = new TalonFX(2);
    private final TalonFX m_rightFollow = new TalonFX(3);
    private final TalonFXConfiguration ghibusConfig = new TalonFXConfiguration();
    private final DifferentialDrive m_robotDrive =
      new DifferentialDrive(m_leftDrive::set, m_rightDrive::set);

    public Drive() {
        SendableRegistry.addChild(m_robotDrive, m_leftDrive);
        SendableRegistry.addChild(m_robotDrive, m_rightDrive);

        ghibusConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        m_rightDrive.getConfigurator().apply(ghibusConfig);
        m_leftFollow.setControl(new Follower(m_leftDrive.getDeviceID(), false));
        m_rightFollow.setControl(new Follower(m_rightDrive.getDeviceID(), false));
    }

    public Command arcadeDriveCommand(DoubleSupplier fwd, DoubleSupplier rot) {
        return run(() -> m_robotDrive.arcadeDrive(fwd.getAsDouble(), rot.getAsDouble(), true)).withName("arcadeGhibus");
    }

    public Command driveForTime(Double time, Double speed) {
        return runOnce(() -> {
            autoTimer.reset();
            autoTimer.start();
        }).andThen(() -> {
            m_robotDrive.arcadeDrive(speed, 0);
        }).until(
            () -> autoTimer.get() >= time
        ).finallyDo(() -> {
            m_robotDrive.stopMotor();
        });
    }
}
