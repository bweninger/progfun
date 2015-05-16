





package models

import play.api.db._
import play.api.Play.current
import java.sql.Date

import scala.slick.driver.H2Driver.simple._
import scala.reflect.runtime._
import scala.reflect.runtime.{ universe => ru }

case class PurchaseOrder(orderNum : Option[Int], customerId : Option[Int], productId : Option[Int], quantity : Option[Int], shippingCost : Option[Int], salesDate : Option[Date], shippingDate : Option[Date], freightCompany : Option[String])

// Definition of the PurchaseOrde table
object PurchaseOrder extends Table[PurchaseOrder]("PURCHASE_ORDER") {

  //def id = column[Int]("ID", O.PrimaryKey, O AutoInc) // This is the primary key column
  
  
  def orderNum = column[Int]("ORDER_NUM", O.PrimaryKey) 
  
  
  def customerId = column[Option[Int]]("CUSTOMER_ID") 
  // def customerIdEntity = foreignKey("SYS_FK_10126", customerId, Customer)(_.id)
  
  def productId = column[Option[Int]]("PRODUCT_ID") 
  // def productIdEntity = foreignKey("SYS_FK_10127", productId, Product)(_.id)
  
  def quantity = column[Option[Int]]("QUANTITY") 
  
  
  def shippingCost = column[Option[Int]]("SHIPPING_COST") 
  
  
  def salesDate = column[Option[Date]]("SALES_DATE") 
  
  
  def shippingDate = column[Option[Date]]("SHIPPING_DATE") 
  
  
  def freightCompany = column[Option[String]]("FREIGHT_COMPANY") 
  
    
  
  def * = orderNum.? ~ customerId ~ productId ~ quantity ~ shippingCost ~ salesDate ~ shippingDate ~ freightCompany <> (PurchaseOrder.apply _, PurchaseOrder.unapply _)
  //def autoInc = id.? ~ orderNum.? ~ customerId ~ productId ~ quantity ~ shippingCost ~ salesDate ~ shippingDate ~ freightCompany <> (PurchaseOrde.apply _, PurchaseOrde.unapply _) returning id 

  def findAll(filter: String = "%") = {
    for {
      entity <- PurchaseOrder
      // if (entity.name like ("%" + filter))
    } yield entity
  }

  def findByPK(pk: Int) =
    for (entity <- PurchaseOrder if entity.orderNum === pk) yield entity
  
    
  /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options = this.findAll().map(x => x.orderNum -> orderNum)
  
  val mirror = ru.runtimeMirror(getClass.getClassLoader)
  
  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, order: String, asc: Boolean = false, filter: String = "%") = {
    val members = ru.typeOf[PurchaseOrder].members.filter(m => m.isTerm && !m.isMethod).toList
    val fields = members.map(_.name.decoded.trim).reverse.toVector
    println("Fields of Supplier class: " + fields)

    val sortField: String = fields(orderBy.abs - 1)
    println("The field to sort against is: " + sortField)

    // Need to give the sorting field at compile time... is there a better way ?
    val methodFields = sortField match {
      
      case "orderNum" => ru.typeOf[PurchaseOrder.type].declaration(ru.newTermName("orderNum")).asMethod
      
      case "customerId" => ru.typeOf[PurchaseOrder.type].declaration(ru.newTermName("customerId")).asMethod
      
      case "productId" => ru.typeOf[PurchaseOrder.type].declaration(ru.newTermName("productId")).asMethod
      
      case "quantity" => ru.typeOf[PurchaseOrder.type].declaration(ru.newTermName("quantity")).asMethod
      
      case "shippingCost" => ru.typeOf[PurchaseOrder.type].declaration(ru.newTermName("shippingCost")).asMethod
      
      case "salesDate" => ru.typeOf[PurchaseOrder.type].declaration(ru.newTermName("salesDate")).asMethod
      
      case "shippingDate" => ru.typeOf[PurchaseOrder.type].declaration(ru.newTermName("shippingDate")).asMethod
      
      case "freightCompany" => ru.typeOf[PurchaseOrder.type].declaration(ru.newTermName("freightCompany")).asMethod
      
    }

    findAll().sortBy { x =>    
      val reflectedMethod = mirror.reflect(x).reflectMethod(methodFields)().asInstanceOf[Column[Any]]
      if (orderBy >= 0) reflectedMethod.asc
      else reflectedMethod.desc
    }.drop(page * pageSize).take(pageSize)
  }

}
