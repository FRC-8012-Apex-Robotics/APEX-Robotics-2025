package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Autonomous extends SubsystemBase {
    private NetworkTable m_table;
    private Timer m_timer = new Timer();
    private double tx;
    private double ty;
    private double ta;

    public Autonomous() {
        m_table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public Command goToReef(CommandGenericHID controller, Drive drive){
            return run(() -> {
                tx = m_table.getEntry("tx").getDouble(0);
                SmartDashboard.putNumber("TX", tx);
                if (tx < 0) {
                    drive.arcadeDriveCommand(() -> 0.0, () -> 0.2);
                }
                else if(tx > 0) {
                    drive.arcadeDriveCommand(() -> 0.0, () -> -0.2);
                }
            })
            .until(() -> controller.button(3).getAsBoolean())
            .finallyDo(() -> drive.arcadeDriveCommand(() -> 0.0, () -> 0.0));
    }
}
