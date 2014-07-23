package applicationLogic;

public class ContactPersonHistory extends PersonHistory{
	
	private int contactPersonId;
	private int collectionId;
	
	// Getters & Setters
	
	public int getContactPersonId() {
		return contactPersonId;
	}
	
	public void setContactPersonId(int contactPersonId) {
		this.contactPersonId = contactPersonId;
	}
	
	public int getCollectionId() {
		return collectionId;
	}
	
	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}
	
	// Constructor

	public ContactPersonHistory(int historyId, int contactPersonId, int collectionId, String beginDate, String endDate) {
		super(historyId, beginDate, endDate);
		this.contactPersonId = contactPersonId;
		this.collectionId = collectionId;
	}

	@Override
	public String toString() {
		return "ContactPersonHistory:[ " + super.toString() + ", contactPersonId=" + contactPersonId
				+ ", collectionId=" + collectionId + "]";
	}
	
	
	

}
