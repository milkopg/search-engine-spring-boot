package net.icdpublishing.exercise2.myapp;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.icdpublishing.exercise2.myapp.customers.domain.Customer;
import net.icdpublishing.exercise2.myapp.customers.general.Constants;
import net.icdpublishing.exercise2.myapp.customers.search.SearchForm;
import net.icdpublishing.exercise2.myapp.customers.service.CustomSearchEngineRetrievalService;
import net.icdpublishing.exercise2.myapp.customers.service.CustomerDaoService;
import net.icdpublishing.exercise2.searchengine.domain.Record;

@Controller
public class MySearchController {
	@Autowired
	CustomSearchEngineRetrievalService retrievalService;
	
	@Autowired
	CustomerDaoService daoService;
	
	@GetMapping(Constants.SCREEN_HOME)
	public String home(Model model) {
		SearchForm searchForm = new SearchForm();
		model.addAttribute(Constants.MODEL_SEARCH_FORM, searchForm);
		return Constants.SCREEN_SEARCH;
	}
	
	
	@PostMapping(Constants.SCREEN_SEARCH)
	public ModelAndView search(Model model, @ModelAttribute(Constants.MODEL_SEARCH_FORM) SearchForm searchForm) {
		model.addAttribute(Constants.MODEL_SEARCH_FORM, searchForm);
		return showResults(model, searchForm);
	}
	
	
	@PostMapping(Constants.SCREEN_RESULTS)
	public ModelAndView showResults(Model model, @Valid SearchForm searchForm) {
		String surname = searchForm.getSurname();
		String postcode = searchForm.getPostcode();
		String email = searchForm.getEmail();
		
		Customer customer = daoService.findCustomerByEmailAddress(email);
		Collection<Record> persons = retrievalService.performSearch(surname, postcode, email);
		
		model.addAttribute(Constants.MODEL_CUSTOMER, customer);
		model.addAttribute(Constants.MODEL_PERSONS, persons);
		ModelAndView modelAndView = new ModelAndView(Constants.SCREEN_RESULTS, model.asMap());
		return modelAndView;
	}
	
	@GetMapping(value = Constants.SCREEN_SEARCH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Collection<Record> search (@RequestParam(value="email", required=true) String email, 
			@RequestParam(value="surname", required=true) String surname, 
			@RequestParam(value="postcode", required=true) String postcode) {
		Collection<Record> persons = retrievalService.performSearch(surname, postcode, email); //TODO validate email REGEX
		return persons;
	}
}