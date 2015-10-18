package general;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utilities {

	public static void printResult(int result){
		switch(result){
			case Constants.INFORMATION_NOT_AVAILABLE:
				System.out.println("INFORMATION_NOT_AVAILABLE");
				break;
			case Constants.FOUNDS_EXCEEDED:
				System.out.println("FOUNDS_EXCEEDED");
				break;
			case Constants.PATIENT_NOT_INSURED:
				System.out.println("PATIENT_NOT_INSURED");
				break;
			case Constants.PHYSICIAN_NOT_IN_CONTRACTUAL_RELATIONSHIP:
				System.out.println("PHYSICIAN_NOT_IN_CONTRACTUAL_RELATIONSHIP");
				break;
			case Constants.UNSUPPORTED_MEDICAL_SERVICE:
				System.out.println("UNSUPPORTED_MEDICAL_SERVICE");
				break;
			case Constants.SUCCESS:
				System.out.println("SUCCESS");
				break;
		}
	}
    
    //metoda preluata din laboratorul 4
	public static String convertGregorianCalendarToString(GregorianCalendar date) {
		return date.get(Calendar.DAY_OF_MONTH)+"/"+date.get(Calendar.MONTH)+"/"+date.get(Calendar.YEAR)+" "+date.get(Calendar.HOUR)+":"+date.get(Calendar.MINUTE);
	}
}
