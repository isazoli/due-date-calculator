package isaszol

import com.google.common.annotations.VisibleForTesting

/**
  * Time represented in "Military time" form.
  *
  * @see http://militarytimegeek.com/military-time.html
  */
case class MyMilitaryTime(hoursAndMinutes: Int)

object MyMilitaryTime {

  private val StartOfOfficeHours = toMilitaryTime(9)
  private val EndOfOfficeHours = toMilitaryTime(17)
  private val PlusOneDay = 1
  private val NoRollForward = 0

  @VisibleForTesting
  private[isaszol] def toMilitaryTime(hours: Int): Int = hours * 100

  @VisibleForTesting
  private[isaszol] def rollForwardToOfficeHours(minutes: Int): Int = StartOfOfficeHours + (minutes % EndOfOfficeHours)

  def stepWorkinghours(currentTime: MyMilitaryTime, hoursToAdd: Int): (MyMilitaryTime, Int) = {
    currentTime.hoursAndMinutes + toMilitaryTime(hoursToAdd) match {
      case afterOfficeHours if afterOfficeHours > EndOfOfficeHours =>
        (MyMilitaryTime(rollForwardToOfficeHours(afterOfficeHours)), PlusOneDay)
      case adjustedTime =>
        (MyMilitaryTime(adjustedTime), NoRollForward)
    }
  }

}