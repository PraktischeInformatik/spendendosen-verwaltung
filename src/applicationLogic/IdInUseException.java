package applicationLogic;

public class IdInUseException extends Exception {
	
	/**
	 * @author Etibar Hasanov
	 */
	private static final long serialVersionUID = 1L;

	public IdInUseException() {
	}

	
	public IdInUseException(String message) {
		super(message);
	}

	
	public IdInUseException(Throwable cause) {
		super(cause);
	}

	public IdInUseException(String message, Throwable cause) {
		super(message, cause);
	}


}
