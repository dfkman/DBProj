package table;
import javafx.beans.property.SimpleStringProperty;

public class Property {
	private SimpleStringProperty addr;
	private SimpleStringProperty sqft;
	private SimpleStringProperty seller;
	private SimpleStringProperty listP;
	private SimpleStringProperty nbed;
	private SimpleStringProperty nbath;
	private boolean isEdited; 

	public Property(String addr, String seller, String sqft, String listing, String bed, String bath) {
		this.addr = new SimpleStringProperty(addr);
		this.seller = new SimpleStringProperty(seller);
		this.sqft = new SimpleStringProperty(sqft);
		this.listP = new SimpleStringProperty(listing);
		this.nbed = new SimpleStringProperty(bed);
		this.nbath = new SimpleStringProperty(bath);
		isEdited = false;
	}
	
	public void saveToTable() {
		//save function for going back to sql
		isEdited = false;
	}

	public String getAddr(){
		return addr.get();
	}
	public String getSeller(){
		return seller.get();
	}
	public String getSqft(){
		return sqft.get();
	}
	public String getListP(){
		return listP.get();
	}
	public String getNbed(){
		return nbed.get();
	}
	public String getNbath(){
		return nbath.get();
	}
	public void setAddr(String newVal){
		addr.set(newVal);
	}
	public void setSeller(String newVal){
		seller.set(newVal);
	}
	public void setSqft(String newVal){
		sqft.set(newVal);
	}
	public void setListP(String newVal){
		listP.set(newVal);
	}
	public void setNbed(String newVal){
		nbed.set(newVal);
	}
	public void setNbath(String newVal){
		nbath.set(newVal);
	}
	
}
