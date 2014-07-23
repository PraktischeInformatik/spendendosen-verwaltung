package applicationLogicAccess;

/**
 * @author Etibar Hasanov
 *
 */
public class IdNotFoundException extends Exception {

	public IdNotFoundException() {
	}

	
	public IdNotFoundException(String message) {
		super(message);
	}

	
	public IdNotFoundException(Throwable cause) {
		super(cause);
	}

	
	public IdNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
