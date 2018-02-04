package net.icdpublishing.exercise2;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import net.icdpublishing.exercise2.myapp.MySearchController;
import net.icdpublishing.exercise2.myapp.customers.service.CustomSearchEngineRetrievalService;

@RunWith(SpringRunner.class)
//@SpringBootTest
@WebMvcTest(MySearchController.class)
public class SearchEngineSpringBootApplicationTests {
	
	@Autowired
    private MockMvc mvc;
	
	 @MockBean
	 private CustomSearchEngineRetrievalService searchEngineRetrievalService;;

	@Test
	public void contextLoads() {
		//assertFalse(condition);
		System.out.println("Found" +  searchEngineRetrievalService.performSearch("surname", "poscode", "email"));
	}

}
