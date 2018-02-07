package net.icdpublishing.exercise2.myapp

import java.util.concurrent.ConcurrentHashMap

import net.icdpublishing.exercise2.myapp.charging.ChargingException
import net.icdpublishing.exercise2.myapp.charging.services.ChargingService
import net.icdpublishing.exercise2.myapp.customers.dao.CustomChargingDao
import net.icdpublishing.exercise2.myapp.customers.dao.CustomChargingDaoImpl
import net.icdpublishing.exercise2.myapp.customers.loader.PaymentDataLoader
import spock.lang.Specification

class Req4ChangingCustomersTest extends Specification{
	ChargingService chargingService;
	CustomChargingDao customChargingDao;
	ConcurrentHashMap<String, Integer> paymentMap;
	def setup() {
		chargingService = Mock(ChargingService);
		customChargingDao = new CustomChargingDaoImpl();
		paymentMap = new PaymentDataLoader().loadAllPaymentMap();
	}
	
	def "Catching exception for charging new Customer" () {
		when: "Charge paying customer"
		customChargingDao.chargeForSearch("john.doe@192.com", 4) 
		then: "Catch an exception"
		thrown(ChargingException) 
	}
	
	def "Charging for search" () {
		def email = "john.doe@192.com".toString();
		chargingService.charge(email, 4) >> { paymentMap.put(email, paymentMap.get(email)-4)}
		when: "Charge paying customer"
		chargingService.charge(email, 4)
		customChargingDao.chargeForSearch(email, 4) 
		then: "Catch an exception"
		def ex = thrown(ChargingException)
		customChargingDao.getCustomerBalance(email) == paymentMap.get(email);
		
	}
	
	def "No exception non paying customer" () {
		def email = "harry.lang@192.com".toString();
		when: "Charge paying customer"
		customChargingDao.chargeForSearch(email, 4) 
		then: "No Exception"
		notThrown(ChargingException)
	}
	
	def "No charging for non paying customer" () {
		def email = "harry.lang@192.com".toString();
		when: "Charge paying customer"
		customChargingDao.chargeForSearch(email, 4)
		then: "No Exception"
		customChargingDao.getCustomerBalance(email) == paymentMap.get(email);
	}
}
