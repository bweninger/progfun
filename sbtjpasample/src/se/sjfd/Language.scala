package se.sjfd

import javax.persistence._

import scala.beans.BeanProperty

/**
 * Created by bweninger on 05/05/2015.
 */
@Entity
@Table(name = "language")
class Language(l : String) {

  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty @Column(name = "ID")
  var id : Int = _

  @BeanProperty @Column(name = "NAME")
  var name : String = l

  def this() = this(null)

  override def toString = id + " = " + name


}
