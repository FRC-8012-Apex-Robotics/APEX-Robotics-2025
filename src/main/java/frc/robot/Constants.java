package frc.robot;

public class Constants {
    public static final int PIGEON_ID = 12;

    public class DriveConstants {
        public static final int FRONT_LEFT_ID = 0;
        public static final int FRONT_RIGHT_ID = 1;
        public static final int BACK_LEFT_ID = 2;
        public static final int BACK_RIGHT_ID = 3;
        public static final double ROTOR_WHEEL_RATIO = 8.46;
        public static final double WHEEL_DIAMETER = 6;
    }
    public class IntakeConstants {
        public static final int ARM_ID = 4;
        public static final int SPIN_ID = 5;
        public static final double INTAKE_SPEED = 0.75;
        public static final double CORAL_EJECT_SPEED = 1;
        public static final double ARM_P = 2;
        public static final double ARM_I = 0;
        public static final double ARM_D = 0.001;
        public static final double SPIN_P = 0;
        public static final double SPIN_I = 0;
        public static final double SPIN_D = 0;
        public static final double ARM_UP_POS = 0.1;
        public static final double ARM_DOWN_POS = 0.39;
        public static final double ARM_CORAL_POS = 0.001;
    }
    public class AutoConstants { //In auto, set the center wheels on the line
        public static final double WAIT_SECONDS = 5; //Time to wait before driving
        public static final double FORWARD_SPEED = 0.4;
        public static final double DRIVE_SECONDS = 2.6;
    }


}
