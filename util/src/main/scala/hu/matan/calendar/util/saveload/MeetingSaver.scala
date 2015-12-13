package hu.matan.calendar.util.saveload

import java.io.{FileInputStream, FileOutputStream}

import hu.matan.calendar.Meeting
import scodec.Codec
import scodec.bits.BitVector
import shapeless.Lazy.mkLazy

object MeetingSaver {

  private var list: List[Meeting] = List[Meeting]()

  def save(meeting: Meeting): Unit = {
    list = meeting :: list
    val out = new FileOutputStream("meeting-list.scodec")
    val bitVector: BitVector = Codec[List[Meeting]].encode(list).require
    out.write(bitVector.toByteArray)
  }

  def load(who: String): List[Meeting] = {
    val in = new FileInputStream("meeting-list.scodec")
    val decode = Codec[List[Meeting]].decode(BitVector.fromInputStream(in))
    decode.require.value.filter(_.who.equalsIgnoreCase(who))
  }
}
