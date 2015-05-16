package se.sjfd

import javax.persistence._

import scala.beans.BeanProperty

/**
 * Created by BWeninger on 04/05/2015.
 */

@Entity
@Table(name = "customer")
class Customer(n: String) {

  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "CUSTOMER_ID")
  @BeanProperty
  var id: Int = _

  @BeanProperty
  @Column(name = "NAME")
  var name : String = n

  def this() = this(null)

  override def toString = id + " = " + name



}
