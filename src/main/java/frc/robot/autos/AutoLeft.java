package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;

public class AutoLeft extends SequentialCommandGroup{

    public AutoLeft(Drive drive, Intake intake) {
        drive.arcadeDriveCommand(null, null);
    }
}
