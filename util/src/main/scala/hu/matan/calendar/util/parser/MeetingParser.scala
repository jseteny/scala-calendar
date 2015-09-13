package hu.matan.calendar.util.parser

import java.util.Locale

import hu.matan.calendar.Meeting
import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, LocalTime}

import scala.util.parsing.combinator.RegexParsers


class MeetingParser(implicit val givenDate: DateTime) extends RegexParsers {

  def command = meet ~> opt("with") ~> rep(not("on") ~> not("at") ~> namePart) ~
    opt("on" ~> date) ~
    opt("at" ~> time) ^^ {

    case nameList ~ None ~ None => Meeting(nameList.mkString(" "), givenDate, None)
    case nameList ~ None ~ Some(t) => Meeting(nameList.mkString(" "), givenDate, Some(LocalTime.parse(t)))
    case nameList ~ Some(d) ~ None => Meeting(nameList.mkString(" "), parseDate(d), None)
    case nameList ~ Some(d) ~ Some(t) => Meeting(nameList.mkString(" "), parseDate(d), Some(LocalTime.parse(t)))
  }

  def parseDate(d: String): DateTime = {
    try {
      DateTime.parse(d)
    } catch {
      case _: IllegalArgumentException => DateTimeFormat.shortDate().withLocale(Locale.ENGLISH).parseDateTime(d)
    }
  }

  def namePart: Parser[String] = """\w+""".r

  def date: Parser[String] = """[\w\.\-/]+""".r

  def time: Parser[String] = """\d+:\d+""".r | """\d+""".r

  def meet = "meet"

  def parse(source: String): Meeting = parseAll(command, source) match {
    case Success(expression, _) => expression
    case f: NoSuccess => throw new IllegalArgumentException(f.msg)
  }

  def parse(source: java.io.Reader): Meeting = parseAll(command, source) match {
    case Success(expression, _) => expression
    case NoSuccess(err, next) => throw new IllegalArgumentException("failed to parse " +
      "(line " + next.pos.line + ", column " + next.pos.column + "):\n" +
      err + "\n" + next.pos.longString)
  }
}
