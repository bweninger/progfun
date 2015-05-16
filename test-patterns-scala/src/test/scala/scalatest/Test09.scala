package scalatest

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.testkit.TestKit
import org.scalatest.WordSpecLike
import org.scalatest.matchers.MustMatchers
import org.scalatest.BeforeAndAfterAll
import akka.testkit.ImplicitSender

//http://doc.akka.io/docs/akka/snapshot/scala/testing.html
 
object Setup {
	class EchoActor extends Actor {
		def receive = {
			case x => sender ! x
		}
	}
	
	case class Address(street: String, city: String, state: String, zip: String)
	
	//see https://developers.google.com/maps/documentation/geocoding/#Limits
	class GeoActor extends Actor {
		def receive = {
			case Address(street,city,state,zip) => {
				import dispatch._, Defaults._
				val svc = url(s"http://maps.googleapis.com/maps/api/geocode/xml?address=${street},${city},${state},${zip}&sensor=true".replace(" ","+"))
				val response = Http(svc OK as.xml.Elem)
				val lat = (for {
				    elem <- response() \\ "geometry" \ "location" \ "lat"
				  } yield elem.text).head
				val lng = (for {
				    elem <- response() \\ "geometry" \ "location" \ "lng"
				  } yield elem.text).head
				sender ! s"${lat},${lng}"
			}
			case _ => sender ! "none"
		}
	}
}


class Test09(asys: ActorSystem) extends TestKit(asys) with ImplicitSender
	with WordSpecLike with MustMatchers with BeforeAndAfterAll {
 
	import Setup._
	def this() = this(ActorSystem("Setup"))
 
	override def afterAll {
		TestKit.shutdownActorSystem(system)
	}
 
	"An Echo actor" must {
 		"return messages" in {
			val echo = system.actorOf(Props[EchoActor])
			echo ! "hello world"
			expectMsg("hello world")
		}
	}
	
	"Geo actor" must {
 		"send back lat,lon" in {
			val geo = system.actorOf(Props[GeoActor])
			geo ! Address("27 South Park Avenue","San Francisco","CA","94107")
			expectMsg("37.7822991,-122.3930776")
		}
	}
}

