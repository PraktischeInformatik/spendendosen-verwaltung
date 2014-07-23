package applicationLogic;

import java.io.Serializable;

public class Address implements Serializable{
	/**
	 * @author Etibar Hasanov
	 */
	private static final long serialVersionUID = 1L;
	private String streetName;
	private String hauseNumber;
	private int zip;
	private String locationName;
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getHauseNumber() {
		return hauseNumber;
	}
	public void setHauseNumber(String hauseNumber) {
		this.hauseNumber = hauseNumber;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	

	public Address() {
		this.setStreetName("");
		this.setHauseNumber("");
		this.setLocationName("");
	}
	
	public Address(String streetName, String hauseNumber, int zip,
			String locationName) {
		super();
		this.setStreetName(streetName);
		this.setHauseNumber(hauseNumber);
		this.setZip(zip);
		this.setLocationName(locationName);
	}
	public String toString(){
		return "[Street_Name="+streetName+"Hause_Number="+hauseNumber+"ZIP="+zip+"Location_Name="+locationName+"]";
	}
        
        public String toStringTable() {
                return streetName+" "+hauseNumber+", "+zip+" "+locationName;
        }
        
	public String toStringPdf(){
		/**
		 * @author KevinSchneider
		 */
		return streetName+" "+hauseNumber+", "+zip+" "+locationName;
	}
}