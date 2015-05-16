package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c > r) 0
    else if (c == 0 || r == c) 1
    else pascal(c, r - 1) + pascal(c - 1, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def loop(chars: List[Char], x: Int): Boolean =
      if (x < 0) false
      else if (chars.isEmpty) {
        if (x == 0) true
        else false
      } else {
        val c = chars.head
        if (c == '(') loop(chars.tail, x + 1)
        else if (c == ')') loop(chars.tail, x - 1)
        else loop(chars.tail, x)
      }

    loop(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0) 1
    else if (money < 0) 0
    else if (coins.isEmpty && money >= 1) 0
    else countChange(money, coins.tail) + countChange(money - coins.head, coins)
  }

  def sumInts = sum(x => x)
  
  def sumCubes = sum(x => x*x*x)

  def sumFactorial = sum(fact)
  
  def fact(x : Int) : Int = if(x <= 1) 1 else x * fact(x - 1)
  
//  def sum(f:Int => Int, a : Int, b : Int) : Int = {
//    def loop(a : Int, acc : Int):Int = {
//		if(a > b) acc
//		else loop(a + 1, acc + f(a))
//  	}
//  	loop(a, 0)
//  }
  
// Retorna uma funcao por si, que por sua vez recebe dois parametros inteiros e retorna um int
  def sum(f : Int => Int) : (Int, Int) => Int = {
	def sumF(a : Int, b : Int) : Int = { 
	  if(a > b) 0
	  else f(a) + sumF(a+1, b)
	}
	return sumF
  }
  
  //Lista de parametros multipla, equivalente a sumF
//  def sum(f: Int => Int)(a : Int, b : Int) : Int =
//    if(a > b) 0 else f(a) + sum(f)(a+1, b)
    
}