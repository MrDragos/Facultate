package model;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class User extends Model{

	private SimpleStringProperty personal_identifier;
	private SimpleStringProperty first_name;
	private SimpleStringProperty last_name;
	private SimpleStringProperty address;
	private SimpleStringProperty phone_number;
	private SimpleStringProperty email;
	private SimpleStringProperty IBAN_account;
	private SimpleStringProperty contract_number;
	private SimpleStringProperty hire_date;
	private SimpleStringProperty position;
	private SimpleStringProperty salary;
	private SimpleStringProperty working_hours;
	private SimpleStringProperty paraph_code;
	private SimpleStringProperty scientific_title;
	private SimpleStringProperty didactic_chair;
	private SimpleStringProperty username;
	private SimpleStringProperty password;
	

    public User(ArrayList<String> user) {
        this.personal_identifier = new SimpleStringProperty(user.get(0));
		this.first_name = new SimpleStringProperty(user.get(1));
		this.last_name = new SimpleStringProperty(user.get(2));
		this.address = new SimpleStringProperty(user.get(3));
		this.phone_number = new SimpleStringProperty(user.get(4));
		this.email = new SimpleStringProperty(user.get(5));
		this.IBAN_account = new SimpleStringProperty(user.get(6));
		this.contract_number = new SimpleStringProperty(user.get(7));
		this.hire_date = new SimpleStringProperty(user.get(8));
		this.position = new SimpleStringProperty(user.get(9));
		this.salary = new SimpleStringProperty(user.get(10));
		this.working_hours = new SimpleStringProperty(user.get(11));
		this.paraph_code = new SimpleStringProperty(user.get(12));
		this.scientific_title = new SimpleStringProperty(user.get(13));
		this.didactic_chair = new SimpleStringProperty(user.get(14));
		this.username = new SimpleStringProperty(user.get(15));
		this.password = new SimpleStringProperty(user.get(16));


    }
    
	@Override
	public ArrayList<String> getValues() {
        ArrayList<String> values = new ArrayList<>();
        values.add(personal_identifier.get());
        values.add(first_name.get());
        values.add(last_name.get());
        values.add(address.get());
        values.add(phone_number.get());
        values.add(email.get());
        values.add(IBAN_account.get());
        values.add(contract_number.get());
        values.add(hire_date.get());
        values.add(position.get());
        values.add(salary.get());
        values.add(working_hours.get());
        values.add(paraph_code.get());
        values.add(scientific_title.get());
        values.add(didactic_chair.get());
        values.add(username.get());
        values.add(password.get());
        return values;
	}
	
	public User(String personal_identifier,
				String first_name,
				String last_name,
				String address,
				String phone_number,
				String email,
				String IBAN_account,
				String contract_number,
				String hire_date,
				String position,
				String salary,
				String working_hours,
				String paraph_code,
				String scientific_title,
				String didactic_chair,
				String username,
				String password) {
		
		this.personal_identifier = new SimpleStringProperty(personal_identifier);
		this.first_name = new SimpleStringProperty(first_name);
		this.last_name = new SimpleStringProperty(last_name);
		this.address = new SimpleStringProperty(address);
		this.phone_number = new SimpleStringProperty(phone_number);
		this.email = new SimpleStringProperty(email);
		this.IBAN_account = new SimpleStringProperty(IBAN_account);
		this.contract_number = new SimpleStringProperty(contract_number);
		this.hire_date = new SimpleStringProperty(hire_date);
		this.position = new SimpleStringProperty(position);
		this.salary = new SimpleStringProperty(salary);
		this.working_hours = new SimpleStringProperty(working_hours);
		this.paraph_code = new SimpleStringProperty(paraph_code);
		this.scientific_title = new SimpleStringProperty(scientific_title);
		this.didactic_chair = new SimpleStringProperty(didactic_chair);
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		
	}
	
	public String getPersonal_identifier() {
		return personal_identifier.get();
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

	public String getIBAN_account() {
		return IBAN_account.get();
	}

	public String getContract_number() {
		return contract_number.get();
	}

	public String getHire_date() {
		return hire_date.get();
	}

	public String getPosition() {
		return position.get();
	}

	public String getSalary() {
		return salary.get();
	}

	public String getWorking_hours() {
		return working_hours.get();
	}
	
	public String getParaphCode() {
		return paraph_code.get();
	}
	
	public String getDidacticChair() {
		return didactic_chair.get();
	}
	
	public String getScientificTitle() {
		return scientific_title.get();
	}

	public String getUsername() {
		return username.get();
	}

	public String getPassword() {
		return password.get();
	}
	
    public void setPersonal_identifier(String personal_identifier) {
        this.personal_identifier.set(personal_identifier);
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

	public void setIBAN_account(String iBAN_account) {
		IBAN_account.set(iBAN_account);
	}

	public void setContract_number(String contract_number) {
		this.contract_number.set(contract_number);
	}

	public void setHire_date(String hire_date) {
		this.hire_date.set(hire_date);
	}

	public void setPosition(String position) {
		this.position.set(position);
	}

	public void setSalary(String salary) {
		this.salary.set(salary);
	}

	public void setWorking_hours(String working_hours) {
		this.working_hours.set(working_hours);
	}

	public void setUsername(String username) {
		this.username.set(username);
	}

	public void setPassword(String password) {
		this.password.set(password);
	}
	
	public void setParaphCode(String paraph_code) {
		this.paraph_code.set(paraph_code);
	}
	
	public void setDidacticChair(String didactic_chair) {
		this.didactic_chair.set(didactic_chair);
	}
	
	public void setScientificTitle(String scientificTitle) {
		this.scientific_title.set(scientificTitle);
	}


	
	
}
