package entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Offer")
public class Offer {
	int id;
	String deliveryDate;
	int price;
	
	public Offer() {
		this.id = -1;
		this.deliveryDate = null;
		this.price = -1;
	}
	
	public Offer(int id,String deliveryDate,int price){
		this.id = id;
		this.deliveryDate = deliveryDate;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	

}
