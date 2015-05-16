package scalatest

import org.scalatest.{FunSuite}
import domain._

class Test05 extends FunSuite {
  //create some test accounts
	val testRange = 1 to 10
  val accounts = for{a <- testRange} yield Account(a.toString)
	//define some rules
	def ruleEven = (a:Account) => {a.name.toInt % 2 == 0}
	def rule3Mult = (a:Account) => {a.name.toInt % 3 == 0}
	//compose rules
	def ruleValidAccountOr = (a:Account) => {ruleEven(a) || rule3Mult(a)}
	def ruleValidAccountAnd = (a:Account) => {ruleEven(a) && rule3Mult(a)}
	//data driven test creating a test per account
	accounts.foreach{a =>
		test(s"Account ${a.name} validation: Or") {
			withClue(s"a.name=${a.name}") {
				assert(ruleValidAccountOr(a))
			}
		}
	}
	
	accounts.foreach{a =>
		test(s"Account ${a.name} validation: And") {
			withClue(s"a.name=${a.name}") {
				assert(ruleValidAccountAnd(a))
			}
		}
	}

}