package se.sjfd

/**
 * Created by bweninger on 05/05/2015.
 */
trait CustomerDAO {

  def save(customer : Customer) : Unit

  def find(id : Int) : Option[Customer]

  def getAll : List[Customer]

}
