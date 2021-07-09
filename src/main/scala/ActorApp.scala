import java.io.FileWriter

import akka.actor.{ActorSystem, Props}
import Actors.Worker

object ActorApp extends App{

  //write out titles
  writeHorseTitleHorseToCSV("src/main/target/165k_clean_horse_data.csv")
  writeHorseTitleRaceToCSV("src/main/target/165" +
    "k_clean_race_data.csv")

  //partone Create System
  val webCrawlingSystem = ActorSystem("crawl")

  //partThree instatiate Actors
  val masterOne = webCrawlingSystem.actorOf(Props[Worker], "one")
  val masterTwo = webCrawlingSystem.actorOf(Props[Worker], "two")
  val masterThree = webCrawlingSystem.actorOf(Props[Worker], "three")
  val masterFour = webCrawlingSystem.actorOf(Props[Worker], "four")
  val masterFive = webCrawlingSystem.actorOf(Props[Worker], "five")
  val masterSix = webCrawlingSystem.actorOf(Props[Worker], "six")
  val masterSeven = webCrawlingSystem.actorOf(Props[Worker], "seven")
  val masterEight = webCrawlingSystem.actorOf(Props[Worker], "eight")
  val masterNine = webCrawlingSystem.actorOf(Props[Worker], "nine")
  val masterTen = webCrawlingSystem.actorOf(Props[Worker], "ten")
  val masterEleven = webCrawlingSystem.actorOf(Props[Worker], "eleven")
  val masterTwelve = webCrawlingSystem.actorOf(Props[Worker], "twelve")
  val masterThirteen = webCrawlingSystem.actorOf(Props[Worker], "thirteen")
  val masterFourteen = webCrawlingSystem.actorOf(Props[Worker], "fourteen")
  val masterFifteen = webCrawlingSystem.actorOf(Props[Worker], "fifteen")

//  //partFour communicate
  Range(150001,165001).map(n => {
    if(n % 15 == 0){
      masterOne ! "%07d".format(n)
    } else if(n % 15 == 1){
      masterTwo ! "%07d".format(n)
    }else if(n % 15 == 2){
      masterThree ! "%07d".format(n)
    } else if(n % 15 == 3) {
      masterFour ! "%07d".format(n)
    }else if(n % 15 == 4){
      masterFive ! "%07d".format(n)
    } else if(n % 15 == 5) {
      masterSix ! "%07d".format(n)
    }else if(n % 15 == 6){
      masterSeven ! "%07d".format(n)
    } else if(n % 15 == 7) {
      masterEight ! "%07d".format(n)
    } else if(n % 15 == 8) {
      masterNine ! "%07d".format(n)
    } else if(n % 15 == 9) {
      masterTen ! "%07d".format(n)
    }else if(n % 15 == 10) {
      masterEleven ! "%07d".format(n)
    }else if(n % 15 == 11) {
      masterTwelve ! "%07d".format(n)
    }else if(n % 15 == 12) {
      masterThirteen ! "%07d".format(n)
    }else if(n % 15 == 13) {
      masterFourteen ! "%07d".format(n)
    }else{
      masterFifteen ! "%07d".format(n)
    }
  })

  Thread.sleep(900000)
  webCrawlingSystem.terminate

  def writeHorseTitleHorseToCSV(filename:String): Unit = {
    val fw = new FileWriter(filename, true)
    try {
      fw.write(
        "ID,NAME,DOB,TRAINER,GENDER,SIRE,DAM,OWNER"+"\n"
      )
    }
    finally fw.close()
  }

  def writeHorseTitleRaceToCSV(filename:String): Unit = {
    val fw = new FileWriter(filename, true)
    try {
      fw.write(
        "Horse_id,Date,Pos,Ran,BHA,Type,Course,Distance,Going,Class,Starting Price"+"\n"
      )
    }
    finally fw.close()
  }



}
