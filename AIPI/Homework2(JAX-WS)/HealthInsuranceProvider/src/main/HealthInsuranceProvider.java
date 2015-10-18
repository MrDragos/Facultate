package main;
import general.Constants;
import insurance.Insurance;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.bind.attachment.AttachmentMarshaller;
import javax.xml.ws.Endpoint;

import dataaccess.DataBaseWrapperImpl;


public class HealthInsuranceProvider {
	public static void main(String[] args) {
    	Endpoint.publish("http://localhost:8083/insurance/Insurance", new Insurance());
	}
}
