package net.icdpublishing.exercise2.myapp;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	public MySearchController() {
		// TODO Auto-generated constructor stub
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
	public String search(Model model, @ModelAttribute("searchForm") SearchForm searchForm, @RequestParam(value="name", required=false, defaultValue="World") String name) {
		model.addAttribute("name", name);
		model.addAttribute("searchForm", searchForm);
		return showResults(model, searchForm);
	}
	
	
	@PostMapping("/results")
	public String showResults(Model model, SearchForm searchForm) {
		if (searchForm == null) return Constants.SCREEN_NO_DATA;
		String surname = searchForm.getSurname();
		String postcode = searchForm.getPostcode();
		String email = searchForm.getEmail();
		SimpleSurnameAndPostcodeQuery query = new SimpleSurnameAndPostcodeQuery(surname, postcode);
		Customer customer = daoService.findCustomerByEmailAddress(email);
		SearchRequest request = new SearchRequest(query, customer);
		
		Collection<Record> results = handleRequest(request).stream().
				filter(record -> (record.getPerson().getSurname().equals(surname) && record.getPerson().getAddress().getPostcode().equals(postcode)))
				.collect(Collectors.toList());
		model.addAttribute("resutls", results);
		return Constants.SCREEN_RESULTS;
	} 
	
	private Collection<Record> getResults(SimpleSurnameAndPostcodeQuery query) {
		return retrievalService.search(query);
	}
}