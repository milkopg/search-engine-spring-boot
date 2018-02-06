package net.icdpublishing.exercise2.myapp

import java.lang.reflect.Array

import groovy.swing.factory.CollectionFactory
import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao
import net.icdpublishing.exercise2.myapp.customers.dao.HardcodedListOfCustomersImpl
import net.icdpublishing.exercise2.myapp.customers.service.CustomSearchEngineRetrievalService
import net.icdpublishing.exercise2.myapp.customers.service.CustomSearchEngineRetrievalServiceImpl
import net.icdpublishing.exercise2.searchengine.domain.Address
import net.icdpublishing.exercise2.searchengine.domain.Person
import net.icdpublishing.exercise2.searchengine.domain.Record
import net.icdpublishing.exercise2.searchengine.domain.SourceType
import net.icdpublishing.exercise2.searchengine.loader.DataLoader
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery
import spock.lang.Specification

class Req6SortingSurnameAscTest extends Specification {
	CustomSearchEngineRetrievalService searchEngineRetrievalService;
	CustomerDao customerDao;
	Collection<Record> mockRecords;
	SimpleSurnameAndPostcodeQuery query;
	
	def setup() {
		searchEngineRetrievalService = new CustomSearchEngineRetrievalServiceImpl();;
		customerDao = new HardcodedListOfCustomersImpl();
		query = new SimpleSurnameAndPostcodeQuery("Smith", "sw6 2bq");
	}
	
	def "get records by asc order" () {
		def mockRecords = new DataLoader().loadAllDatasets();
		when: "Fetch sorted records"
		def sortedRecords = searchEngineRetrievalService.search(query);
		def isSorted = false;
		def surnameList = new ArrayList();
		for (Record record : sortedRecords) {
			def surname = record.person.surname;
			surnameList.add(surname);
		}
		def surnameListCopy = new ArrayList(surnameList);
		Collections.sort(surnameList);
		then: "Check if they are sorted"
		surnameList == surnameListCopy
	}
}
