package frc.robot.CommandBased;

import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import frc.robot.CommandBased.subsystems.Drive;

public class RobotContainer {
    private final Drive m_drive = new Drive();

    private final CommandGenericHID m_generic = new CommandGenericHID(0);

    public void configureBindings(){
        m_drive.setDefaultCommand(
            m_drive.arcadeDriveCommand(
            () -> m_generic.getRawAxis(0), () -> Math.pow(m_generic.getRawAxis(1), 2))
            );
    }
    
}