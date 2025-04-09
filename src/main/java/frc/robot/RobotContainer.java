package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autos.*;

public class RobotContainer {
    private final Drive m_drive = new Drive();
    private final Intake m_intake = new Intake();
    private final AutoLeft m_auto = new AutoLeft(m_drive, m_intake);

    private final CommandGenericHID m_generic = new CommandGenericHID(0);
    private final CommandXboxController m_xbox = new CommandXboxController(1);
    public static SendableChooser<Command> sendableChooser = new SendableChooser<Command>();

    RobotContainer() {
        sendableChooser.addOption("AutoLeft", new AutoLeft(m_drive, m_intake));
        sendableChooser.addOption("AutoMiddle", new AutoMiddle());
        sendableChooser.addOption("AutoRight", new AutoRight());

        SmartDashboard.putData("Auton", sendableChooser);
    }

    // for racecar controls
    public void configureBindings(){
        m_drive.setDefaultCommand(
            m_drive.arcadeDriveCommand(
            () -> 0.61*(m_generic.getRawAxis(3) - m_generic.getRawAxis(2)),
            () -> 0.4*m_generic.getRawAxis(0))
            );


        m_generic.button(2).onTrue(m_intake.armDown());
        m_generic.button(1).onTrue(m_intake.armUp());
        m_generic.button(3).whileTrue(m_intake.spinIntakeIn());
        m_generic.button(4).whileTrue(m_intake.spinIntakeOut());
    }

    // for FPS drive controls
    public void configXboxBindings() {
        // default drive to use left stick forward
        // and right stick turn
        m_drive.setDefaultCommand(
            m_drive.arcadeDriveCommand(
                () -> -m_xbox.getLeftY() * Constants.DriveConstants.MAX_FWD_SPEED, 
                () -> m_xbox.getRightX() * Constants.DriveConstants.MAX_TURN_SPEED));

        // intake controls
        m_xbox.rightTrigger(Constants.DriveConstants.TRIGGER_DEADBAND).whileTrue(m_intake.spinIntakeIn());
        m_xbox.leftTrigger(Constants.DriveConstants.TRIGGER_DEADBAND).whileTrue(m_intake.spinIntakeOut());
        m_xbox.b().onTrue(m_intake.armDown());
        m_xbox.a().onTrue(m_intake.armUp());
    }

    public Command getAutonomousCommand(){
        DriverStation.reportWarning("Autonomous", false);
        return Commands.sequence(
            Commands.waitSeconds(Constants.AutoConstants.WAIT_SECONDS),
            m_drive.arcadeDriveCommand(() -> Constants.AutoConstants.FORWARD_SPEED, () -> 0.0)
            .raceWith(Commands.waitSeconds(Constants.AutoConstants.DRIVE_SECONDS)),
            m_intake.coral()
        );
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