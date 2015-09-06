package hu.matan.calendar.util.parser

import hu.matan.calendar.Meeting
import hu.matan.calendar.Meeting.NoTime
import org.joda.time.DateTime
import org.scalatest.{FlatSpec, Matchers}

class MeetingParserTest extends FlatSpec with Matchers {

  implicit val today = DateTime.now().withTimeAtStartOfDay()
  val parser: MeetingParser = new MeetingParser()

  "meet with Joe" should "mean a Meeting with Joe" in {

    val input = "meet with Joe"
    val result = parser.parse(input)
    result should be(Meeting("Joe", today, NoTime))
  }

  "meet Joe" should "mean a Meeting with Joe" in {

    val input = "meet Joe"
    val result = parser.parse(input)
    result should be(Meeting("Joe", today, NoTime))
  }

  "meet with Jane at 16" should "mean a Meeting with Jane at 16" in {

    val input = "meet with Jane at 16"
    val result = parser.parse(input)
    result should be(Meeting("Jane", today, Some(16)))
  }

  "meet with Jane Kennedy at 17" should "mean a Meeting with Jane Kennedy at 17" in {

    val input = "meet with Jane Kennedy at 17"
    val result = parser.parse(input)
    result should be(Meeting("Jane Kennedy", today, Some(17)))
  }

  "meet Jane Kennedy at 18" should "mean a Meeting with Jane Kennedy at 18" in {

    val input = "meet Jane Kennedy at 18"
    val result = parser.parse(input)
    result should be(Meeting("Jane Kennedy", today, Some(18)))
  }

  private val givenDate: String = "2014-12-01"
  s"meet Jane Kennedy on $givenDate at 18" should s"mean a Meeting on $givenDate with Jane Kennedy at 18" in {

    val input = "meet Jane Kennedy on " + givenDate + " at 18"
    val result = parser.parse(input)
    result should be(Meeting("Jane Kennedy", DateTime.parse(givenDate), Some(18)))
  }
}
