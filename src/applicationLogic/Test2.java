package applicationLogic;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import dataAccess.DataAccess;

public class Test2 {

	public static void main(String[] 
			args) throws SQLException, ParseException {
		// TODO Auto-generated method stub
		DataAccess.getInstance().initDataAccessConnection();
		DataAccess.getInstance().createBasicTables();
		DonationBoxManager.getInstance().loadAllDonationBoxes();
		PersonManager.getInstance().loadAllPersonsFromDb();
		
		//ClearingDonationBox.setNextClearingId(30);
		ArrayList<Person> allp=PersonManager.getInstance().getAllPersons();
		OrganisationPerson person1=(OrganisationPerson) allp.get(0);
		
		ArrayList<DonationBox> alldb=DonationBoxManager.getInstance().getAllBoxes();
		//System.out.println(DonationBox.getNextBoxId());
		//int i=4;
	//	i++;
	//	DonationBox.setNextBoxId(100	);
		
		DonationBox db=new DonationBox(true,0, "1",person1, 1);
		//db.setComment("hiihgii");
		db.clearBox(Calendar.getInstance(),50);
		db.clearBox(Calendar.getInstance(),60);

		db.clearBox(Calendar.getInstance(),70);
		DonationBoxManager.getInstance().savenewDonationBox(db);
		
		DonationBox db1=new DonationBox(true,0, "Person2",person1, 1);
		db1.clearBox(Calendar.getInstance(),80);
		db1.clearBox(Calendar.getInstance(),90);
		db1.clearBox(Calendar.getInstance(),100);
		
		float endsum=db1.getEndSum();
		System.out.println("endsum="+String.valueOf(endsum));
		
	    
		
		
		DonationBoxManager.getInstance().savenewDonationBox(db1);
		
		DonationBox db2=new DonationBox(false,0, "Person3",person1, 1);
		db2.clearBox(Calendar.getInstance(),100);
		db2.clearBox(Calendar.getInstance(),110);
		DonationBoxManager.getInstance().savenewDonationBox(db2);
		
		System.out.println(db1);
		
		
		
		alldb=DonationBoxManager.getInstance().getAllBoxes();
		
		Iterator<DonationBox> it=alldb.iterator();
		while(it.hasNext()){
			DonationBox tempdb=it.next();
			//System.out.println(tempdb);	
		}
	
		Iterator<DonationBox> it14=DonationBoxManager.getInstance().searchDonationBoxWithBoxId(19, 
                DonationBoxManager.getInstance().getAllBoxes()).iterator();  
		
            while (it14.hasNext()) {System.out.println("444444444444444"+it14.next().toString());}
		
		
		Iterator<DonationBox> it15=DonationBoxManager.getInstance().searchDonationBoxWithComment("1", 
				                 DonationBoxManager.getInstance().getAllBoxes()).iterator();
				                                                      
		while (it15.hasNext()) {System.out.println("555555555555"+it15.next().toString());}
		
		
		
		Iterator<DonationBox> it16=DonationBoxManager.getInstance().getAvailableDonationBoxes().iterator();
        
           while (it16.hasNext()) System.out.println("666666666666"+it16.next().toString());
           
     
       //  Date date1=new Date();
       // Iterator<DonationBox> it17=DonationBoxManager.getInstance().
        //		                   searchDonationBoxWithClearingDate(Calendar.getInstance(),
       	//	                		   DonationBoxManager.getInstance().getAllBoxes());
          //     while (it17.hasNext()) System.out.println("666666666666"+it17.next().toString());
	}
	}

