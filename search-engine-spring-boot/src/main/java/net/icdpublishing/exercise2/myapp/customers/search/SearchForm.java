package net.icdpublishing.exercise2.myapp.customers.search;

/**
 * Helper class to put all search fields - email, surname, postcode to one class
 * @author Milko Galev
 *
 */
public class SearchForm {
	
	private String email;
	private String surname;
	private String postcode;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
}
