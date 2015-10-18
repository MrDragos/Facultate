package model;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class Reservation extends Model{

	private SimpleStringProperty id;
	private SimpleStringProperty reservation_date;
	private SimpleStringProperty clinic_id;
	private SimpleStringProperty patient_id;
	private SimpleStringProperty medical_service_id;
	private SimpleStringProperty medic_id;
	private SimpleStringProperty weekday;
	private SimpleStringProperty timeslot;

	

    public Reservation(ArrayList<String> reservation) {
        this.id = new SimpleStringProperty(reservation.get(0));
        this.reservation_date = new SimpleStringProperty(reservation.get(1));
		this.clinic_id = new SimpleStringProperty(reservation.get(2));
		this.patient_id = new SimpleStringProperty(reservation.get(3));
		this.medical_service_id = new SimpleStringProperty(reservation.get(4));
		this.medic_id = new SimpleStringProperty(reservation.get(5));
		this.weekday = new SimpleStringProperty(reservation.get(6));
		this.timeslot = new SimpleStringProperty(reservation.get(7));
    }
    
	@Override
	public ArrayList<String> getValues() {
        ArrayList<String> values = new ArrayList<>();
        values.add(id.get());
        values.add(reservation_date.get());
        values.add(clinic_id.get());
        values.add(patient_id.get());
        values.add(medical_service_id.get());
        values.add(medic_id.get());
        values.add(weekday.get());
        values.add(timeslot.get());
        
        return values;
	}
	
	public Reservation(String id,
				String reservation_date,
				String clinic_id,
				String patient_id,
				String medical_service_id,
				String medic_id,
				String weekday,
				String timeslot) {
		
		this.id = new SimpleStringProperty(id);
		this.clinic_id = new SimpleStringProperty(clinic_id);
		this.patient_id = new SimpleStringProperty(patient_id);
		this.medical_service_id = new SimpleStringProperty(medical_service_id);
		this.reservation_date = new SimpleStringProperty(reservation_date);
		this.weekday = new SimpleStringProperty(weekday);
		this.timeslot = new SimpleStringProperty(timeslot);
		this.medic_id = new SimpleStringProperty(medic_id);
		
	}

	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id.set(id);;
	}

	public String getClinic_id() {
		return clinic_id.get();
	}

	public void setClinic_id(String clinic_id) {
		this.clinic_id.set(clinic_id);;
	}

	public String getPatient_id() {
		return patient_id.get();
	}

	public void setPatient_id(String patient_id) {
		this.patient_id.set(patient_id);;
	}

	public String getMedical_service_id() {
		return medical_service_id.get();
	}
	
	public String getMedic_id() {
		return medic_id.get();
	}

	public void setMedical_service_id(String medical_service_id) {
		this.medical_service_id.set(medical_service_id);
	}
	
	public void setMedic_id(String medic_id) {
		this.medic_id.set(medic_id);
	}

	public String getReservation_date() {
		return reservation_date.get();
	}

	public void setReservation_date(String reservation_date) {
		this.reservation_date.set(reservation_date);;
	}

	public String getWeekday() {
		return weekday.get();
	}

	public void setWeekday(String weekday) {
		this.weekday.set(weekday);;
	}

	public String getTimeslot() {
		return timeslot.get();
	}

	public void setTimeslot(String timeslot) {
		this.timeslot.set(timeslot);;
	}
	
	
	
}
