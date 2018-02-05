package net.icdpublishing.exercise2.myapp.customers.dao;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import net.icdpublishing.exercise2.myapp.customers.dao.ChargingDaoImpl;
import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao;
import net.icdpublishing.exercise2.myapp.customers.domain.Customer;
import net.icdpublishing.exercise2.myapp.customers.domain.CustomerType;

public class ChargingDaoImplTest {
	private ChargingDaoImpl chargingDao;
	private CustomerDao customerDao;
	private Map <String, Customer> customerMap;
	private ConcurrentHashMap<String, Integer> premiumCustomersMap = new ConcurrentHashMap<>();
	
	public ChargingDaoImplTest(ChargingDaoImpl chargingDao, CustomerDao customerDao) {
		this.chargingDao = chargingDao;
		this.customerDao = customerDao;
		customerMap = customerDao.getCustomersMap();
		initPurchaseCredits();
	}

	private void initPurchaseCredits() {
		for (Entry <String, Customer> entry : customerDao.getCustomersMap().entrySet()) {
			if (CustomerType.PREMIUM.equals(entry.getValue().getCustomType())) {
				premiumCustomersMap.put(entry.getKey(), 192);
			}
		}
	}
	
	public void chargeForSearch(String email, int numberOfCredits) {
		
		chargingDao.chargeForSearch(email, numberOfCredits);
	}

}
