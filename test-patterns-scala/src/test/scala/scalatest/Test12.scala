package scalatest

import org.scalatest._
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession

object Contacts extends Table[(Long, String)]("CONTACTS") {
	def id = column[Long]("CONTACT_ID", O.PrimaryKey)
  def name = column[String]("CONTACT_NAME")
  def gender = column[String]("GENDER")
  def * = id ~ name
}

class Test12 extends FunSuite {
	val dbUrl = "jdbc:h2:mem:contacts"
	val dbDriver = "org.h2.Driver"
	
	test("Slick, H2, embedded") {		
		Database.forURL(dbUrl, driver = dbDriver) withSession {
			Contacts.ddl.create
			Contacts.insertAll(
					(1, "Bob"),
					(2, "Tom"),
					(3, "Salley")
				)

		  val names = ( for( c <- Contacts if c.name like "%o%") yield c.name ).list
			names.foreach(println)
			assert(names === List("Bob","Tom"))
		}
	}
}