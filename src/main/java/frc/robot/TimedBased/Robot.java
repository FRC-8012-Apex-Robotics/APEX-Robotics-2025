// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.TimedBased;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.Follower;
import java.lang.Math;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private final TalonFX m_leftDrive = new TalonFX(0);
  private final TalonFX m_rightDrive = new TalonFX(1);
  private final TalonFX m_leftFollow = new TalonFX(2);
  private final TalonFX m_rightFollow = new TalonFX(3);
  private final DifferentialDrive m_robotDrive =
      new DifferentialDrive(m_leftDrive::set, m_rightDrive::set);
  private final XboxController m_controller = new XboxController(0);
  private int controlMode = 0;
  private final Timer m_timer = new Timer();

  public Robot() {
    SendableRegistry.addChild(m_robotDrive, m_leftDrive);
    SendableRegistry.addChild(m_robotDrive, m_rightDrive);
  }

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightDrive.setInverted(true);
    m_leftFollow.setControl(new Follower(m_leftDrive.getDeviceID(), false));
    m_rightFollow.setControl(new Follower(m_rightDrive.getDeviceID(), false));
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.restart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      m_robotDrive.arcadeDrive(0.5, 0.0, false);
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    if (m_controller.getRightBumperReleased()) {
      controlMode += 1;
    }
    if (controlMode >= 3) {
      controlMode = 0;
    }
    if (controlMode == 1) {
      double turn = -m_controller.getLeftX();
      m_robotDrive.arcadeDrive(-m_controller.getLeftY(), (Math.pow(0.1, (1-turn))+Math.pow(0.1, (1+turn)) - 0.2));
    }
    if (controlMode == 0) {
      double turn = -m_controller.getRightX();
      m_robotDrive.arcadeDrive(-m_controller.getLeftY(), (Math.pow(0.1, (1-turn))+Math.pow(0.1, (1+turn)) - 0.2));
    }
    if (controlMode == 2) {
      double turn = -m_controller.getLeftX();
      m_robotDrive.arcadeDrive(m_controller.getRightTriggerAxis()-m_controller.getLeftTriggerAxis(), (Math.pow(0.1, (1-turn))+Math.pow(0.1, (1+turn)) - 0.2));
    }
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
