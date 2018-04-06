package table;

public class House extends Property {
	int beds;
	int baths;
	public House(String addr, int sqft, float listing, int nbed, int nbath) {
		super(addr, sqft, listing);
		this.beds = nbed;
		this.baths = nbath;
	}
}
