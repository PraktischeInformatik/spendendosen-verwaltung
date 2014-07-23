package applicationLogic;

public class OrgaCollectionHistory extends PersonHistory {

	private int organisationPersonId;
	private int collectionId;
	
	// Getters & Setters
		
	public int getOrganisationPersonId() {
		return organisationPersonId;
	}
	public void setOrganisationPersonId(int organisationPersonId) {
		this.organisationPersonId = organisationPersonId;
	}
	public int getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}
		
	// Constructor
	
	public OrgaCollectionHistory(int historyId, int organisationPersonId, int collectionId, String beginDate, String endDate) {
		
		super(historyId, beginDate, endDate);
		this.organisationPersonId = organisationPersonId;
		this.collectionId = collectionId;
	}
	
	@Override
	public String toString() {
		return "OrgaCollectionHistory:[" + super.toString() + ", organisationPersonId="
				+ organisationPersonId + ", collectionId=" + collectionId + "]";
	}
	
	
	
}
