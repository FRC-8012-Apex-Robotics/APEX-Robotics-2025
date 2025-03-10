package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import choreo.trajectory.DifferentialSample;
import edu.wpi.first.math.controller.LTVUnicycleController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.DistanceUnit;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Drive extends SubsystemBase{
    private final Timer driveTimer = new Timer();
    private final TalonFX m_leftDrive = new TalonFX(Constants.DriveConstants.FRONT_LEFT_ID);
    private final TalonFX m_rightDrive = new TalonFX(Constants.DriveConstants.FRONT_RIGHT_ID);
    private final TalonFX m_leftFollow = new TalonFX(Constants.DriveConstants.BACK_LEFT_ID);
    private final TalonFX m_rightFollow = new TalonFX(Constants.DriveConstants.BACK_RIGHT_ID);
    private final TalonFXConfiguration ghibusConfig = new TalonFXConfiguration();

    private final DifferentialDrive m_robotDrive =
      new DifferentialDrive(m_leftDrive::set, m_rightDrive::set);

    public Drive() {
        SendableRegistry.addChild(m_robotDrive, m_leftDrive);
        SendableRegistry.addChild(m_robotDrive, m_rightDrive);

        ghibusConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

        m_leftDrive.getConfigurator().apply(ghibusConfig);
        m_leftFollow.setControl(new Follower(m_leftDrive.getDeviceID(), false));
        m_rightFollow.setControl(new Follower(m_rightDrive.getDeviceID(), false));

    }

    public Command arcadeDriveCommand(DoubleSupplier fwd, DoubleSupplier rot) {
        return run(() -> m_robotDrive.arcadeDrive(-fwd.getAsDouble(), -rot.getAsDouble(), true))
        .finallyDo(() -> m_robotDrive.stopMotor())
        .withName("arcadeGhibus");
    }

}
