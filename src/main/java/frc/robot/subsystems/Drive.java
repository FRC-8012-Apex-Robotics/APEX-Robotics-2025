package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import choreo.trajectory.DifferentialSample;
import edu.wpi.first.math.controller.LTVUnicycleController;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.DistanceUnit;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase{
    private final Timer driveTimer = new Timer();
    private final TalonFX m_leftDrive = new TalonFX(0);
    private final TalonFX m_rightDrive = new TalonFX(1);
    private final TalonFX m_leftFollow = new TalonFX(2);
    private final TalonFX m_rightFollow = new TalonFX(3);
    private final TalonFXConfiguration ghibusConfig = new TalonFXConfiguration();

    private final DifferentialDrive m_robotDrive =
      new DifferentialDrive(m_leftDrive::set, m_rightDrive::set);

    private final Pigeon2 m_pigeon = new Pigeon2(12);
    private final DifferentialDriveOdometry m_odometry = new DifferentialDriveOdometry(m_pigeon.getRotation2d(), nativeUnitsToDistanceMeters(m_leftDrive.getRotorPosition().getValueAsDouble()), nativeUnitsToDistanceMeters(m_rightDrive.getRotorPosition().getValueAsDouble()));;
    

    private final LTVUnicycleController ltvController = new LTVUnicycleController(0.02);

    public Drive() {
        SendableRegistry.addChild(m_robotDrive, m_leftDrive);
        SendableRegistry.addChild(m_robotDrive, m_rightDrive);

        ghibusConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

        m_leftDrive.getConfigurator().apply(ghibusConfig);
        m_leftFollow.setControl(new Follower(m_leftDrive.getDeviceID(), false));
        m_rightFollow.setControl(new Follower(m_rightDrive.getDeviceID(), false));

    }

    public Command arcadeDriveCommand(DoubleSupplier fwd, DoubleSupplier rot) {
        return run(() -> m_robotDrive.arcadeDrive(-fwd.getAsDouble(), -rot.getAsDouble(), true)).withName("arcadeGhibus");
    }

    public void followTrajectory(DifferentialSample sample){

    }

    private double nativeUnitsToDistanceMeters(double sensorCounts){
        double motorRotations = sensorCounts;
        double wheelRotations = motorRotations / 8.46;
        double positionMeters = wheelRotations * (2 * Math.PI * Units.inchesToMeters(6));
        return positionMeters;
    }
}
