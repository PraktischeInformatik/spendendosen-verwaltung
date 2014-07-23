package applicationLogic;

import java.io.Serializable;

public class OrganisationPerson extends Person implements Serializable{
	/**
	 * @author Etibar Hasanov
	 */
	private static final long serialVersionUID = 1L;
	
	
	public OrganisationPerson(String forename, String lastname, Address address, String email, String telephoneNumber, 
			String mobilNumber, String comment, boolean isActiveMember){
			super(forename, lastname, address, email, telephoneNumber, 
					mobilNumber, comment,isActiveMember);
	}

	//Sebastian
	public OrganisationPerson(int personId ,String forename, String lastname, Address address, String email, String telephoneNumber, 
			String mobilNumber, String comment, boolean isActiveMembe){
		super(personId, forename, lastname, address, email, telephoneNumber, mobilNumber, comment, isActiveMembe);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "OrganisationPerson:"+super.toString();
	}
	
}