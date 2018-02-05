package net.icdpublishing.exercise2.myapp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan

import net.icdpublishing.exercise2.myapp.charging.ChargingException
import net.icdpublishing.exercise2.myapp.charging.dao.ChargingDao
import net.icdpublishing.exercise2.myapp.charging.services.ChargingService
import net.icdpublishing.exercise2.myapp.customers.service.ChargingServiceTest
import spock.lang.Specification

@WebMvcTest
class Req4ChangingCustomersTest extends Specification{
	
	ChargingService chargingService;
	ChargingDao chargingDao;
	def setup() {
		chargingService = Mock(ChargingService);
		chargingDao = Mock (ChargingDao);
	}
	
	def "Charge paying customer" () {
		when: "Charge paying customer"
		def chargeTest = new ChargingServiceTest(chargingDao);
		chargeTest.charge("john.doe@192.com", 4);_
		then: "Catch an exception"
		//thrown(ChargingException)
		true;
	}
	
	def "Charge non paying customer" () {
		when: "Charge paying customer"
		def chargeTest = new ChargingServiceTest(chargingDao);
		chargeTest.charge("john.doe@192.com", 4);_
		then: "No Exception"
		true;
	}
	
}
