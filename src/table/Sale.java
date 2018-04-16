package table;

import javafx.beans.property.SimpleStringProperty;


public class Sale {
	private SimpleStringProperty emp;
	private SimpleStringProperty buyer;
	private SimpleStringProperty seller;
	private SimpleStringProperty prop;
	private SimpleStringProperty refnum;
	private SimpleStringProperty date;
	private SimpleStringProperty price;
	
	public Sale(String emp, String buy, String seller, String prop, String ref,
				String date, String price) {
		this.emp = new SimpleStringProperty(emp);
		this.buyer = new SimpleStringProperty(buy);
		this.seller = new SimpleStringProperty(seller);
		this.prop = new SimpleStringProperty(prop);
		this.refnum = new SimpleStringProperty(ref);
		this.date = new SimpleStringProperty(date);
		this.price = new SimpleStringProperty(price);
	}

	public String getEmp(){
		return emp.get();
	}
	public String getBuyer(){
		return buyer.get();
	}
	public String getProp(){
		return prop.get();
	}
	public String getRefNum(){
		return refnum.get();
	}
	public String getPrice(){
		return price.get();
	}
	public String getDate(){
		return date.get();
	}
	public String getSeller(){
		return seller.get();
	}
	public void setEmp(String newValue){
		emp.set(newValue);
	}
	public void setBuyer(String newValue){
		buyer.set(newValue);
	}
	public void setSeller(String newValue){
		seller.set(newValue);
	}
	public void setProp(String newValue){
		prop.set(newValue);
	}
	public void setPrice(String newValue){
		price.set(newValue);
	}
	public void setDate(String newValue){
		date.set(newValue);
	}
	public void setRefnum(String newValue){
		refnum.set(newValue);
	}
	
}
