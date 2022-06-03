import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer

import java.net.URLEncoder
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object Main {
  //  val result = Http("https://api.bilibili.com/x/web-interface/popular").param("ps", "20").param("pn", "1").asString
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  import system.dispatcher

  def getRequeset(ps: Int, pn: Int) = {
    val request = HttpRequest(
      method = HttpMethods.GET,
      uri = s"https://api.bilibili.com/x/web-interface/popular?ps=${ps}&pn=${pn}"
    )
    request
  }


  def simpleRequest(ps:Int,pn:Int)= {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(getRequeset(ps,pn))
    responseFuture.flatMap(_.entity.toStrict(2 seconds)).map(_.data.utf8String).foreach(println)
  }

  def main(args: Array[String]): Unit = {
    for(i<-1 to 20){
      simpleRequest(20,i)
    }
  }
}
