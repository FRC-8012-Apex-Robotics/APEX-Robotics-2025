package frc.robot.CommandBased;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    private final RobotContainer m_container = new RobotContainer();

    public Robot(){
        m_container.configureBindings();
    }

    @Override
    public void robotPeriodic(){

        CommandScheduler.getInstance().run();
    }

}
