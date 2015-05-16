package se.sjfd

/**
 * Created by BWeninger on 05/05/2015.
 */
trait LanguageDAO {

  def save(language : Language)

  def find(id : Int) : Option[Language]

  def getAll : List[Language]

  def getByName(name : String) : List[Language]

}
