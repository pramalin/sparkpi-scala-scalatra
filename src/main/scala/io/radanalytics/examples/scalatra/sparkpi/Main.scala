package io.radanalytics.examples.scalatra.sparkpi

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener

object Main {

    def main( args : Array[ String ] ) : Unit = {
        val port = 8080
        val server = new Server( port )
        val context = new WebAppContext()

        context.setContextPath( "/" )
        context.setResourceBase( "src/main/webapp" )
        context.setInitParameter( ScalatraListener.LifeCycleKey, "io.radanalytics.examples.scalatra.sparkpi.ScalatraInit" ) // Override the Scalatra default for ScalatraBootstrap in default package
        context.addEventListener( new ScalatraListener )

        server.setHandler( context )
        server.start()
        server.join()
    }

}
