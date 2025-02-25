package frc.robot.CommandBased;

import java.time.temporal.IsoFields;

import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import frc.robot.CommandBased.subsystems.Drive;
import frc.robot.CommandBased.subsystems.Intake;
import frc.robot.CommandBased.subsystems.Autonomous;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
    private final Drive m_drive = new Drive();
    private final Intake m_intake = new Intake();
    private final Autonomous m_autonomous =new Autonomous();

    private final CommandGenericHID m_generic = new CommandGenericHID(0);

    public void configureBindings(){
        m_drive.setDefaultCommand(
            m_drive.arcadeDriveCommand(
            () -> 0.76*(m_generic.getRawAxis(3) - m_generic.getRawAxis(2)), () -> 0.6*m_generic.getRawAxis(0))
            );


        //m_generic.button(2).onTrue(m_intake.armDown());
        //m_generic.button(1).onTrue(m_intake.armUp());
        m_generic.button(3).whileTrue(m_intake.spinIntakeIn());
    }

    public Command getAutonomousCommand(){
        return m_autonomous.goToReef(m_generic, m_drive);
    }

    public void resetIntakePosition() {
        m_intake.resetArmPosition();
    }
}