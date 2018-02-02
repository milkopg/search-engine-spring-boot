package net.icdpublishing.exercise2.myapp;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.icdpublishing.exercise2.myapp.customers.domain.Customer;
import net.icdpublishing.exercise2.myapp.customers.general.Constants;
import net.icdpublishing.exercise2.myapp.customers.search.SearchForm;
import net.icdpublishing.exercise2.myapp.customers.service.CustomerDaoService;
import net.icdpublishing.exercise2.searchengine.domain.Record;
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery;
import net.icdpublishing.exercise2.searchengine.services.SearchEngineRetrievalService;

@Controller
public class MySearchController {
	
	private SearchEngineRetrievalService retrievalService;
	
	@Autowired
	CustomerDaoService daoService;
	
	@Autowired
	public MySearchController(SearchEngineRetrievalService retrievalService) {
		this.retrievalService = retrievalService;
	}
	
	public Collection<Record> handleRequest(SearchRequest request) {
		Collection<Record> resultSet = getResults(request.getQuery());
		return resultSet;
	}
	
	@GetMapping("/")
	public String home(Model model) {
		SearchForm searchForm = new SearchForm();
		model.addAttribute("name", "");
		model.addAttribute("searchForm", searchForm);
		return Constants.SCREEN_SEARCH;
	}
	
	
	@PostMapping("/search")
	public ModelAndView search(Model model, @ModelAttribute("searchForm") SearchForm searchForm, @RequestParam(value="name", required=false, defaultValue="World") String name) {
		model.addAttribute("name", name);
		model.addAttribute("searchForm", searchForm);
		return showResults(model, searchForm);
	}
	
	
	@PostMapping("/results")
	public ModelAndView showResults(Model model, @Valid SearchForm searchForm) {
		String surname = searchForm.getSurname();
		String postcode = searchForm.getPostcode();
		String email = searchForm.getEmail();
		
		Collection<Record> persons = performSearch(surname, postcode, email);
		
		Customer customer = daoService.findCustomerByEmailAddress(email);
		model.addAttribute("customer", customer);
		model.addAttribute("persons", persons);
		ModelAndView modelAndView = new ModelAndView(Constants.SCREEN_RESULTS, model.asMap());
		return modelAndView;
	}
	
	private Collection<Record> performSearch(String surname, String postcode, String email) {
		SimpleSurnameAndPostcodeQuery query = new SimpleSurnameAndPostcodeQuery(surname, postcode);
		Customer customer = daoService.findCustomerByEmailAddress(email);
		SearchRequest request = new SearchRequest(query, customer);
		
		Collection<Record> persons = handleRequest(request);
		return persons;
	}
	
	//@RequestMapping((value = "/getString", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "/search", produces = "application/json; charset=UTF-8")
	public @ResponseBody Collection<Record> search (@RequestParam(value="email", required=true) String email, 
			@RequestParam(value="surname", required=true) String surname, 
			@RequestParam(value="postcode", required=true) String postcode) {
		Collection<Record> persons = performSearch(surname, postcode, email);
		return persons;
		
	}
	
	
	private Collection<Record> getResults(SimpleSurnameAndPostcodeQuery query) {
		return retrievalService.search(query);
	}
}