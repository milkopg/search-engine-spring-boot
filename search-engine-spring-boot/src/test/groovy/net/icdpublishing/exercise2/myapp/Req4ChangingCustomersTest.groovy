package net.icdpublishing.exercise2.myapp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import net.icdpublishing.exercise2.myapp.charging.ChargingException
import net.icdpublishing.exercise2.myapp.charging.services.ChargingService
import net.icdpublishing.exercise2.myapp.customers.dao.ChargingDaoImpl
import net.icdpublishing.exercise2.myapp.customers.dao.ChargingDaoImplTest
import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao
import net.icdpublishing.exercise2.myapp.customers.dao.HardcodedListOfCustomersImpl
import net.icdpublishing.exercise2.myapp.customers.service.ChargingServiceTest
import spock.lang.Specification

@SpringBootTest
class Req4ChangingCustomersTest extends Specification{
	ChargingService chargingService;
	ChargingDaoImpl chargingDao;
	CustomerDao customerDao;
	ChargingDaoImplTest chargingDaoTest;
	def setup() {
		chargingService = Mock(ChargingService);
		customerDao = new HardcodedListOfCustomersImpl();
		chargingDao = new ChargingDaoImpl();
		chargingDaoTest =  new ChargingDaoImplTest(chargingDao, customerDao);
		chargingDao.initPurchaseCredits();
		chargingDao.setCustomerDao(customerDao);
	}
	
	def "Catching exception for charging new Customer" () {
		chargingService.charge("john.doe@192.com", 4) >> {throw new ChargingException("")}
		when: "Charge paying customer"
		
		chargingDaoTest.chargeForSearch("john.doe@192.com", 4) 
		then: "Catch an exception"
		thrown(ChargingException)
	}
	
	def "Charging for search" () {
		when: "Charge paying customer"
		chargingDaoTest.chargeForSearch("john.doe@192.com", 4)
		then: "Catch an exception"
		thrown(ChargingException)
		chargingDao.getPremiumCustomersMap().get("john.doe@192.com") == 188
	}
	
	def "Charge non paying customer" () {
		when: "Charge paying customer"
		chargingDaoTest.chargeForSearch("harry.lang@192.com", 4);_
		then: "No Exception"
		!chargingDao.getPremiumCustomersMap().contains("harry.lang@192.com") 
	}
}
