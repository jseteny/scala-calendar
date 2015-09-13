package hu.matan.calendar

import org.joda.time.{DateTime, LocalTime}

case class Meeting(who: String, date: DateTime, at: Option[LocalTime])

object Meeting {
  val NoTime = None
}
