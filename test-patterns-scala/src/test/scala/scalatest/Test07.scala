package scalatest

import org.scalatest._
import org.scalatest.matchers.ShouldMatchers

class Test07 extends FlatSpec with ShouldMatchers {
  "This test" should "pass" in {
    true should be === true
  }
}
