package net.icdpublishing.exercise2.myapp


import net.icdpublishing.exercise2.myapp.charging.services.ChargingService
import net.icdpublishing.exercise2.myapp.customers.domain.Customer
import net.icdpublishing.exercise2.myapp.customers.domain.CustomerType
import net.icdpublishing.exercise2.myapp.customers.service.CustomSearchEngineRetrievalService
import net.icdpublishing.exercise2.myapp.customers.service.CustomSearchEngineRetrievalServiceImpl
import net.icdpublishing.exercise2.myapp.customers.service.CustomerService
import net.icdpublishing.exercise2.searchengine.domain.SourceType
import spock.lang.Specification

class CustomSearchEngineRetrievalServiceTest extends Specification {

    CustomerService customerService = Mock()
    ChargingService chargingService = Mock()

    CustomSearchEngineRetrievalService customSearchEngineRetrievalService

    def setup() {
        customSearchEngineRetrievalService = new CustomSearchEngineRetrievalServiceImpl(customerService: customerService, chargingService: chargingService)
    }

    def "perform search as paying customer"() {
        given:
            def surname = "Smith"
            def postcode = "sw6 2bq"
            def email = "john@test.com"

            customerService.findCustomerByEmailAddress(email) >> new Customer(customerType: CustomerType.PREMIUM)

            //normally data would be mocked but data is already hard coded for the purpose of the exercise

        when:
            def records = customSearchEngineRetrievalService.performSearch(surname, postcode, email)

        then:
            //assert expected number of records were found
            records.size() == 3

            //assert sorting by forename asc
            records[0].person.forename == "Alfred"
            records[1].person.forename == "James"
            records[2].person.forename == "Mary"

            //alfred has all data sources
            records[0].sourceTypes == [SourceType.DNB, SourceType.BT, SourceType.ELECTORAL_ROLL] as Set

            //charging occurred
            1 * chargingService.charge(email, 3)
    }

    def "perform search as non paying customer"() {
        given:
            def surname = "Smith"
            def postcode = "sw6 2bq"
            def email = "john@test.com"

            customerService.findCustomerByEmailAddress(email) >> new Customer(customerType: CustomerType.NON_PAYING)

            //normally data would be mocked but data is already hard coded for the purpose of the exercise

        when:
            def records = customSearchEngineRetrievalService.performSearch(surname, postcode, email)

        then:
            //assert expected number of records were found
            records.size() == 1

            //assert sorting by forename asc
            records[0].person.forename == "Mary"

            //records only have BT
            records.each {
                r -> r.sourceTypes == [SourceType.BT] as Set
            }

            //charging wasn't triggered
            0 * chargingService.charge(*_)
    }
}
