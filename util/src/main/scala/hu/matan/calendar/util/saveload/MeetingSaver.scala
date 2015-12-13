package hu.matan.calendar.util.saveload

import java.io.{FileInputStream, FileOutputStream}

import hu.matan.calendar.Meeting
import scodec.Codec
import scodec.bits.BitVector
import shapeless.Lazy.mkLazy

object MeetingSaver {

  private var list: List[Meeting] = List[Meeting]()


  http :// stackoverflow.com / questions / 32790957 / define - codec - for -recursive - data - structure

  https :// github.com / scodec / scodec / tree / master / shared / src / test / scala / scodec / codecs


  val codecm: Codec[Meeting] = scodec.codecs.lazily(scodec.codecs.string :: scodec.codecs.long :: scodec.codecs.long)

  def codec: Codec[List[Meeting]] = scodec.codecs.lazily(scodec.codecs.list(codec)).as[List[Meeting]]

  def save(meeting: Meeting): Unit = {
    list = meeting :: list
    val out = new FileOutputStream("meeting-list.scodec")
    val bitVector: BitVector = codec.encode(list).require
    out.write(bitVector.toByteArray)
  }

  def load(who: String): List[Meeting] = {
    val in = new FileInputStream("meeting-list.scodec")
    val decode = Codec[List[Meeting]].decode(BitVector.fromInputStream(in))
    decode.require.value.filter(_.who.equalsIgnoreCase(who))
  }
}
