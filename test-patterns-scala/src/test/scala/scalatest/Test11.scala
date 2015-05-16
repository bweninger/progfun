package scalatest

import org.scalatest._
import org.scalatest.concurrent._
import akka.actor._
import scala.concurrent._
import scala.concurrent.duration._

class Test11 extends FunSuite with BeforeAndAfterAll with org.scalatest.concurrent.Futures {
	
	test("Spray Http Client, Akka, Futures") {
		//client imports
		import spray.http._
		import spray.client.pipelining._
		import system.dispatcher
		import scala.util.{Success, Failure}
		
		//Pipe and request to send through it. Capture response as as Future[String]
		val pipe: HttpRequest => Future[String] = ( sendReceive ~> unmarshal[String])
		val response: Future[String] = pipe {Get(s"http://localhost:${_port}/hello")}
		
		//handle response
		response onComplete { _.getOrElse("Error") }
		
		//block until response value, then assert
		val value = Await.result(response,2.seconds)
		assert(value === "hi")
	}

	//Spray server management
	import spray.routing.SimpleRoutingApp

	implicit val system = ActorSystem("system")
	val _port = 8091
	
		object Server extends SimpleRoutingApp {
		  startServer(interface = "localhost", port = _port) {
				get {
					path("hello") {
						complete {"hi"}
					}
				}
			}
		}
	
	override def beforeAll() {
		Server	
	}

	override def afterAll() {
		system.scheduler.scheduleOnce(2.second)(system.shutdown())(system.dispatcher)
	}
}

