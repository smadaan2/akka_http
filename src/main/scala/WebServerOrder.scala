import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import scala.io.StdIn
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Created by hai on 6/12/2017.
  */
object WebServerOrder extends App {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  case class Item(name: String, id: Long)

  case class Order(items: List[Item])

  def fetchItem(itemId: Long): Future[Option[Item]] = Future(Some(Item("shikha", 1)))

  def saveOrder(order: Order): Future[String] = ???

  implicit val itemFormat = jsonFormat2(Item)
  implicit val orderFormat = jsonFormat1(Order)

  val route: Route =

    get {
      path("items" / LongNumber) { id =>
        val maybeItem: Future[Option[Item]] = fetchItem(id)

        /*
                  onSuccess(maybeItem) {
                    case Some(item) => complete(item)
                    case None       => complete(StatusCodes.NotFound)
                  }
        */

        onComplete(maybeItem) {
          case Success(Some(item)) => complete(item)
          case Success(None) => complete(StatusCodes.NotFound)
          case Failure(ex) => complete(StatusCodes.NotFound)
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8081)
  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate())

}
