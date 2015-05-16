package scalatest

import org.scalatest.FunSuite
import domain._

class Test04 extends FunSuite {
	val testRange = 1 to 20
  val accounts = for{a <- testRange} yield Account(a.toString)

  test("Non-parallel processing") {
    accounts.foreach(_.process)
  }

  test("Parallel processing") {
    accounts.par.foreach(_.process)
  }
}