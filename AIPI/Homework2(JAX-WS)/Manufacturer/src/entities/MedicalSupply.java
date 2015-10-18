package entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="MedicalSupply")
public class MedicalSupply {
	int id;
	String name;
	int manufacturerId;
	String description;
	int price;
	int stock;
	int executionTime;
	
	public MedicalSupply() {
		this.id = -1;
		this.name = null;
		this.description = null;
		this.manufacturerId = -1;
		this.price = -1;
		this.stock = -1;
		this.executionTime = -1;
	}
	
	public MedicalSupply(int id,
						String name,
						int manufacturerId, 
						String description,
						int price,
						int stock,
						int executionTime) {
		this.id = id;
		this.name = name;
		this.manufacturerId = manufacturerId;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.executionTime = executionTime;
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

	public int getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(int manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(int executionTime) {
		this.executionTime = executionTime;
	}
	

}
