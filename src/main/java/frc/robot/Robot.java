package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;


public class Robot extends TimedRobot {
    private Command m_autonomousCommand;

    private final RobotContainer m_container = new RobotContainer();

    public Robot(){
        m_container.configureBindings();
    }

    @Override
    public void robotPeriodic(){

        CommandScheduler.getInstance().run();
    }

    @Override
    public void autonomousInit(){
        m_autonomousCommand = m_container.getAutonomousCommand();
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
    }

    @Override 
    public void testInit() {
        m_container.resetIntakePosition();
    }
}
