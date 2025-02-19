package frc.robot.CommandBased;

import java.time.temporal.IsoFields;

import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import frc.robot.CommandBased.subsystems.Drive;
import frc.robot.CommandBased.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
    private final Drive m_drive = new Drive();
    private final Intake m_intake = new Intake();

    private final CommandGenericHID m_generic = new CommandGenericHID(0);

    public void configureBindings(){
        m_drive.setDefaultCommand(
            m_drive.arcadeDriveCommand(
            () -> 0.76*m_generic.getRawAxis(1), () -> -0.6*m_generic.getRawAxis(2))
            );


        m_generic.button(1).onTrue(m_intake.armDown());
        m_generic.button(2).onTrue(m_intake.armUp());

    }

    public Command getAutonomousCommand(){
        return m_drive.driveForTime(1.0, 0.25);
    }
}