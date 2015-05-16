package se.sjfd

import javax.persistence.EntityManager

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}
import scala.collection.JavaConversions._

/**
 * Created by BWeninger on 05/05/2015.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
class LanguageDAOImpl extends LanguageDAO {

  var entityManager : EntityManager = _

  override def save(language: Language): Unit = {
    language.id match {
      case 0 => this.entityManager.persist(language)
      case _ => this.entityManager.merge(language)
    }
  }

  override def find(id: Int): Option[Language] = {
    Option(this.entityManager.find(classOf[Language], id))
  }

  override def getAll: List[Language] = {
    this.entityManager.createQuery("FROM Language", classOf[Language]).getResultList.toList
  }

  override def getByName(name: String): List[Language] = {
    entityManager.createQuery("FROM Language WHERE name = :name",
      classOf[Language]).setParameter(":name", name).getResultList.toList
  }
}
