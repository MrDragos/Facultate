package general;

public interface Constants {

    final public static String DATABASE_CONNECTION = "jdbc:mysql://localhost:3306/";
    final public static String DATABASE_USERNAME = "root";
    final public static String DATABASE_PASSWORD = "19381932";
    final public static String DATABASE_NAME = "Grupa342C4_SavaDragos";

    final public static boolean DEBUG = true;
    
    final public  static int SUCCESS = 1000;
    final public  static int PHYSICIAN_NOT_IN_CONTRACTUAL_RELATIONSHIP = 1001;
    final public  static int PATIENT_NOT_INSURED = 1002;
    final public  static int UNSUPPORTED_MEDICAL_SERVICE = 1003;
    final public  static int FOUNDS_EXCEEDED = 1004;
    final public  static int INFORMATION_NOT_AVAILABLE = 1005;
    
    final public  static int INVALID_REGISTRATION_ID = 100;
    final public  static int ORDER_PLACED = 101;
    final public  static int ORDER_CANCELLED = 102;

}
