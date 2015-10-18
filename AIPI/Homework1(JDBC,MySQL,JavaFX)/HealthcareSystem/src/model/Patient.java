package model;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class Patient extends Model{

	private SimpleStringProperty id;
	private SimpleStringProperty first_name;
	private SimpleStringProperty last_name;
	private SimpleStringProperty address;
	private SimpleStringProperty phone_number;
	private SimpleStringProperty email;
	private SimpleStringProperty registered;

	

    public Patient(ArrayList<String> patient) {
        this.id = new SimpleStringProperty(patient.get(0));
		this.first_name = new SimpleStringProperty(patient.get(1));
		this.last_name = new SimpleStringProperty(patient.get(2));
		this.address = new SimpleStringProperty(patient.get(3));
		this.phone_number = new SimpleStringProperty(patient.get(4));
		this.email = new SimpleStringProperty(patient.get(5));
		this.registered = new SimpleStringProperty(patient.get(6));
    }
    
	@Override
	public ArrayList<String> getValues() {
        ArrayList<String> values = new ArrayList<>();
        values.add(id.get());
        values.add(first_name.get());
        values.add(last_name.get());
        values.add(address.get());
        values.add(phone_number.get());
        values.add(email.get());
        values.add(registered.get());
        
        return values;
	}
	
	public Patient(String id,
				String first_name,
				String last_name,
				String address,
				String phone_number,
				String email,
				String registered) {
		
		this.id = new SimpleStringProperty(id);
		this.first_name = new SimpleStringProperty(first_name);
		this.last_name = new SimpleStringProperty(last_name);
		this.address = new SimpleStringProperty(address);
		this.phone_number = new SimpleStringProperty(phone_number);
		this.email = new SimpleStringProperty(email);
		this.registered = new SimpleStringProperty(registered);
	}
	
	public String getPersonal_identifier() {
		return id.get();
	}

	public String getFirst_name() {
		return first_name.get();
	}

	public String getLast_name() {
		return last_name.get();
	}

	public String getAddress() {
		return address.get();
	}

	public String getPhone_number() {
		return phone_number.get();
	}

	public String getEmail() {
		return email.get();
	}
	
	public String getRegistered() {
		return registered.get();
	}
	
    public void setPersonal_identifier(String id) {
        this.id.set(id);
    }

	public void setFirst_name(String first_name) {
		this.first_name.set(first_name);
	}

	public void setLast_name(String last_name) {
		this.last_name.set(last_name);
	}

	public void setAddress(String address) {
		this.address.set(address);
	}

	public void setPhone_number(String phone_number) {
		this.phone_number.set(phone_number);
	}

	public void setEmail(String email) {
		this.email.set(email);
	}
	

	public void setRegistered(String registered) {
		this.registered.set(registered);
	}
	
}
