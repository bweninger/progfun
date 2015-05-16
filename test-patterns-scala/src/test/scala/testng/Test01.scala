package testng

import org.testng.Assert.assertEquals
import org.testng.annotations.Test
import org.scalatest.Assertions


class Test01 extends Assertions {
  //Basic TestNG using Scala
  @Test def aBasicTest: Unit = {
    assertEquals("abc", "abc")
  }
}