package applicationLogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import dataAccess.DataAccess;


/**
 * @author Etibar
 *
 */

public class LoginManager {

	private ArrayList<Login> allLogins;
	private static LoginManager instance = null;
	private static int accessLevelOfProgramm = 1;

	public static int getAccessLevelOfProgramm() {
		return accessLevelOfProgramm;
	}

	public static void setAccessLevelOfProgramm(int accessLevelOfProgramm) {
		LoginManager.accessLevelOfProgramm = accessLevelOfProgramm;
	}

	public ArrayList<Login> getAllLogins() {
		return allLogins;
	}

	public void setAllLogins(ArrayList<Login> allLogins) {
		this.allLogins = allLogins;
	}

	public LoginManager() {
		allLogins = new ArrayList<Login>();
	}

	public static LoginManager getInstance() {
		if (instance == null)
			instance = new LoginManager();
		return instance;
	}

	/**
	 * @author Etibar Hasanov
	 * @param loginName
	 * @return
	 */
	public Login getLoginWithName(String loginName) {
		Login result = null;
		ArrayList<Login> allLogins = LoginManager.getInstance().getAllLogins();
		Iterator<Login> itLogin = allLogins.iterator();
		while (itLogin.hasNext()) {
			Login tempLogin = itLogin.next();
			if (tempLogin.getLoginName().equals(loginName))
				result = tempLogin;
		}
		return result;
	}

	/**
	 * @author Etibar Hasanov
	 * @param loginName
	 * @param passwort
	 * @param accessLevel
	 * @param isDeleted
	 */
	public void changeLogin(String loginName, String passwort, int accessLevel,
			boolean isDeleted) {
		// find Login with loginName and change passwort , accesLevel and
		// isDeleted
		Login resultLogin;
		resultLogin = LoginManager.getInstance().getLoginWithName(loginName);
		resultLogin.setPasswort(passwort);
		resultLogin.setAccessLevel(accessLevel);
		resultLogin.setDeleted(isDeleted);
		return;
	}


	/**
	 * @author Etibar Hasanov
	 * @param loginName
	 * @param passwort
	 * @throws SQLException
	 */

	public void changePasswortOfLogin(String loginName, String passwort)
			throws SQLException {
		// find Login with loginName and change passwort , accesLevel and
		// isDeleted
		Login resultLogin;
		resultLogin = LoginManager.getInstance().getLoginWithName(loginName);
		resultLogin.setPasswort(passwort);
		DataAccess.getInstance().changePasswordByLoginname(loginName, passwort);
		return;
	}

	/**
	 * @author Etibar Hasanov
	 * @param loginName
	 * @throws SQLException
	 * @throws LoginInUseException
	 */
	public void deleleLogin(String loginName) throws SQLException,
			LoginInUseException {

		Login tempLogin;
		tempLogin = LoginManager.getInstance().getLoginWithName(loginName);
		tempLogin.setDeleted(true);
		DataAccess.getInstance().deleteLoginByLoginname(loginName);
		LoginManager.getInstance().loadAllLogins();
	}
	
	//added by sz
	/**
	 * @author Sebastian
	 * deletes the login by name from db
	 * @param loginName the loginname
	 * @throws SQLException
	 * @throws LoginInUseException
	 */
	public static void deleteLoginFromDB(String loginName) throws SQLException, LoginInUseException{
		DataAccess.getInstance().deleteLoginByLoginnameFromDB(loginName);
		LoginManager.getInstance().loadAllLogins();
	}

	// genauso wie in DonationBoxManager oder PersonManager , ohne Referencen (:

	/**
	 * @author Etibar Hasanov
	 * @throws SQLException
	 * @throws LoginInUseException
	 */

	public void loadAllLogins() throws SQLException, LoginInUseException {
		ArrayList<Login> tempAllLogins = DataAccess.getInstance()
				.getLoginsFromDB();
		LoginManager.getInstance().setAllLogins(tempAllLogins);

	}


	/**
	 * @author Etibar Hasanov
	 * @param login
	 * @throws SQLException
	 * @throws LoginInUseException
	 */

	public void saveLogin(Login login) throws SQLException, LoginInUseException {
		DataAccess.getInstance().storeLoginIntoDB(login);
		LoginManager.getInstance().loadAllLogins();
	}


	/**
	 * @author Etibar Hasanov
	 * @param loginName
	 * @param accessLevel
	 * @throws SQLException
	 * @throws LoginInUseException
	 */

	public void changeAccessLevel(String loginName, int accessLevel)
			throws SQLException, LoginInUseException {
		Login tempLogin;
		tempLogin = LoginManager.getInstance().getLoginWithName(loginName);
		tempLogin.setAccessLevel(accessLevel);
		;
		DataAccess.getInstance().setAccesslevelInLoginByLoginname(loginName,
				accessLevel);
		LoginManager.getInstance().loadAllLogins();
	}


	/**
	 * @author Etibar Hasanov
	 * @param loginName
	 * @param passwort
	 * @param accessLevel
	 * @return
	 * @throws LoginInUseException
	 */

	public Login createLogin(String loginName, String passwort, int accessLevel)
			throws LoginInUseException {
		if (loginInUse(loginName) == true)
			throw new LoginInUseException();
		return new Login(loginName, passwort, accessLevel, false);
	}


	/**
	 * @author Etibar Hasanov
	 * @param loginName
	 * @return
	 */

	public boolean loginInUse(String loginName) {
		boolean result = false;
		ArrayList<Login> tempLogins = LoginManager.getInstance().getAllLogins();
		Iterator<Login> tempIt = tempLogins.iterator();
		while (tempIt.hasNext()) {
			if (tempIt.next().getLoginName().equals(loginName))
				result = true;
		}

		return result;
	}

	

}
