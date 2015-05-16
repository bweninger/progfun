package scalatest

import org.scalatest.FunSuite

class Test10 extends FunSuite {
	test("Reactive library") {
		import rx._
		val a = Var(2) 
		val b = Var(3)
		val two = Var("two")
		val c = Rx{ a() + b() }
		val str = Rx{ a() + "," + two() }
		println(c()) // 5
		assert(c() === 5)
		println(str())
		assert(str() === "2,two")
		a() = 5
		println(c()) // 8
		assert(c() === 8)
		println(str())
		assert(str() === "5,two")
	}
}

