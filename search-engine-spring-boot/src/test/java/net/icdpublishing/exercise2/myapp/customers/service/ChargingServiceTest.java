package net.icdpublishing.exercise2.myapp.customers.service;

import net.icdpublishing.exercise2.myapp.charging.ChargingException;
import net.icdpublishing.exercise2.myapp.charging.dao.ChargingDao;

public class ChargingServiceTest extends ChargingServiceImpl{
	private ChargingDao chargingDao;
	
	public ChargingServiceTest(ChargingDao chargingDao) {
		this.chargingDao = chargingDao;
	}
	
	@Override
	public void charge(String email, int numberOfCredits) throws ChargingException {
		chargingDao.chargeForSearch(email, numberOfCredits);
	}
}
