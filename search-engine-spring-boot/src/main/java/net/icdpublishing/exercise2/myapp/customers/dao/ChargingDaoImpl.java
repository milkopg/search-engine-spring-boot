package net.icdpublishing.exercise2.myapp.customers.dao;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.icdpublishing.exercise2.myapp.charging.ChargingException;
import net.icdpublishing.exercise2.myapp.charging.dao.ChargingDao;
import net.icdpublishing.exercise2.myapp.customers.domain.Customer;
import net.icdpublishing.exercise2.myapp.customers.domain.CustomerType;

@Repository
public class ChargingDaoImpl implements ChargingDao {
	private static ConcurrentHashMap<String, Integer> premiumCustomersMap = new ConcurrentHashMap<>();
	
	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public void chargeForSearch(String email, int numberOfCredits) {
		if (premiumCustomersMap.containsKey(email)) {
			int credits = premiumCustomersMap.get(email);
			credits -= numberOfCredits;
			premiumCustomersMap.replace(email, credits);
			throw new ChargingException("We don't charge for individual records which exclusively contain data sourced from \"BT\". ");
		}
	}
	
	@PostConstruct
	private void initPurchaseCredits() {
		if (customerDao != null)
		for (Entry <String, Customer> entry : customerDao.getCustomersMap().entrySet()) {
			if (CustomerType.PREMIUM.equals(entry.getValue().getCustomType())) {
				premiumCustomersMap.put(entry.getKey(), 192);
			}
		}
	}
	
	public  ConcurrentHashMap<String, Integer> getPremiumCustomersMap() {
		return premiumCustomersMap;
	}
	
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
		initPurchaseCredits();
	}
}
