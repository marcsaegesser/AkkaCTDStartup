package demo

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import akka.actor._
import akka.testkit.CallingThreadDispatcher

class DemoTests extends WordSpec with ShouldMatchers {

  class Fubar extends Actor {
    def receive = {
      case t: String => assert(Thread.currentThread.getName.equals(t))
      case _         => // WTF
    }
  }

  "an actor" should {
    "run on the calling thread using the CallingThreadDispatcher" in {
      val actor = ActorSystem().actorOf(Props(new Fubar).withDispatcher(CallingThreadDispatcher.Id))
      // Thread.sleep(50)  // Uncomment this sleep and the actor will run on the calling thread
      actor ! Thread.currentThread.getName
    }
  }
}
