package net.icdpublishing.exercise2.myapp.customers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.icdpublishing.exercise2.myapp.charging.ChargingException;
import net.icdpublishing.exercise2.myapp.charging.services.ChargingService;
import net.icdpublishing.exercise2.myapp.customers.dao.CustomChargingDao;

@Service
public class CustomChargingServiceImpl implements ChargingService {
	@Autowired
	private CustomChargingDao chargingDao;
	
	@Override
	public void charge(String email, int numberOfCredits) throws ChargingException {
		chargingDao.chargeForSearch(email, numberOfCredits);
	}
}
