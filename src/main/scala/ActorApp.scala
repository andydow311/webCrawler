import akka.actor.{Actor, ActorSystem, Props}
import org.jsoup.Jsoup.connect
import java.util.regex.Pattern

import Actors.Master

object ActorApp extends App{

  //partone Create System
  val webCrawlingSystem = ActorSystem("crawl")

  //partTwo createActors
 /* class CrawlerActor extends Actor{
    def receive: PartialFunction[Any,Unit] ={
      case url:String => {
        println(s"I have received $url")
      }
      case _ =>  println("Nothing received here!!")
    }
  }*/

  //partThree instatiate Actor
  val crawler = webCrawlingSystem.actorOf(Props[Master], "theCrawler")

  //partFour communicate
  crawler ! "https://www.sportinglife.com/racing/profiles/horse/0000721"


}
