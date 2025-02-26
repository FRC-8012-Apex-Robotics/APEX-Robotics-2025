package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.autos.*;

public class Robot extends TimedRobot {
    private Command m_autonomousCommand;

    private final RobotContainer m_container = new RobotContainer();

    public static SendableChooser<Command> sendableChooser;

    public Robot(){
        m_container.configureBindings();
    }

    @Override 
    public void robotInit() {
        sendableChooser = new SendableChooser<Command>();

        sendableChooser.addOption("AutoLeft", new AutoLeft());
        sendableChooser.addOption("AutoMiddle", new AutoMiddle());
        sendableChooser.addOption("AutoRight", new AutoRight());

        SmartDashboard.putData("Auton", sendableChooser);
    }

    @Override
    public void robotPeriodic(){

        CommandScheduler.getInstance().run();
    }

    @Override
    public void autonomousInit(){
        m_autonomousCommand = sendableChooser.getSelected();
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
    }

    @Override 
    public void testInit() {
        m_container.resetIntakePosition();
    }
}
