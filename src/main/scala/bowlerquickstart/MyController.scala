package bowlerquickstart

import org.bowlerframework.controller.{Controller,FunctionNameConventionRoutes}
import org.bowlerframework.model.{ ParameterMapper, Validations}
import org.bowlerframework.view.{Renderable}
import java.io.File

/**
 * 
 * extends:
 * - Controller: used to construct routes and deal with them by providing functions that respond to routes.
 * - ParameterMapper: takes a request and maps any values into beans or other objects.
 * - Validations: validation enables the Controller
 * - Renderable: allows you to render View Model objects.
 */

class MyController extends Controller with ParameterMapper with Validations with Renderable  with FunctionNameConventionRoutes {


  // simple, no args render, just renders the root view of /views/GET/index (or http 204 for JSON)
 // views are resolved by view-root ("/view" on the classpath by default) + HTTP Method + path,
// in this case /views/GET/index. The ending of the template file (mustache, ssp, jade or scaml) will be auto-resolved in the order mentioned here.
// for named params, the ":" of the Scalatra route definition will be replaced by "_" when looking up on the classpath.
  def `GET /` = render
  
  def `POST /`(file:Test) = {
    val filename = file.filename
    render(new Status("file exists on filesystem %s".format(new File(filename).exists())))
  }
}

case class Status(val status:String)

case class Test(val file:List[String]){
  val StoreLocation = """.*StoreLocation=([^\,]*)\,.*""".r
  def filename = file.head match {
	  case StoreLocation(location) => location
	  case _ => "nothing " + file.head
	}
}
