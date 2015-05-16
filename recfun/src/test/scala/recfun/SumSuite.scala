package recfun

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class SumIntsSuite extends FunSuite{
	import Main._
	
	test("Somando inteiros") {
	  assert(sumInts(3, 4) == 7)
	}
	
	test("Somando Fatoriais") {
		assert(sumFactorial(3, 4) == 30)
	}
	
	test("Somando Cubos"){
	  assert(sumCubes(3, 4) == 91)
	}
}