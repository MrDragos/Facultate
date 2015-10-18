package ro.pub.cs.aipi.lab07.graphicuserinterface;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import ro.pub.cs.aipi.lab07.dataaccess.DataBaseWrapper;
import ro.pub.cs.aipi.lab07.dataaccess.DataBaseWrapperImplementation;
import ro.pub.cs.aipi.lab07.general.Constants;

public class LocationGraphicUserInterface {
	final private static DataBaseWrapper dataBaseWrapper = DataBaseWrapperImplementation.getInstance();
    
	
	public static void displayLocationGraphicUserInterface(PrintWriter printWriter) {
		
		//dataBaseWrapper.setDefaultDatabase(Constants.DATABASE_NAME);
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
        content += "<h2>"+Constants.LOCATION.toUpperCase()+"</h2>\n"; 
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
        
        //obtin lista de locatii
		ArrayList<ArrayList<String>> locations = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("id");
		attributes.add("name");
		attributes.add("address");
		attributes.add("phone_number");
		attributes.add("email");
		attributes.add("timetable_id");
		String whereClause = null;  
		
		//get all locations
		try {
			locations = dataBaseWrapper.getTableContent("location", attributes, whereClause, null, null);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//for each location
		for(ArrayList<String> location : locations){
			String locationId = location.get(0);
			String locationName = location.get(1);
			String locationAddress = location.get(2);
			String locationPhoneNumber = location.get(3);
			String locationEmail = location.get(4);
			String timetableId = location.get(5);
			
			
			String textareaString = "";
		    //textareaString += "Location: \t"+locationName+"\n";
		    textareaString += "Adresa: \t\t"+locationAddress+"\n";
		    textareaString += "Numar telefon: \t\t"+locationPhoneNumber+"\n";
		    textareaString += "Email:         \t\t"+locationEmail+"\n\n";
			

			//get timetable for current location
			ArrayList<ArrayList<String>> timetable = new ArrayList<ArrayList<String>>();
			attributes = new ArrayList<String>();
			attributes.add("monday");
			attributes.add("tuesday");
			attributes.add("wednesday");
			attributes.add("thursday");
			attributes.add("friday");
			attributes.add("saturday");
			attributes.add("sunday");
			whereClause = "id='"+timetableId+"'";
		
			try {
				timetable = dataBaseWrapper.getTableContent("timetable", attributes, whereClause, null, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String luni = timetable.get(0).get(0);
			String marti = timetable.get(0).get(1);
			String miercuri = timetable.get(0).get(2);
			String joi = timetable.get(0).get(3);
			String vineri = timetable.get(0).get(4);
			String sambata = timetable.get(0).get(5);
			String duminica = timetable.get(0).get(6);
			
		    textareaString += "\t\t\t\tTimetable\n\n";
		    textareaString += "LUNI:     \t"+luni+"\n";
		    textareaString += "MARTI:    \t"+marti+"\n";
		    textareaString += "MIERCURI: \t"+miercuri+"\n";
		    textareaString += "JOI:      \t"+joi+"\n";
		    textareaString += "VINERI:   \t"+vineri+"\n";
		    textareaString += "SAMBATA:  \t"+sambata+"\n";
		    textareaString += "DUMINICA: \t"+duminica+"\n";
		    
			ArrayList<ArrayList<String>> medics = new ArrayList<ArrayList<String>>();
			attributes = new ArrayList<String>();
			attributes.add("m.id");
			attributes.add("m.first_name");
			attributes.add("m.last_name");
			attributes.add("m.speciality");
			whereClause = "gs.location='"+locationName+ "' AND gs.medic_id=m.id";
			//obtin lista de medici
			try {
				medics = dataBaseWrapper.getTableContent("generic_schedule gs,medic m", attributes, whereClause, "m.first_name", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			ArrayList<String> specialities = new ArrayList<String>();
			HashSet hs = new HashSet();
			textareaString += "\n\t\t\t\tMEDICS\n\n";
			ArrayList<String> distinctMedics = new ArrayList<String>();
			
			for(ArrayList<String> medic: medics){
				//for each medic
				String aux = "";
				
				String medicId= medic.get(0);
				String medicFirstName = medic.get(1);
				String medicLastName = medic.get(2);
				String medicSpeciality = medic.get(3);	
				
				aux += medicFirstName + " "+medicLastName+" "+"("+medicSpeciality+")";
				distinctMedics.add(aux);
				specialities.add(medicSpeciality);
				//textareaString += medicFirstName+"\t"+medicLastName+"\n";
				
			}
			
			HashSet<String> ds = new HashSet<String>();
			ds.addAll(distinctMedics);
			distinctMedics.clear();
			distinctMedics.addAll(ds);
			
			for(String medicInfo: distinctMedics){
				//for each medic
				String[] temp = medicInfo.split(" ");
				textareaString += "\t\t"+temp[0]+"\t"+temp[1]+"\t"+temp[2]+"\n";
			}
			
			textareaString += "\n\nSPECIALITIES\n\n";

			//elimin duplicatele din lista(pot exista medici cu aceeasi specialitate)
			hs.addAll(specialities);
			specialities.clear();
			specialities.addAll(hs);
			
			for(String sp:specialities){
				ArrayList<ArrayList<String>> services = new ArrayList<ArrayList<String>>();
				textareaString += "\n\t"+sp.toUpperCase()+"\n\n";
				attributes = new ArrayList<String>();
				attributes.add("ms.id");
				attributes.add("ms.name");
				attributes.add("ms.execution_time");
				attributes.add("ms.price");
				attributes.add("ms.speciality_id");
				String tableName = "medical_service ms,speciality s";
				whereClause = "s.name='"+sp+"' AND ms.speciality_id=s.id";
				try {
					services = dataBaseWrapper.getTableContent(tableName, attributes, whereClause, "ms.name", null);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(ArrayList<String> service:services){
					String serviceName = service.get(1);
					String serviceExecutionTime = service.get(2);
					String price = service.get(3);
					textareaString += "\t\t"+serviceName+"\t"+serviceExecutionTime+" (min)\t"+price+" (RON)\n";
				}
			}
			
		    content += "<center>";
		    content += "<h2>\n";
		    content += locationName;
		    content += "</h2>\n";
			content += "<textarea readonly=\"readonly\" id=\"location\" rows=\"4\" cols=\"50\">\n";
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
