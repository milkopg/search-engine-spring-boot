package net.icdpublishing.exercise2.myapp.customers.dao;

import net.icdpublishing.exercise2.myapp.charging.dao.ChargingDao;

public interface CustomChargingDao extends ChargingDao{
	/**
	 * Method return current customer balance by given email 
	 * @param email valid email of customer
	 * @return account balance or null of email is not valid or null
	 */
	public Integer getCustomerBalance(String email);
}
