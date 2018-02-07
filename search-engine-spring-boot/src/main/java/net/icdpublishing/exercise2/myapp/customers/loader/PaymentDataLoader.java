package net.icdpublishing.exercise2.myapp.customers.loader;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao;
import net.icdpublishing.exercise2.myapp.customers.dao.HardcodedListOfCustomersImpl;
import net.icdpublishing.exercise2.myapp.customers.domain.Customer;
import net.icdpublishing.exercise2.myapp.customers.domain.CustomerType;

/**
 * Custom DataLoader requires to load payment data for premium customers 
 * @author Milko Galev
 *
 */
public class PaymentDataLoader {
	
	private CustomerDao customerDao;
	
	public PaymentDataLoader() {
		this.customerDao = new HardcodedListOfCustomersImpl();
	}
	
	/**
	 * It simulate adding payments credits to premium {@code CustomerType.PREMIUM} customers. Collection is ConcurrentHashMap
	 * to avoid any concurrent issue while credits are taken for searching 
	 * @return ConcurrentHashMap of initialized credits
	 */
	public ConcurrentHashMap<String, Integer> loadAllPaymentMap() {
		ConcurrentHashMap<String, Integer> premiumCustomersMap = new ConcurrentHashMap<>();
		for (Entry <String, Customer> entry : customerDao.getCustomersMap().entrySet()) {
			if (CustomerType.PREMIUM.equals(entry.getValue().getCustomType())) {
				premiumCustomersMap.put(entry.getKey(), 192);
			}
		}
		return premiumCustomersMap;
	}
}
