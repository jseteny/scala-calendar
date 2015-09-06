package hu.matan.calendar

import org.joda.time.DateTime

case class Meeting(who: String, date:DateTime, at: Option[Int])

object Meeting {
  val NoTime = None
}
