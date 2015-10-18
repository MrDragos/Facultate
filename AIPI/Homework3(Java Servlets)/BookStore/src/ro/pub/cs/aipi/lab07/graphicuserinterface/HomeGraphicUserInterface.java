package ro.pub.cs.aipi.lab07.graphicuserinterface;

import java.io.PrintWriter;
import java.util.ArrayList;

import ro.pub.cs.aipi.lab07.dataaccess.DataBaseWrapper;
import ro.pub.cs.aipi.lab07.dataaccess.DataBaseWrapperImplementation;
import ro.pub.cs.aipi.lab07.general.Constants;

public class HomeGraphicUserInterface {
	final private static DataBaseWrapper dataBaseWrapper = DataBaseWrapperImplementation.getInstance();
    public static void displayHomeGraphicUserInterface(PrintWriter printWriter) {
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
        content += "<h2>"+"WELCOME !!!"+"</h2>\n"; 
        content += "</center>\n";   
        content += "<form name=\"formular\" action=\"HomeServlet\" method=\"POST\">\n";
        content += "<br></br>";
        //System.out.println("Afisare");
        content += "<center>\n";
        content += "<h3>"+Constants.LOCATION+"</h3>\n";
        content += "<div id=\"locationImage\">\n";
		content += "<input type=\"image\" src=\"./images/user_interface/location.jpg\" id=\"submit2\" name=\""+Constants.LOCATION.toLowerCase()+"\" value=\""+Constants.LOCATION.toLowerCase()+"\">\n";
        content += "</div>\n";
		content += "</center>\n";
		
        content += "<br/>\n";
        content += "<br/>\n";
		
        content += "<center>\n";
        content += "<h3>"+Constants.MEDICAL_TEAM+"</h3>\n";
        content += "<div id=\"medicalTeamImage\">\n";
		content += "<input type=\"image\" src=\"./images/user_interface/medical-team.jpg\" id=\"submit2\" name=\""+Constants.MEDICAL_TEAM.toLowerCase()+"\" value=\""+Constants.MEDICAL_TEAM+"\">\n";
        content += "</div>\n";
        content += "</center>\n";
        
        content += "<br/>\n";
        content += "<br/>\n";
        
        content += "<center>\n";
        content += "<h3>"+Constants.MEDICAL_SERVICE+"</h3>\n";
        content += "<div id=\"medicalServiceImage\">\n";
		content += "<input type=\"image\" src=\"./images/user_interface/medical_service.jpg\" id=\"submit2\" name=\""+Constants.MEDICAL_SERVICE.toLowerCase()+"\" value=\""+Constants.MEDICAL_SERVICE+" onclick=\"form.action= \"MedicalServiceServlet\"; form.method=\"POST\" ;\""+"\">\n";
        content += "</div>\n";
		content += "</center>\n";
		
		content += "</form>\n";
        content += "</body>\n";
        content += "</html>\n";        
        printWriter.println(content);
    }
}
