package net.icdpublishing.exercise2.myapp.customers.dao;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import net.icdpublishing.exercise2.myapp.charging.ChargingException;
import net.icdpublishing.exercise2.myapp.customers.loader.PaymentDataLoader;

@Repository
public class CustomChargingDaoImpl implements CustomChargingDao {
	private static ConcurrentHashMap<String, Integer> premiumCustomersMap = new ConcurrentHashMap<>();

	public CustomChargingDaoImpl() {
		premiumCustomersMap = new PaymentDataLoader().loadAllPaymentMap();
	}
	
	@Override
	public void chargeForSearch(String email, int numberOfCredits) {
		if (premiumCustomersMap.containsKey(email)) {
			int credits = premiumCustomersMap.get(email);
			credits -= numberOfCredits;
			if (credits < 0) {
				credits = 0;
				throw new ChargingException("Cannot display data. You don't have enought credits for display data");
			}
			premiumCustomersMap.replace(email, credits);
			//throw new ChargingException("We don't charge for individual records which exclusively contain data sourced from \"BT\". ");
		}
	}
	
	@Override
	public Integer getCustomerBalance(String email) {
		if (email == null) return null;
		Integer balance = null;
		if (premiumCustomersMap.containsKey(email)) {
			balance = premiumCustomersMap.get(email);
		}
		return balance;
	}
}
