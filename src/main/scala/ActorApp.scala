import java.io.FileWriter

import akka.actor.{ActorSystem, Props}
import Actors.Worker

object ActorApp extends App{

  writeHorseTitleRaceToCSV("src/main/target/2K_clean_race_and_horse_data_with_age_and_pos_maps.csv")

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
  val masterSixteen = webCrawlingSystem.actorOf(Props[Worker], "sixteen")
  val masterSeventeen = webCrawlingSystem.actorOf(Props[Worker], "seventeen")
  val masterEightteen = webCrawlingSystem.actorOf(Props[Worker], "eightteen")
  val masterNineteen = webCrawlingSystem.actorOf(Props[Worker], "nineteen")
  val masterTwenty = webCrawlingSystem.actorOf(Props[Worker], "twenty")


  //  //partFour communicate
  Range(0,100001).map(n => {
    if(n % 20 == 0){
      masterOne ! "%07d".format(n)
    } else if(n % 20 == 1){
      masterTwo ! "%07d".format(n)
    }else if(n % 20 == 2){
      masterThree ! "%07d".format(n)
    } else if(n % 20 == 3) {
      masterFour ! "%07d".format(n)
    }else if(n % 20 == 4){
      masterFive ! "%07d".format(n)
    } else if(n % 20 == 5) {
      masterSix ! "%07d".format(n)
    }else if(n % 20 == 6){
      masterSeven ! "%07d".format(n)
    } else if(n % 20 == 7) {
      masterEight ! "%07d".format(n)
    } else if(n % 20 == 8) {
      masterNine ! "%07d".format(n)
    } else if(n % 20 == 9) {
      masterTen ! "%07d".format(n)
    }else if(n % 20 == 10) {
      masterEleven ! "%07d".format(n)
    }else if(n % 20 == 11) {
      masterTwelve ! "%07d".format(n)
    }else if(n % 20 == 12) {
      masterThirteen ! "%07d".format(n)
    }else if(n % 20 == 13) {
      masterFourteen ! "%07d".format(n)
    }else if(n % 20 == 14) {
      masterFifteen ! "%07d".format(n)
    }else if(n % 20 == 15) {
      masterSixteen ! "%07d".format(n)
    }else if(n % 20 == 16) {
      masterSeventeen ! "%07d".format(n)
    }else if(n % 20 == 17) {
      masterEightteen ! "%07d".format(n)
    }else if(n % 20 == 18) {
      masterNineteen ! "%07d".format(n)
    } else{
      masterTwenty ! "%07d".format(n)
    }
  })

  Thread.sleep(2000000)
  webCrawlingSystem.terminate

  def writeHorseTitleRaceToCSV(filename:String): Unit = {
    val fw = new FileWriter(filename, true)
    try {
      fw.write(
        "ID,NAME,DOB,AGE OF HORSE,TRAINER,GENDER,SIRE,DAM,OWNER,DATE,POS,RAN,BHA,TYPE,COURSE,DISTANCE,GOING,CLASS,STARTING PRICE,POS_LABEL"+"\n"
      )
    }
    finally fw.close()
  }



}
