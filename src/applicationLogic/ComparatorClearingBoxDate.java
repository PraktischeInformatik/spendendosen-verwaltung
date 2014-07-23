/**
 * @author KevinSchneider
 * this comparator is used to sort an ArrayList of ClearingDonationBox by date
 */

package applicationLogic;

import java.util.Comparator;

public class ComparatorClearingBoxDate implements Comparator<ClearingDonationBox> {
	@Override
	public int compare (ClearingDonationBox clear1, ClearingDonationBox clear2){
		if (clear1.getClearDate().compareTo(clear2.getClearDate()) == -1){
			System.out.println(clear1.getClearingId() + " " + clear2.getClearingId() + ": " + -1);
			return -1;
		}
		
		else if (clear1.getClearDate().compareTo(clear2.getClearDate()) == 1){
			System.out.println(clear1.getClearingId() + " " + clear2.getClearingId() + ": " + 1);
			return 1;
		}
		
		else {
			System.out.println(clear1.getClearingId() + " " + clear2.getClearingId() + ": " + 0);
			return 0;
		}
	}
}
