
// http://scodec.org/guide/Combined+Pages.html


import scodec.Codec
import scodec.codecs.implicits._

case class Point(x: Int, y: Int)

case class Line(start: Point, end: Point)

case class Arrangement(lines: Vector[Line])

val arr = Arrangement(Vector(
  Line(Point(0, 0), Point(10, 10)),
  Line(Point(0, 10), Point(10, 0))))

val arrBinary = Codec.encode(arr).require

val decoded = Codec[Arrangement].decode(arrBinary).require.value