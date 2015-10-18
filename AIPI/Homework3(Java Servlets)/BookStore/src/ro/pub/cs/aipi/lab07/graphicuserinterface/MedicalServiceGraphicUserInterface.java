package ro.pub.cs.aipi.lab07.graphicuserinterface;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import ro.pub.cs.aipi.lab07.dataaccess.DataBaseWrapper;
import ro.pub.cs.aipi.lab07.dataaccess.DataBaseWrapperImplementation;
import ro.pub.cs.aipi.lab07.general.Constants;



public class MedicalServiceGraphicUserInterface {
	final private static DataBaseWrapper dataBaseWrapper = DataBaseWrapperImplementation.getInstance();
	
	public static ArrayList<String> getDistinctContent(String tableName, String attribute) {
	    ArrayList<String> result = new ArrayList<>();
	    result.add(Constants.ALL);
	    try {
	        ArrayList<String> attributes = new ArrayList<>();
	        attributes.add("DISTINCT("+attribute+")");
	        ArrayList<ArrayList<String>> tableContent = dataBaseWrapper.getTableContent(tableName, attributes, null, null, null);
	        for (ArrayList<String> tableRow: tableContent)
	            result.add(tableRow.get(0).toString());
	    } catch (SQLException sqlException) {
	        System.out.println("An exception has occurred: "+sqlException.getMessage());
	        if (Constants.DEBUG)
	        	sqlException.printStackTrace();
	    }
	    return result;
	}

    public static void displayMedicalServiceGraphicUserInterface(PrintWriter printWriter,String currentSpeciality) {
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
        content += "<h2>"+"MEDICAL SERVICES"+"</h2>\n"; 
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
		
        content += "<select name=\"selectedspeciality\" onchange=\"document.formular.submit()\" style=\"width: 10%\">\n";
        for (String collection: getDistinctContent(Constants.SPECIALITIES_TABLE,"name"))
            if (collection.equals(currentSpeciality))
                content += "<option value=\""+collection+"\" SELECTED>"+collection+"</option>\n";
            else
                content += "<option value=\""+collection+"\">"+collection+"</option>\n";
        content += "</select>\n";
        
		ArrayList<ArrayList<String>> medicalServices = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();

		String tableName;
		String whereClause;
		if(currentSpeciality == null){
			whereClause = null;
			tableName = "medical_service";
			attributes.add("id");
			attributes.add("name");
			attributes.add("execution_time");
			attributes.add("price");
			attributes.add("speciality_id");
		}else if(currentSpeciality.equals(Constants.ALL.toLowerCase())){
			whereClause = null;
			tableName = "medical_service";
			attributes.add("id");
			attributes.add("name");
			attributes.add("execution_time");
			attributes.add("price");
			attributes.add("speciality_id");
		}else{
			attributes.add("ms.id");
			attributes.add("ms.name");
			attributes.add("ms.execution_time");
			attributes.add("ms.price");
			attributes.add("ms.speciality_id");
			tableName = "medical_service ms,speciality s";
			whereClause = "s.name='"+currentSpeciality+"' AND ms.speciality_id=s.id";
		}
		
		try {
			medicalServices = dataBaseWrapper.getTableContent(tableName, attributes, whereClause, null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(ArrayList<String> medicalService:medicalServices){
			
			String serviceName = medicalService.get(1);
			String serviceExecutionTime = medicalService.get(2);
			String price = medicalService.get(3);
			String textareaString ="";
			
			textareaString += "Execution Time:      \t"+serviceExecutionTime+" minute\n";
			textareaString += "Price: \t\t\t"+price+" RON\n";
			
		    content += "<center>";
		    content += "<h2>\n";
		    content += serviceName;
		    content += "</h2>\n";
			content += "<textarea readonly=\"readonly\" id=\"service\" rows=\"4\" cols=\"50\">\n";
		    content += textareaString;
		    content += "</textarea>\n";
			content += "</center>";
		}
	
        content += "</form>\n";
        content += "</center>\n";
        content += "</body>\n";
        content += "</html>\n"; 
        System.out.println("A fost apasat");
        printWriter.println(content);
    }
}
