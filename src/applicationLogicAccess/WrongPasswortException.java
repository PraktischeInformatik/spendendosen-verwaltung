package applicationLogicAccess;

/**
 * @author Etibar Hasanov
 *
 */
public class WrongPasswortException extends Exception{

private static final long serialVersionUID = 1L;

	
	public WrongPasswortException() {
	}

	
	public WrongPasswortException(String message) {
		super(message);
	}

	
	public WrongPasswortException(Throwable cause) {
		super(cause);
	}

	
	public WrongPasswortException(String message, Throwable cause) {
		super(message, cause);
	}

	
}
