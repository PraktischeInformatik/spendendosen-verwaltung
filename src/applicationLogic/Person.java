package applicationLogic;

import java.io.Serializable;

public abstract class Person implements Serializable{
	/**
	 * @author Etibar Hasanov
	 */
	private static final long serialVersionUID = 1L;
	static private int nextPersonId=0;
	private int personId;
	private String forename;
	private String lastname;
	private Address address;
	private String email;
	private String telephoneNumber;
	private String mobilNumber;
	private String comment;
	private boolean isActiveMember;
	

	public Person(){
		personId=nextPersonId;
		nextPersonId++;		
	}
	
	public Person(String forename, String lastname, Address address, String email, String telephoneNumber, 
			String mobilNumber, String comment, boolean isActiveMember){
		this();
		this.setForename(forename);
		this.setLastName(lastname);
		this.setAddress(address);
		this.setEmail(email);
		this.setTelephoneNumber(telephoneNumber);
		this.setMobilNumber(mobilNumber);
		this.setComment(comment);
		this.setIsActiveMember(isActiveMember);
	}
	
	//Sebastian
	public Person(int personId ,String forename, String lastname, Address address, String email, String telephoneNumber, 
	String mobilNumber, String comment, boolean isActiveMember){
		this.setPersonId(personId);
		this.setForename(forename);
		this.setLastName(lastname);
		this.setAddress(address);
		this.setEmail(email);
		this.setTelephoneNumber(telephoneNumber);
		this.setMobilNumber(mobilNumber);
		this.setComment(comment);
		this.setIsActiveMember(isActiveMember);
	}
	
	public static int getNextPersonId() {
		return nextPersonId;
	}
	public static void setNextPersonId(int nextPersonId) {
		Person.nextPersonId = nextPersonId;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getForename() {
		return forename;
	}
	public void setForename(String forename) {
		this.forename = forename;
	}
	public String getLastName() {
		return lastname;
	}
	public void setLastName(String lastName) {
		this.lastname = lastName;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public String getMobilNumber() {
		return mobilNumber;
	}
	public void setMobilNumber(String mobilNumber) {
		this.mobilNumber = mobilNumber;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public boolean getIsActiveMember() {
		return isActiveMember;
	}
        
        public String getIsActiveMemberForTable() {
		if (isActiveMember) {
                    return "Ja";
                } else {
                    return "Nein";
                }   
	}

	public void setIsActiveMember(boolean isActiveMember) {
		this.isActiveMember = isActiveMember;
	}
	
	public String toString(){
		return "ID="+personId+"Fore_Name="+forename+"Last_Name="+lastname+"Address="+address.toString()+"Email="+email;
	}
	
	public String toStringPdf(){
		/**
		 * @author KevinSchneider
		 */
		return forename+" "+lastname+" (ID: "+personId+")";
	}
}