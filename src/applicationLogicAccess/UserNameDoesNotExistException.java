package applicationLogicAccess;

/**
 * @author Etibar Hasanov
 *
 */
public class UserNameDoesNotExistException extends Exception {

private static final long serialVersionUID = 1L;

	
	public UserNameDoesNotExistException() {
	}

	
	public UserNameDoesNotExistException(String message) {
		super(message);
	}

	
	public UserNameDoesNotExistException(Throwable cause) {
		super(cause);
	}

	
	public UserNameDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	
}
