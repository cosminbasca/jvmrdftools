package com.rdftools

/**
 * Created by basca on 17/09/14.
 */
case class LubmConfig(port: Int = -1,
                      dieOnBrokenPipe: Boolean = true,
                      portNotificationPrefix: String = "SERVER PORT=",
                      numThreads: Int = 10,
                      asynchronous: Boolean = false,
                      numAsyncWorkers: Int = 10,
                      other: Seq[String] = Seq())

object LubmGenerator extends App {
  override def main(args: Array[String]) = {
//    val parser = new scopt.OptionParser[LubmConfig]("localNotifierServer") {
//      head(BuildInfo.name, BuildInfo.version)
//
//      opt[Int]('p', "port") action {
//        (x, c) => c.copy(port = x)
//      } text "port is the port to bind to"
//
//      opt[Unit]("die_on_broken_pipe") optional() action {
//        (_, c) => c.copy(dieOnBrokenPipe = true)
//      } text "if set to true (default) the server will exit when the parent starting process exists"
//
//      opt[String]('n', "port_notification_prefix") action {
//        (x, c) => c.copy(portNotificationPrefix = x)
//      } text "the port notification prefix used (to detect the port reporting line)"
//
//      opt[Int]('t', "num_threads") action {
//        (x, c) => c.copy(numThreads = x)
//      } text "the number of threads to allocate for the worker pool (default=10)"
//
//      opt[Unit]("asynchronous") optional() action {
//        (_, c) => c.copy(asynchronous = true)
//      } text "if set to true (default is false) the context is wrapped by an asynchronous context"
//
//      opt[Int]('w', "num_async_workers") action {
//        (x, c) => c.copy(numAsyncWorkers = x)
//      } text "the number of async worker threads to allocate for the async executor (default=10), this parameter is ignored if asynchronous is false"
//
//      arg[String]("<other>...") unbounded() optional() action {
//        (x, c) => c.copy(other = c.other :+ x)
//      } text "optional unbounded args"
//    }
//
//    parser.parse(args, LubmConfig()) map { config =>
//      serveForever(config.port, config.dieOnBrokenPipe, config.portNotificationPrefix, config.numThreads, config.asynchronous, config.numAsyncWorkers, config.other)
//    } getOrElse {
//      println("arguments could not be parsed.")
//    }
  }
}
