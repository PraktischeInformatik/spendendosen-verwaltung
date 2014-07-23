package applicationLogic;

public class OrgaBoxHistory extends PersonHistory{
	
	private int organisationPersonId;
	private int boxId;
	
	// Getters & Setters
	
	public int getOrganisationPersonId() {
		return organisationPersonId;
	}
	public void setOrganisationPersonId(int organisationPersonId) {
		this.organisationPersonId = organisationPersonId;
	}
	public int getBoxId() {
		return boxId;
	}
	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}
	
	// Constructor
	
	public OrgaBoxHistory(int historyId, int organisationPersonId, int boxId, String beginDate, String endDate) {
		
		super(historyId, beginDate, endDate);
		this.organisationPersonId = organisationPersonId;
		this.boxId = boxId;
	}
	
	@Override
	public String toString() {
		return "OrgaBoxHistory:["+ super.toString() + ", organisationPersonId=" + organisationPersonId
				+ ", boxId=" + boxId + "]";
	}
	
	
	

}
