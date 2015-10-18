package ro.pub.cs.aipi.lab07.graphicuserinterface;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import ro.pub.cs.aipi.lab07.dataaccess.DataBaseWrapper;
import ro.pub.cs.aipi.lab07.dataaccess.DataBaseWrapperImplementation;
import ro.pub.cs.aipi.lab07.general.Constants;

public class MedicalTeamGraphicUserInterface {
	final private static DataBaseWrapper dataBaseWrapper = DataBaseWrapperImplementation.getInstance();
    
	public static void displayMedicalTeamGraphicUserInterface(PrintWriter printWriter) {
    	String content = new String();
        content += "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n";
        content += "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n";
        content += "<head>\n";
        content += "<meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\" /><title>"+Constants.APPLICATION_NAME+"</title>\n";
        content += "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/bookstore.css\" />\n";
        content += "<link rel=\"icon\" type=\"image/x-icon\" href=\"./images/favicon.ico\" />\n";  
        content += "</head>\n"; 
        content += "<body>\n";
        content += "<center>\n";
        content += "<h2>"+Constants.MEDICAL_TEAM.toUpperCase()+"</h2>\n"; 
        content += "</center>\n";   
        content += "<form name=\"formular\" action=\"HomeServlet\" method=\"POST\">\n";
        
        content += "<br/>\n";
        content += "<br/>\n";
        
        
        content += "<table>\n";
        content += "<tr width=\"100%\">\n";
        
        content += "<td width=\"455px\">\n";
        content += "<center>\n";
        content += "<h3>"+Constants.LOCATION+"</h3>\n";
        content += "<div id=\"locationImage\">\n";
		content += "<input type=\"image\" src=\"./images/user_interface/rsz_location.jpg\" id=\"submit2\" name=\""+Constants.LOCATION.toLowerCase()+"\" value=\""+Constants.LOCATION.toLowerCase()+"\">\n";
        content += "</div>\n";
		content += "</center>\n";
		content += "</td>\n";

        content += "<td width=\"455px\">\n";
        content += "<center>\n";
        content += "<h3>"+Constants.MEDICAL_TEAM+"</h3>\n";
        content += "<div id=\"medicalTeamImage\">\n";
		content += "<input type=\"image\" src=\"./images/user_interface/rsz_medical-team.jpg\" id=\"submit2\" name=\""+Constants.MEDICAL_TEAM.toLowerCase()+"\" value=\""+Constants.MEDICAL_TEAM+"\">\n";
        content += "</div>\n";
        content += "</center>\n";
        content += "</td>\n";
        
        content += "<td width=\"455px\">\n";
        content += "<center>\n";
        content += "<h3>"+Constants.MEDICAL_SERVICE+"</h3>\n";
        content += "<div id=\"medicalServiceImage\">\n";
		content += "<input type=\"image\" src=\"./images/user_interface/rsz_medical_service.jpg\" id=\"submit2\" name=\""+Constants.MEDICAL_SERVICE.toLowerCase()+"\" value=\""+Constants.MEDICAL_SERVICE+"\">\n";
        content += "</div>\n";
		content += "</center>\n";
		content += "</td>\n";
		
		content += "</tr>\n";
		content += "</table>\n";
		
		content += "<br>";
		content += "</br>";
        
        //obtin lista de medici
		ArrayList<ArrayList<String>> medics = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("id");
		attributes.add("first_name");
		attributes.add("last_name");
		attributes.add("speciality");
		String whereClause = null; 
		
		try {
			medics = dataBaseWrapper.getTableContent("medic", attributes, whereClause, null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(ArrayList<String> medic:medics ){
			String medicId= medic.get(0);
			String medicFirstName = medic.get(1);
			String medicLastName = medic.get(2);
			String medicSpeciality = medic.get(3);
			String textareaString = "";
			
			ArrayList<ArrayList<String>> evaluation = new ArrayList<ArrayList<String>>();
			attributes = new ArrayList<String>();
			attributes.add("medic_id");
			attributes.add("value");
			whereClause = "medic_id="+medicId;
			try {
				evaluation = dataBaseWrapper.getTableContent("evaluation", attributes, whereClause, null, null);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			textareaString += "RATING:\t"+evaluation.get(0).get(1);
			
			textareaString += "\n\nPROGRAM\n\n";
			
			ArrayList<ArrayList<String>> genericSchedule = new ArrayList<ArrayList<String>>();
			attributes = new ArrayList<String>();
			attributes.add("weekday");
			attributes.add("time_slot");
			attributes.add("location");
			attributes.add("medic_id");
			whereClause = "medic_id="+medicId;
			try {
				genericSchedule = dataBaseWrapper.getTableContent("generic_schedule", attributes, whereClause, null, null);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			Hashtable<String, String> weekDays = new Hashtable<String, String>();
			for(ArrayList<String> schedule:genericSchedule){
				String weekday = schedule.get(0);
				String timeSlot = schedule.get(1);
				String location = schedule.get(2);
				weekDays.put(weekday, timeSlot+" "+location);
			}
			
			textareaString += "Luni    \t"+weekDays.get("luni").split(" ")[0]+"\t"+weekDays.get("luni").split(" ")[1]+"\n";
			textareaString += "Marti   \t"+weekDays.get("marti").split(" ")[0]+"\t"+weekDays.get("marti").split(" ")[1]+"\n";
			textareaString += "Miercuri\t"+weekDays.get("miercuri").split(" ")[0]+"\t"+weekDays.get("miercuri").split(" ")[1]+"\n";
			textareaString += "Joi     \t"+weekDays.get("joi").split(" ")[0]+"\t"+weekDays.get("joi").split(" ")[1]+"\n";
			textareaString += "Vineri  \t"+weekDays.get("vineri").split(" ")[0]+"\t"+weekDays.get("vineri").split(" ")[1]+"\n";
			textareaString += "Sambata \t"+weekDays.get("sambata").split(" ")[0]+"\t"+weekDays.get("sambata").split(" ")[1]+"\n";
			textareaString += "Duminica\t"+weekDays.get("duminica").split(" ")[0]+"\t"+weekDays.get("duminica").split(" ")[1]+"\n";

			textareaString += "\n\n";
			ArrayList<ArrayList<String>> concedii = new ArrayList<ArrayList<String>>();
			attributes = new ArrayList<String>();
			attributes.add("specific_date");
			attributes.add("medic_id");
			whereClause = "medic_id="+medicId;
			try {
				
				concedii = dataBaseWrapper.getTableContent("specific_schedule", attributes, whereClause, "specific_date", null);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(concedii.size()>0){
				textareaString += "Date Concediu/Vacanta\n\n";
				for(ArrayList<String> day:concedii){
					textareaString += "\t"+day.get(0)+"\n";
				}
			}
			textareaString += "\n\n";

			textareaString += "SPECIALITY : \t"+medicSpeciality+"\n\n";
			
			ArrayList<ArrayList<String>> medicalServices = new ArrayList<ArrayList<String>>();
			attributes = new ArrayList<String>();
			attributes.add("ms.name");
			attributes.add("ms.execution_time");
			attributes.add("ms.price");
			whereClause = "s.name='"+medicSpeciality+"' AND s.id=ms.speciality_id";
			try {
				medicalServices = dataBaseWrapper.getTableContent("medical_service ms,speciality s", attributes, whereClause, null, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			textareaString += "MEDICAL SERVICES\n\n";
			for(ArrayList<String> medicalService:medicalServices){
				String serviceName = medicalService.get(0);
				String serviceExecutionTime = medicalService.get(1);
				String servicePrice = medicalService.get(2);
				
				textareaString += "Name: \t"+ serviceName+"\n";
				textareaString += "Execution Time: \t"+serviceExecutionTime+"\n";
				textareaString += "Price: \t\t"+servicePrice+"\n\n";
			}
			
		    content += "<center>";
		    content += "<h2>\n";
		    content += medicFirstName+" "+medicLastName;
		    content += "</h2>\n";
			content += "<textarea readonly=\"readonly\" id=\"medic\" rows=\"4\" cols=\"50\">\n";
		    content += textareaString;
		    content += "</textarea>\n";
			content += "</center>";
			
		}
        
        content += "</form>\n";
        content += "</center>\n";
        content += "</body>\n";
        content += "</html>\n";        
        printWriter.println(content);
    }
}
