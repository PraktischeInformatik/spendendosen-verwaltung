package applicationLogicAccess;

import java.util.ArrayList;

/**
 * @author Etibar Hasanov
 *
 */
public class OrganisationPersonInUseException extends Exception {
	
	private ArrayList<Integer> collections;
	
	public OrganisationPersonInUseException(ArrayList<Integer> collections)
	{
		this.collections = collections;
	}
	
	public ArrayList<Integer> getCollections() {
		return collections;
	}	

}
