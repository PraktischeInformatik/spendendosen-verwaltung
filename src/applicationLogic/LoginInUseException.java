package applicationLogic;

/**
 * @author Etibar Hasanov
 *
 */

public class LoginInUseException extends Exception {
	
	
	private static final long serialVersionUID = 1L;

	public LoginInUseException() {
	}

	
	public LoginInUseException(String message) {
		super(message);
	}

	
	public LoginInUseException(Throwable cause) {
		super(cause);
	}

	public LoginInUseException(String message, Throwable cause) {
		super(message, cause);
	}


}
