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

    public static String tableNameToMenuEntry(String tableName) {
        return tableName.replaceAll("_", " ");
    }
    
  public static String dateToWeekday(String date){
    	
    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	Date data = null;
		try {
			data = formatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Calendar c = Calendar.getInstance();
    	//c.setFirstDayOfWeek(Calendar.MONDAY);
    	c.setTime(data);
    	int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
    	if(dayOfWeek == 1)
    		dayOfWeek = 7;
    	else
    		dayOfWeek--;
    	
    	switch(dayOfWeek){
	    	case 1:
	    		return "luni";
	    	case 2:
	    		return "marti";
	    	case 3:
	    		return "miercuri";
	    	case 4:
	    		return "joi";
	    	case 5:
	    		return "vineri";
	    	case 6:
	    		return "sambata";
	    	case 7:
	    		return "duminica";
	    	default:
	    		return null;
    	}
    }
  
	public static int monthNumberOfDays(int month){
		int[] days = {31,28,31,30,31,30,31,31,30,31,30,31};
		return days[month-1];
	}
	
	//genereaza lista cu datele lunii
	public static ArrayList<String> generateMonthDates(String year,String month){
		System.out.println(year);
		System.out.println(month);
		ArrayList<String> dates = new ArrayList<String>();
		int number = monthNumberOfDays(Integer.parseInt(month));
		if(month.length() == 1)
			month = "0"+month;
		
		for(int i=1;i<=number;i++){
			dates.add(year+"-"+month+"-"+i);
		}		
		return dates;		
	}
    
    public static String dateToWeekday(Date date){
    	
    	Calendar c = Calendar.getInstance();
    	//c.setFirstDayOfWeek(Calendar.MONDAY);
    	c.setTime(date);
    	int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
    	if(dayOfWeek == 1)
    		dayOfWeek = 7;
    	else
    		dayOfWeek--;
    	
    	switch(dayOfWeek){
	    	case 1:
	    		return "luni";
	    	case 2:
	    		return "marti";
	    	case 3:
	    		return "miercuri";
	    	case 4:
	    		return "joi";
	    	case 5:
	    		return "vineri";
	    	case 6:
	    		return "sambata";
	    	case 7:
	    		return "duminica";
	    	default:
	    		return null;
    	}
    }

    public static String menuEntryToTableName(String menuEntry) {
        return menuEntry.replaceAll(" ", "_");
    }

    public static String compress(ArrayList<String> record) {
        String result = record.get(0);
        for (int position = 1; position < record.size(); position++) {
            result += " / " + record.get(position);
        }
        return result;
    }

    public static ArrayList<String> decompress(String record) {
        ArrayList<String> result = new ArrayList<String>(Arrays.asList(record.split("/")));
        for (int position = 0; position < result.size(); position++) {
            result.set(position, result.get(position).trim().replace("'", "\\'"));
        }
        return result;
    }
    
    //metoda preluata din laboratorul 4
	public static String convertGregorianCalendarToString(GregorianCalendar date) {
		return date.get(Calendar.DAY_OF_MONTH)+"/"+date.get(Calendar.MONTH)+"/"+date.get(Calendar.YEAR)+" "+date.get(Calendar.HOUR)+":"+date.get(Calendar.MINUTE);
	}
}
