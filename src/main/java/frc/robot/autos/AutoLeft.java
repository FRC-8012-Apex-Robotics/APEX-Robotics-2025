package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;

import frc.robot.Constants;

public class AutoLeft extends SequentialCommandGroup{

    public AutoLeft(Drive drive, Intake intake) {
        
        Commands.sequence(
            Commands.waitSeconds(Constants.AutoConstants.WAIT_SECONDS),
            drive.arcadeDriveCommand(() -> Constants.AutoConstants.FORWARD_SPEED, () -> 0.0)
            .raceWith(Commands.waitSeconds(Constants.AutoConstants.DRIVE_SECONDS)),
            intake.coral()
        );
    }

    
}
