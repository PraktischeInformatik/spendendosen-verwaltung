package applicationLogic;

/**
 * @author Etibar Hasanov
 *
 */
public class Login {
	private String loginName;
	private String passwort;
	private int accessLevel;
	private boolean isDeleted;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}


	public Login(String loginName, String passwort, int accessLevel,
			boolean isDeleted)  {
		this.loginName = loginName;
		this.passwort = passwort;
		this.accessLevel = accessLevel;
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "[loginName=" + loginName + ", passwort=" + passwort
				+ ", accessLevel=" + accessLevel + ", isDeleted=" + isDeleted
				+ "]";
	}

}
