package Actors

import java.util.Date

import akka.actor.Actor
import scala.util.matching.Regex
import org.jsoup._

class Master extends Actor{

  def receive: PartialFunction[Any,Unit] ={
    case url:String => {
      println(s"I have received $url")
      val horse = getHorse(Jsoup.connect(url).get().toString)

    }
    case _ =>  println("Nothing received here!!")
  }


  def getDocument(url: String): Unit ={
    Jsoup.connect(url).get()
  }
  
  def getHorse(doc:String): Horse = {

    println("doc = "+doc)

    val nameRegex: Regex = """<h1 data-test-id="header-title" class=\"Header__Title-xeaizz-0 ndttp\">(.+?)</h1>""".r

    val ageRegex: Regex = """<td class="Header__DataValue-xeaizz-4 cUoUJA">(.+?)</td>""".r

    val sexRegex: Regex = """<td class="Header__DataValue-xeaizz-4 cUoUJA">(.+?)</td>""".r

    val name=  nameRegex.findFirstMatchIn(doc).get.group(1)
    println("name = " + name)
    val age = ageRegex.findFirstMatchIn(doc).get.group(1)
    println("age = " + age)
    val sex = sexRegex.findFirstMatchIn(doc).get.group(1)
    println("sex = " + sex)

    getDob(age)

    //ToDO convert string to date
    return Horse(name, age,sex)
  }


  def getDob(age: String) : String = {
    val dobRegex: Regex = """\d{2}th\s[a-zA-Z]+\s\d{4}""".r
    val dob=  dobRegex.findFirstMatchIn(age).get.toString()
    println(dob)
    dob
  }

  case class Horse(name: String, dob: String, sex: String)
}
