package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autos.*;

public class RobotContainer {
    private final Drive m_drive = new Drive();
    private final Intake m_intake = new Intake();

    private final CommandGenericHID m_generic = new CommandGenericHID(0);
    public static SendableChooser<Command> sendableChooser = new SendableChooser<Command>();

    RobotContainer() {
        sendableChooser.addOption("AutoLeft", new AutoLeft(m_drive, m_intake));
        sendableChooser.addOption("AutoMiddle", new AutoMiddle());
        sendableChooser.addOption("AutoRight", new AutoRight());

        SmartDashboard.putData("Auton", sendableChooser);
    }

    public void configureBindings(){
        m_drive.setDefaultCommand(
            m_drive.arcadeDriveCommand(
            () -> 0.76*(m_generic.getRawAxis(3) - m_generic.getRawAxis(2)), () -> 0.6*m_generic.getRawAxis(0))
            );


        m_generic.button(2).onTrue(m_intake.armDown());
        m_generic.button(1).onTrue(m_intake.armUp());
        m_generic.button(3).whileTrue(m_intake.spinIntakeIn());
        m_generic.button(4).whileTrue(m_intake.spinIntakeOut());
    }

    public Command getAutonomousCommand(){
        return sendableChooser.getSelected();
    }

    public Drive getDrive() {
        return m_drive;
    }

    public Intake getIntake() {
        return m_intake;
    }

    public void resetIntakePosition() {
        m_intake.resetArmPosition();
    }
}