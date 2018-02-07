package net.icdpublishing.exercise2.myapp.customers.exception;

public class CustomerNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4152015208726276389L;

	/**
	 * Custom exception thrown when is customer is not found
	 * @param message
	 */
	public CustomerNotFoundException(String message) {
		super(message);
	}
}
