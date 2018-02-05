package net.icdpublishing.exercise2.myapp.customers.service;

import net.icdpublishing.exercise2.myapp.charging.ChargingException;
import net.icdpublishing.exercise2.myapp.charging.dao.ChargingDao;
import net.icdpublishing.exercise2.myapp.customers.dao.ChargingDaoImpl;
import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao;

public class ChargingServiceTest{
	private ChargingDao chargingDao;
	private CustomerDao customerDao;
	
	public ChargingServiceTest(ChargingDao chargingDao, CustomerDao customerDao) {
		this.chargingDao = chargingDao;
		this.customerDao = customerDao;
	}
	
	public void charge(String email, int numberOfCredits) throws ChargingException {
		chargingDao.chargeForSearch(email, numberOfCredits);
	}
}
