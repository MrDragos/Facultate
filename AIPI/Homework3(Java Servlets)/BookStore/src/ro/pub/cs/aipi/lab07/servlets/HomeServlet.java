package ro.pub.cs.aipi.lab07.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.pub.cs.aipi.lab07.dataaccess.DataBaseWrapper;
import ro.pub.cs.aipi.lab07.dataaccess.DataBaseWrapperImplementation;
import ro.pub.cs.aipi.lab07.entities.Record;
import ro.pub.cs.aipi.lab07.general.Constants;
import ro.pub.cs.aipi.lab07.graphicuserinterface.HomeGraphicUserInterface;
import ro.pub.cs.aipi.lab07.graphicuserinterface.LocationGraphicUserInterface;
import ro.pub.cs.aipi.lab07.graphicuserinterface.MedicalServiceGraphicUserInterface;
import ro.pub.cs.aipi.lab07.graphicuserinterface.MedicalTeamGraphicUserInterface;

public class HomeServlet extends HttpServlet{
	
    private DataBaseWrapper		dataBaseWrapper;
    
    private String              selectedTable, selectedCollection, selectedDomain;
    private String              userDisplayName;
    private String              selectedSpeciality;
    
    private ArrayList<Record>	shoppingCart;
 
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dataBaseWrapper		= DataBaseWrapperImplementation.getInstance();
        selectedSpeciality = Constants.ALL;
    }

    @Override
    public void destroy() {
    	dataBaseWrapper.releaseResources();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {       
    	response.setContentType("text/html");
    	PrintWriter printWriter = response.getWriter();
    	
    	Enumeration<String> parameters = request.getParameterNames();
    	
    	String currentSpeciality = null;
    	
    	if(parameters.hasMoreElements()){
	    	String parameter = (String)parameters.nextElement(); 
	    	if (parameter.contains(Constants.LOCATION.toLowerCase())) {
	    		System.out.println(parameter);
	    		LocationGraphicUserInterface.displayLocationGraphicUserInterface(printWriter);
	    	} else if (parameter.contains(Constants.SELECTED_SPECIALITY.toLowerCase())) {
	    	    //currentSpeciality = parameter.split(".")[];
	    		System.out.println(parameter);
	    		currentSpeciality = request.getParameter(parameter);
	    		System.out.println(currentSpeciality);
	    		MedicalServiceGraphicUserInterface.displayMedicalServiceGraphicUserInterface(printWriter, currentSpeciality);
	    	}else if(parameter.contains(Constants.MEDICAL_SERVICE.toLowerCase())){
	    	    MedicalServiceGraphicUserInterface.displayMedicalServiceGraphicUserInterface(printWriter, currentSpeciality);
	    	}else if(parameter.contains(Constants.MEDICAL_TEAM.toLowerCase())){
	    		MedicalTeamGraphicUserInterface.displayMedicalTeamGraphicUserInterface(printWriter);
	    	}
    	}else{
    		HomeGraphicUserInterface.displayHomeGraphicUserInterface(printWriter);
    	}

}

	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }   
 }
