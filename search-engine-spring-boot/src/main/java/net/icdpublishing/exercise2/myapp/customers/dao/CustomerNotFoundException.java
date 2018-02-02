package net.icdpublishing.exercise2.myapp.customers.dao;

public class CustomerNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4152015208726276389L;

	public CustomerNotFoundException(String message) {
		super(message);
	}
}
