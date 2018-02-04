package net.icdpublishing.exercise2.myapp
import net.icdpublishing.exercise2.myapp.Add
import spock.lang.*

class AddSpec extends spock.lang.Specification{
	def "Add two numbers" () {
		when: "Initializing a class"
		def add = new Add()
		then: "sum of two integers"
		add.add(1, 2) == 3
	}
}
