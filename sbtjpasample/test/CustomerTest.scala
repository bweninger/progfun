import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.springframework.context.support.ClassPathXmlApplicationContext
import se.sjfd.{Language, LanguageDAO, CustomerDAO}

/**
 * Created by BWeninger on 05/05/2015.
 */

@RunWith(classOf[JUnitRunner])
class CustomerTest extends FunSuite {

  val ctx = new ClassPathXmlApplicationContext("application-context.xml")

  test("There are 13 customers in the Derby DB"){
    val customerDao = ctx.getBean(classOf[CustomerDAO])
    val customers = customerDao.getAll
    assert(customers.size == 13)
    println(customerDao.find(3).getOrElse("No customer found with id 3"))
  }

  test("persisting 3 new languages"){
    val languageDao = ctx.getBean(classOf[LanguageDAO])
    languageDao.save(new Language("English"))
    languageDao.save(new Language("French"))
    languageDao.save(new Language("Portuguese"))

    val languages = languageDao.getAll
    assert(languages.size == 3)

    assert(languageDao.getByName("French").size == 1)

  }

}
