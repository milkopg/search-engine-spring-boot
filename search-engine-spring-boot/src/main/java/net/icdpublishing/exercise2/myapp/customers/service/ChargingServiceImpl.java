package net.icdpublishing.exercise2.myapp.customers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.icdpublishing.exercise2.myapp.charging.ChargingException;
import net.icdpublishing.exercise2.myapp.charging.dao.ChargingDao;
import net.icdpublishing.exercise2.myapp.charging.services.ChargingService;

@Service
public class ChargingServiceImpl implements ChargingService {
	@Autowired
	private ChargingDao chargingDao;
	
	@Override
	public void charge(String email, int numberOfCredits) throws ChargingException {
		chargingDao.chargeForSearch(email, numberOfCredits);
	}
}
