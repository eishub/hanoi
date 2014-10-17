package hanoi.exceptions;

/**
 * Custom Exception to indicate an operation on an empty Pin.
 * 
 * @author Sander van den Oever
 */
public class EmptyPinException extends Exception {
	/**
	 * Parameterless constructor.
	 */
	public EmptyPinException() {

	}

	/**
	 * Constructor, creates an Exception with the given message.
	 * 
	 * @param message
	 *            information indicating the source of the Exception.
	 */
	public EmptyPinException(String message) {
		super(message);
	}
}
