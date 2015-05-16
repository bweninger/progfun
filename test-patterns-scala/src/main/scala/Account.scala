package domain

case class Account(val name:String, val created:String = "0") {
  def process = {
    Thread.sleep(200)
    true
  }
}