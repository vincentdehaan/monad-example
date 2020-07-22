package nl.vindh.monadexample

trait TraceableLogging {
  val logger = new Logger
}

class Logger {
  def info(log: String)(implicit traceId: String): Unit =
    println(s"INFO traceId=$traceId $log")
}