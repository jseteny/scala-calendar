package hu.matan.calendar.util.saveload

import hu.matan.calendar.Meeting
import hu.matan.calendar.Meeting._
import org.joda.time.DateTime
import org.scalatest.{FlatSpec, Matchers}

class MeetingSaverTest extends FlatSpec with Matchers {

  implicit val today = DateTime.now().withTimeAtStartOfDay()

  "save " should "load " in {
    MeetingSaver.save(Meeting("Joe", today, NoTime))
    MeetingSaver.load("Joe") should be(List.empty)
  }
}
