package hu.matan.calendar.util.parser

import java.util.Locale

import hu.matan.calendar.Meeting
import hu.matan.calendar.Meeting.NoTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, LocalTime}
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
    result should be(Meeting("Jane", today, Some(LocalTime.parse("16"))))
  }

  "meet with Jane Kennedy at 17" should "mean a Meeting with Jane Kennedy at 17" in {

    val input = "meet with Jane Kennedy at 17"
    val result = parser.parse(input)
    result should be(Meeting("Jane Kennedy", today, Some(LocalTime.parse("17"))))
  }

  "meet Jane Kennedy at 18" should "mean a Meeting with Jane Kennedy at 18" in {

    val input = "meet Jane Kennedy at 18"
    val result = parser.parse(input)
    result should be(Meeting("Jane Kennedy", today, Some(LocalTime.parse("18"))))
  }

  private val givenDate: String = "2014-12-01"
  s"meet Jane Kennedy on $givenDate at 18" should s"mean a Meeting on $givenDate with Jane Kennedy at 18" in {

    val input = s"meet Jane Kennedy on $givenDate at 18"
    val result = parser.parse(input)
    result should be(Meeting("Jane Kennedy", DateTime.parse(givenDate), Some(LocalTime.parse("18"))))
  }

  private val givenTime: String = "14:30"
  s"meet Joel Spolsky on $givenDate at $givenTime" should s"mean a Meeting on $givenDate with Joel Spolsky at $givenTime" in {

    val input = s"meet Joel Spolsky on $givenDate at $givenTime"
    val result = parser.parse(input)
    result should be(Meeting("Joel Spolsky", DateTime.parse(givenDate), Some(LocalTime.parse(givenTime))))
  }

  def shortEnglishDateFormat = DateTimeFormat.shortDate().withLocale(Locale.ENGLISH)

  private val shortEnglishDate = shortEnglishDateFormat.print(DateTime.parse(givenDate))
  s"meet Joel Spolsky on $shortEnglishDate at $givenTime" should s"mean a Meeting on $shortEnglishDate with Joel Spolsky at $givenTime" in {

    val input = s"meet Joel Spolsky on $shortEnglishDate at $givenTime"
    val result = parser.parse(input)

    result should be(Meeting("Joel Spolsky", shortEnglishDateFormat.parseDateTime(shortEnglishDate), Some(LocalTime.parse(givenTime))))
  }

  def longEnglishDateFormat = DateTimeFormat.longDate().withLocale(Locale.ENGLISH)

  private val longEnglishDate = longEnglishDateFormat.print(DateTime.parse(givenDate))
  s"meet Joel Spolsky on $longEnglishDate at $givenTime" should s"mean a Meeting on $longEnglishDate with Joel Spolsky at $givenTime" in {

    val input = s"meet Joel Spolsky on $longEnglishDate at $givenTime"
    val result = parser.parse(input)

    result should be(Meeting("Joel Spolsky", longEnglishDateFormat.parseDateTime(longEnglishDate), Some(LocalTime.parse(givenTime))))
  }
  
  //  2nd  TODO produce combinations of input values using property_based_testing. See http://www.scalatest.org/user_guide/property_based_testing
}
