package entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Manufacturer")
public class Manufacturer {
	int id;
	String name;
	String description;
	String state;	//registered or unregistered
	
	public Manufacturer() {
		this.id = -1;
		this.name = null;
		this.description = null;
		this.state = null;
	}
	
	public Manufacturer(int id,String name,String description,String state) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.state = state;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
