import java.io.{File, PrintWriter}

import scala.collection.mutable
import scala.io.Source

/**
  * Created by Zach on 10/18/16.
  */
object FileIO {

  def main(args: Array[String]): Unit = {

    val purchases = mutable.MutableList[(String, String, String, String, String)]()
    def prompt(s: String) = {
      println(s);
      io.StdIn.readLine()
    }

    Source.fromFile("purchases.csv").getLines().drop(1).foreach(line => {
      var purchase = {
        val Array(customer_id, date, credit_card, cvv, category) = line.split(",").map(_.trim)
        (customer_id, date, credit_card, cvv, category)
      }
      purchases += purchase
    })

    val userInput = prompt("What category would you like to see? [Furniture] [Alcohol] [Toiletries] [Shoes] [Food] [Jewelry]")
    userInput match {
      case s => {
        val text = purchases.filter(_._5 == s).map(x => s"Customer: ${x._1} Date: ${x._2}")
        if (text.isEmpty) println("ERROR: category not found")
        else {
          text.foreach(x => println(x))
          val pw = new PrintWriter(new File("filtered_purchases.prn"))
          text.foreach(x => pw.write(x + "\n"))
          pw.close
        }
      }
    }
  }
}
