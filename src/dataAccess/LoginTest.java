package dataAccess;

import java.sql.SQLException;

import applicationLogic.Login;
import applicationLogic.LoginInUseException;

/**
 * testclass for login
 * @author Sebastian
 *
 */
public class LoginTest {

	public static void main(String[] args) throws SQLException, LoginInUseException {
		
		DataAccess.getInstance().initDataAccessConnection();
		DataAccess.getInstance().createBasicTables();
		
		DataAccess.getInstance().storeLoginIntoDB(new Login("test", "pw", 1, false));
		DataAccess.getInstance().storeLoginIntoDB(new Login("test2", "pw", 3, false));
		DataAccess.getInstance().storeLoginIntoDB(new Login("test3", "pw", 2, false));
		DataAccess.getInstance().storeLoginIntoDB(new Login("test4", "pw", 2, false));
		
		System.out.println(DataAccess.getInstance().getLoginsFromDB());
		
		System.out.println(DataAccess.getInstance().getLoginByLoginname("test3"));
		
		DataAccess.getInstance().deleteLoginByLoginname("test");
		DataAccess.getInstance().deleteLoginByLoginnameFromDB("test");
		DataAccess.getInstance().setAccesslevelInLoginByLoginname("test", 3);
		DataAccess.getInstance().changePasswordByLoginname("test", "milch");
		
		System.out.println(DataAccess.getInstance().getLoginsFromDB());
		
	}

}
