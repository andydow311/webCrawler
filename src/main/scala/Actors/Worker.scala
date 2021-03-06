package Actors
import java.io.FileWriter
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Locale

import akka.actor.Actor
import org.joda.time.format.DateTimeFormat

import scala.util.matching.Regex
import org.jsoup._

class Worker extends Actor{
  //Todo = This Needs changing so that it persists to a relational DB
  val arrays = Set()

  def receive: PartialFunction[Any,Unit] ={
    case id:String => {
      val url = "https://www.sportinglife.com/racing/profiles/horse/" + id
      println(s"I have received $id to make $url")
      val doc = Jsoup.connect(url).get().toString
      val horse = getHorse(id, doc)
      getRaceDetails(horse, doc)
    }
    case _ =>  println("Nothing received here!!")
  }


  def getDocument(url: String): Unit ={
    Jsoup.connect(url).get()
  }

  def getHorse(id:String, doc:String): Array[String] = {

    val nameRegex: Regex = """<h1 data-test-id="header-title" class=\"Header__Title-xeaizz-0 ndttp\">(.+?)</h1>""".r
    val horseDataRegex: Regex = """<td class="Header__DataValue-xeaizz-4 cUoUJA">(.+?)</td>""".r

    val name=  nameRegex.findFirstMatchIn(doc).get.group(1).replace(",","")

    val horseData  = horseDataRegex.findAllMatchIn(doc).toList

    val dob = getDob(clean(horseData(0).toString()))

    val age  = convertDobToMonths(dob.trim)

    val trainer = getTrainer(clean(horseData(1).toString())).replace(",","")

    val sex = clean(horseData(2).toString())

    if(horseData.length == 6) {

      val sire = clean(horseData(3).toString()).replace(",","")

      val dam = clean(horseData(4).toString()).replace(",","")

      val owner = clean(horseData(5).toString()).replace(",","").replace("&amp;","&")

      return Array(
        id,
        name,
        dob,
        age.toString,
        trainer,
        sex,
        sire,
        dam,
        owner)

    }else if(horseData.length == 5){

      val sire = clean(horseData(3).toString())

      val dam = clean(horseData(4).toString())


      return Array(
        id,
        name,
        dob,
        age.toString,
        trainer,
        sex,
        sire,
        dam,
        "-")

    }else{

      return Array(
        id,
        name,
        dob,
        age.toString,
        trainer,
        sex,
        "-",
        "-",
        "-")
    }

  }

  def getRaceDetails(horse: Array[String] , doc:String): Unit ={
    val thisdoc = doc.replaceAll("\n","")

    val rowRegex: Regex = """<tr class="FormTable__StyledTr-sc-1xr7jxa-3 foFtQM">(.+?)</tr>""".r
    val fieldRegex: Regex = """<td class="FormTable__StyledTd-sc-1xr7jxa-4 iSKYZy">(.+?)</td>""".r
    val dateRegex: Regex = """<td class="FormTable__StyledTd-sc-1xr7jxa-4 iSKYZy"><a href=".*">(.+?)</a></td>""".r
    val otherRegex: Regex = """ <td class="FormTable__StyledTd-sc-1xr7jxa-4 iSKYZy">(.*?)</td>""".r
    val rows = rowRegex.findAllMatchIn(thisdoc).toList

    for (elem <- rows) {
      val sequence = Array("","","","","","","","","","","")

      val otherData  = otherRegex.findAllMatchIn(elem.toString()).toList

      for(elems <- otherData){
        if(sequence(0).trim().isEmpty){
          sequence(0) = cleanRaceData(elems.toString().replace(",",""))
        }else if(sequence(1).trim().isEmpty){
          val x = cleanRaceData(elems.toString())
          if(x.contains("/")){
            sequence(1) = x.split("/")(0).replace(",","")
            sequence(10) = mapPos(x.split("/")(0).replace(",","")).toString
            sequence(2) = x.split("/")(1).replace(",","")
          }else{
            sequence(1) = "-"
            sequence(2) = "-"
          }
        }else if(sequence(3).trim().isEmpty){
          sequence(3) = cleanRaceData(elems.toString().replace(",",""))
        }else if(sequence(4).trim().isEmpty){
          sequence(4) = cleanRaceData(elems.toString().replace(",",""))
        }else if(sequence(5).trim().isEmpty){
          sequence(5) = cleanRaceData(elems.toString().replace(",",""))
        }else if(sequence(6).trim().isEmpty){
          sequence(6) = cleanRaceData(elems.toString().replace(",",""))
        }else if(sequence(7).trim().isEmpty){
          sequence(7) = cleanRaceData(elems.toString().replace(",",""))
        }else if(sequence(8).trim().isEmpty){
          sequence(8) = cleanRaceData(elems.toString().replace(",",""))
        }else if(sequence(9).trim().isEmpty){
          sequence(9) = cleanRaceData(elems.toString().replace(",",""))
        }

      }


      writeRaceToCSV(horse,sequence)

    }

  }

  def mapPos(pos:String): Double ={
    if(pos.trim.equals("1") || pos.trim.equals("2") || pos.trim.equals("3")){
      1
    }else{
      0
    }

  }

  def cleanRaceData(str:String) :String = {
    val output = str.replaceAll("<td class=\"FormTable__StyledTd-sc-1xr7jxa-4 iSKYZy\">","")
      .replaceAll("</td>","")
      .replaceAll("<td class=\"FormTable__StyledTd-sc-1xr7jxa-4 iSKYZy\">","")
      .replaceAll("<a href=\".*\">","")
      .replaceAll("</a>","")

    if(output.trim.isEmpty){
      "-"
    }else{
      output
    }

  }

  def clean(str: String) : String = {
    str.replaceAll("<td class=\"Header__DataValue-xeaizz-4 cUoUJA\">","")
      .replaceAll("</td>", "")
      .trim()
  }

  def getDob(age: String) : String = {
    val dobRegex: Regex = """\d{1,2}th\s[a-zA-Z]+\s\d{4}|\d{1,2}st\s[a-zA-Z]+\s\d{4}|\d{1,2}rd\s[a-zA-Z]+\s\d{4}|\d{1,2}nd\s[a-zA-Z]+\s\d{4}""".r
    dobRegex.findFirstMatchIn(age).get.toString()
  }

  def getTrainer(trainer: String): String = {
    trainer.replaceAll("<a href=\"/racing/profiles/trainer/\\d+\">", "")
      .replaceAll("</a>","")
      .trim()
  }


  def writeRaceToCSV(horse: Array[String] , race: Array[String]): Unit = {
    val fw = new FileWriter("src/main/target/2K_clean_race_and_horse_data_with_age_and_pos_maps.csv", true)
    try {
      fw.write(
        horse.mkString(",")+","+race.mkString(",") +"\n"
      )
    }
    finally fw.close()
  }

  def convertDobToMonths(birthDate:String): Long={

    val dayStr = birthDate.trim().split(" ")(0)
    val year = birthDate.trim().split(" ")(2)
    val month = birthDate.trim().split(" ")(1).toUpperCase
    val day = """\d+""".r findFirstIn(dayStr)
    val today = LocalDate.now


    val format = DateTimeFormat.forPattern("MMM")
    val instance = format.withLocale(Locale.ENGLISH).parseDateTime(month)

    val yearFormat = DateTimeFormat.forPattern("YYYY")
    val yearInstance = yearFormat.withLocale(Locale.ENGLISH).parseDateTime(year)

    val month_number = instance.getMonthOfYear
    val month_text = instance.monthOfYear.getAsText(Locale.ENGLISH)

    val  year_number = yearInstance.getYear

    val birthday = LocalDate.of(year_number,month_number, day.get.toInt)

    ChronoUnit.MONTHS.between(birthday, today)


  }



}

