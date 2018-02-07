package net.icdpublishing.exercise2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import net.icdpublishing.exercise2.myapp.MySearchController;
import net.icdpublishing.exercise2.myapp.customers.service.CustomSearchEngineRetrievalService;
import net.icdpublishing.exercise2.myapp.customers.service.CustomerService;

@RunWith(SpringRunner.class)
//@SpringBootTest
@WebMvcTest(MySearchController.class)
@ComponentScan(basePackages= "net.icdpublishing.exercise2.myapp")
public class SearchEngineSpringBootApplicationTests {
	
	@Autowired
	private CustomSearchEngineRetrievalService searchEngineRetrievalService;
	 
	 @Autowired
	 CustomerService customerService;

	@Test
	public void contextLoads() {
		//assertTrue(searchEngineRetrievalService.performSearch("Smith", "sw6 2bq", "harry.lang@192.com").size() == 1);
		System.out.println(searchEngineRetrievalService.performSearch("Smith", "sw6 2bq", "harry.lang@192.com").size() == 1);
		System.out.println(customerService.findCustomerByEmailAddress("harry.lang@192.com"));
	}

}
