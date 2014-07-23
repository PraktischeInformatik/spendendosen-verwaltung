package applicationLogic;

import java.io.Serializable;

public class ContactPerson extends Person implements Serializable{
	/**
	 * @author Etibar Hasanov
	 */
	private static final long serialVersionUID = 1L;

	public ContactPerson(String forename, String lastname, Address address, String email, String telephoneNumber, 
			String mobilNumber, String comment,boolean isActiveMember){
			super(forename,lastname,address,email,telephoneNumber,mobilNumber,comment,isActiveMember);
	}
	
	//Sebastian
	public ContactPerson(int personId, String forename, String lastname, Address address, String email, String telephoneNumber, 
			String mobilNumber, String comment,boolean isActiveMember) {
		super(personId, forename, lastname, address, email, telephoneNumber, mobilNumber, comment, isActiveMember);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ContactPerson:"+super.toString();
	}
}
