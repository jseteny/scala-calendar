package hu.matan.time

case class Time (hours: Int, minutes: Int) {
  def asMinutes: Int = hours * 60 + minutes
  def + (that: Time): Int = this.asMinutes + that.asMinutes
  def - (that: Time): Int = this.asMinutes - that.asMinutes
  override def toString : String = f"$hours%02d:$minutes%02d"
}
