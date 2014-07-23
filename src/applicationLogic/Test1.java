package applicationLogic;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import applicationLogicAccess.Access;
import applicationLogicAccess.UserNameDoesNotExistException;
import applicationLogicAccess.WrongPasswortException;
import dataAccess.DataAccess;

public class Test1 {
	public static void main(String args[]) throws SQLException, ParseException, LoginInUseException, IdInUseException, UserNameDoesNotExistException, WrongPasswortException {
		
		
        DataAccess.getInstance().initDataAccessConnection();
		DataAccess.getInstance().createBasicTables();
    	PersonManager.getInstance().loadAllPersonsFromDb();
    	
    	
    	
    	/*SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    	 Date currentTime = new Date(); 
    	System.out.println(sdf.format(Calendar.getInstance().getTime()));
    	System.out.println(Calendar.getInstance().getTime());**/
    	
    	//BoxHistoryManager.getInstance().setEndDateNull(1000, 1000);
    	
    	DonationBox DonationBoxtempBox1=DonationBoxManager.getInstance().createDonationBoxWithId(1);
    	DonationBox DonationBoxtempBox2=DonationBoxManager.getInstance().createDonationBoxWithId(2);
    	DonationBox DonationBoxtempBox3=DonationBoxManager.getInstance().createDonationBoxWithId(3);
    	DonationBox DonationBoxtempBox5=DonationBoxManager.getInstance().createDonationBoxWithId(5);
    	
    	
    	
    	
    	
    	DonationBoxManager.getInstance().addDonationBox(DonationBoxtempBox1);
    	DonationBoxManager.getInstance().addDonationBox(DonationBoxtempBox2);
    	DonationBoxManager.getInstance().addDonationBox(DonationBoxtempBox3);
    	DonationBoxManager.getInstance().addDonationBox(DonationBoxtempBox5);
    	DonationBox tempBox12=DonationBoxManager.getInstance().createDonationBoxWithLeastFreeId();
    	System.out.println(tempBox12.getBoxId());
    	DonationBoxManager.getInstance().addDonationBox(tempBox12);
    	DonationBox tempBox13=DonationBoxManager.getInstance().createDonationBoxWithLeastFreeId();
    	System.out.println(tempBox13.getBoxId());
    	System.out.println("+++++++++++++++++++++++++++");
    	
    /*	DonationBox a1=Access.createDonationBoxWithId(500);
    	System.out.println(DonationBox.getNextBoxId());
    	DonationBox a2=new DonationBox();
        DonationBoxManager.getInstance().addDonationBox(a1);
    	System.out.println(Access.idInUse(500));

    	DonationBox a3=Access.createDonationBoxWithId(500);
    	
    	System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqq");
    	System.out.println(a1.toString());
    	System.out.println(a2.toString());
    	System.out.println(a3.toString());
    	System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqq");*/
    	
	ArrayList<Person> allPersons1=PersonManager.getInstance().getAllPersons();
	Iterator<Person> it1=allPersons1.iterator();
	
	Login log=new Login("5","5",1,false);
	//gin log1=new Login("6","6",2,false);
	//Login log3=new Login("8","8",2,false);
	//System.out.println(log1.getAccessLevel());
	//System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqq");
	LoginManager logMan=new LoginManager();
	Access.loadAllLogins();
	Access.saveLogin(log);
	//Access.saveLogin(log1);
	
	System.out.println("jjjjjjjjjjjjjjjjj");
	ArrayList<Login> logins=logMan.getInstance().getAllLogins();
    Iterator<Login> it6=logins.iterator()	;
	while (it6.hasNext())  {
		System.out.println(it6.next().toString());
	}
	System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqq");
	System.out.println(logMan.getInstance().getLoginWithName("5").toString());
	
	int temp111=Access.accessLevelofLogin("1", "1");
	
	System.out.println(temp111);
	System.out.println(Access.accessLevelofLogin("6", "5"));
	System.out.println("mmmmmmmmmmmmmmmmmmmmmmmm");
	

	
	System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqq");  
	
	
	
	
	while (it1.hasNext())
	      { 		    
		   System.out.println(it1.next().toString());
	       }
	Address address1= new Address("Haardtstrasse","41", 35,
			"Bochum");
	OrganisationPerson person1=new OrganisationPerson("Hasanov", "Etibar", address1, "etibar23@mail.ru",
			"4534231", "01762345654", "person for Tests", true);
	
	Address address2= new Address("frankfurterstrasse","161",40,
			"Frankfurt");
	ContactPerson person2=new ContactPerson("Sebastian", "Huhn", address2, "huhn_sebastian@live.com",
			"42422424", "2424242424", "person for Tests", true);
	Access.saveNewOrganisationPerson(person1);
	Access.saveNewContactPerson(person2);
	

    System.out.println("_________________________________________________________________");
    System.out.println("_________________________________________________________________");
    System.out.println("_________________________________________________________________");
    
    //Access.loadAllPersons();
	ArrayList<Person> temp1 =PersonManager.getInstance().getAllPersons();
	Iterator<Person> ittemp1=temp1.iterator();
	while (ittemp1.hasNext()) {
		System.out.println(ittemp1.next().toString());
	}
	
	System.out.println("_________________________________________________________________");
    System.out.println("_________________________________________________________________");
    System.out.println("_________________________________________________________________");
    
	ArrayList<ContactPerson> temp2=PersonManager.getInstance().getAllContactPersons();
	Iterator<ContactPerson> ittemp2=temp2.iterator();
        while (ittemp2.hasNext()) {
		System.out.println(ittemp2.next().toString());
	}
	
        System.out.println("_________________________________________________________________");
        System.out.println("_________________________________________________________________");
        System.out.println("_________________________________________________________________");
        
        ArrayList<OrganisationPerson> temp3=PersonManager.getInstance().getAllOrganisationPersons();
    	Iterator<OrganisationPerson> ittemp3=temp3.iterator();
            while (ittemp3.hasNext()) {
    		System.out.println(ittemp3.next().toString());
    	}
    	
            System.out.println("_________________________________________________________________");
            System.out.println("_________________________________________________________________");
            System.out.println("_________________________________________________________________");  
	 
		 DataAccess.getInstance().initDataAccessConnection();
		 DataAccess.getInstance().createBasicTables();
	    
		 ArrayList<DonationBox> allDonationBoxes1=DonationBoxManager.getInstance().getAllBoxes();
			Iterator<DonationBox> ittemp4=allDonationBoxes1.iterator();
		 while (ittemp4.hasNext())
	      { 		    
		   System.out.println(ittemp4.next().toString());
	       }
		 System.out.println("_________________________________________________________________");
         System.out.println("_________________________________________________________________");
         System.out.println("_________________________________________________________________");
         
         DonationBox donBox1=new DonationBox();
         DonationBox donBox2=new DonationBox();
	} 
	
	
	
}
