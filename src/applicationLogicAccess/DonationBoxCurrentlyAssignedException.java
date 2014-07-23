package applicationLogicAccess;

/**
 * @author Etibar Hasanov
 *
 */
public class DonationBoxCurrentlyAssignedException extends Exception {
	private int collectionId;
	
	public DonationBoxCurrentlyAssignedException(int collectionId) {
		this.collectionId = collectionId;
	}
	
	public int getCollectionId() {
		return collectionId;
	}
}
