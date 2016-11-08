
import scala.io.Source
import scala.collection.immutable.ListMap

var i = 0
val map = collection.mutable.Map[String, String]()

for (l <- Source.fromFile("master.csv").getLines()) {
  val arr = l.split(";")
  if (arr.size > 3){    
    val country = arr(3).trim()
    val code = arr(4).trim()
    //map(country) = code
    val agency = arr(5).trim()
    val org = arr(6).trim()
    //println(country)
    //map(agency) = org
    //val cluster = arr(7).trim()
    //map(cluster) = cluster
    val item = arr(8).trim().split(' ').map(_.capitalize).mkString(" ")
    map(item) = item
  }
}
//sort map
val smap = ListMap(map.toSeq.sortBy(_._1):_*)
//println(smap)

smap.foreach { x =>
  i = i + 1
  //println(i + ";" + x._1 + ";" + x._2 + ";;")
  println(i + ";"+ x._1 + ";" + x._2 + ";") 
}

//println("Finished")
