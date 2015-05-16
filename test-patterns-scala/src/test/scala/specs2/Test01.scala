package specs2

import org.specs2.mutable._

class Test01 extends Specification {

  "The integer 1" should {
    "equal 1" in {
      1 === 1
    }
  }
}