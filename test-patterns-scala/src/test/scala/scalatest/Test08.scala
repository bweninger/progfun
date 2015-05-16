package scalatest

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.selenium.WebBrowser
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.WebDriver

class Test08 extends FlatSpec with ShouldMatchers with WebBrowser {

  implicit val webDriver: WebDriver = new HtmlUnitDriver
	go to "http://www.amazon.com" 
	click on "twotabsearchtextbox"
	textField("twotabsearchtextbox").value = "Scala"
	submit()
	pageTitle should be ("Amazon.com: Scala")
	pageSource should include("Scala Cookbook: Recipes")
}
