





package models

import play.api.db._
import play.api.Play.current
import java.sql.Date

import scala.slick.driver.H2Driver.simple._
import scala.reflect.runtime._
import scala.reflect.runtime.{ universe => ru }

case class Product(productId : Option[Int], manufacturerId : Option[Int], productCode : Option[String], purchaseCost : Option[Int], quantityOnHand : Option[Int], markup : Option[Int], available : Option[String], description : Option[String])

// Definition of the Produc table
object Product extends Table[Product]("PRODUCT") {

  //def id = column[Int]("ID", O.PrimaryKey, O AutoInc) // This is the primary key column
  
  
  def productId = column[Int]("PRODUCT_ID", O.PrimaryKey) 
  
  
  def manufacturerId = column[Option[Int]]("MANUFACTURER_ID") 
  // def manufacturerIdEntity = foreignKey("SYS_FK_10116", manufacturerId, Manufacturer)(_.id)
  
  def productCode = column[Option[String]]("PRODUCT_CODE") 
  // def productCodeEntity = foreignKey("SYS_FK_10117", productCode, ProductCode)(_.id)
  
  def purchaseCost = column[Option[Int]]("PURCHASE_COST") 
  
  
  def quantityOnHand = column[Option[Int]]("QUANTITY_ON_HAND") 
  
  
  def markup = column[Option[Int]]("MARKUP") 
  
  
  def available = column[Option[String]]("AVAILABLE") 
  
  
  def description = column[Option[String]]("DESCRIPTION") 
  
    
  
  def * = productId.? ~ manufacturerId ~ productCode ~ purchaseCost ~ quantityOnHand ~ markup ~ available ~ description <> (Product.apply _, Product.unapply _)
  //def autoInc = id.? ~ productId.? ~ manufacturerId ~ productCode ~ purchaseCost ~ quantityOnHand ~ markup ~ available ~ description <> (Produc.apply _, Produc.unapply _) returning id 

  def findAll(filter: String = "%") = {
    for {
      entity <- Product
      // if (entity.name like ("%" + filter))
    } yield entity
  }

  def findByPK(pk: Int) =
    for (entity <- Product if entity.productId === pk) yield entity
  
    
  /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options = this.findAll().map(x => x.productId -> productId)
  
  val mirror = ru.runtimeMirror(getClass.getClassLoader)
  
  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, order: String, asc: Boolean = false, filter: String = "%") = {
    val members = ru.typeOf[Product].members.filter(m => m.isTerm && !m.isMethod).toList
    val fields = members.map(_.name.decoded.trim).reverse.toVector
    println("Fields of Supplier class: " + fields)

    val sortField: String = fields(orderBy.abs - 1)
    println("The field to sort against is: " + sortField)

    // Need to give the sorting field at compile time... is there a better way ?
    val methodFields = sortField match {
      
      case "productId" => ru.typeOf[Product.type].declaration(ru.newTermName("productId")).asMethod
      
      case "manufacturerId" => ru.typeOf[Product.type].declaration(ru.newTermName("manufacturerId")).asMethod
      
      case "productCode" => ru.typeOf[Product.type].declaration(ru.newTermName("productCode")).asMethod
      
      case "purchaseCost" => ru.typeOf[Product.type].declaration(ru.newTermName("purchaseCost")).asMethod
      
      case "quantityOnHand" => ru.typeOf[Product.type].declaration(ru.newTermName("quantityOnHand")).asMethod
      
      case "markup" => ru.typeOf[Product.type].declaration(ru.newTermName("markup")).asMethod
      
      case "available" => ru.typeOf[Product.type].declaration(ru.newTermName("available")).asMethod
      
      case "description" => ru.typeOf[Product.type].declaration(ru.newTermName("description")).asMethod
      
    }

    findAll().sortBy { x =>    
      val reflectedMethod = mirror.reflect(x).reflectMethod(methodFields)().asInstanceOf[Column[Any]]
      if (orderBy >= 0) reflectedMethod.asc
      else reflectedMethod.desc
    }.drop(page * pageSize).take(pageSize)
  }

}
