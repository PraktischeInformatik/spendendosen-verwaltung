package applicationLogic;


/**
 * @author Etibar Hasanov
 *
 */
public class IdNotFoundException extends Exception {
	
	
	private static final long serialVersionUID = 1L;

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