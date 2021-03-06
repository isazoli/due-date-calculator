package isaszol

import com.google.common.annotations.VisibleForTesting

import scala.annotation.tailrec

/**
  * Simple date representation with the necessary operations in the companion object.
  *
  * @param year years A.D.
  * @param month months starting from 1. So January represented with value 1.
  * @param day days starting from 1.
  * @param dayOfWeek day of week represented with it's english name with capital letter. So wednesday is "Wednesday"
  *
  * Example: 2014 March 4, tuesday will be represented with MyDate(2014, 3, 4, "Tuesday")
  */
case class MyDate(year: Int, month: Int, day: Int, dayOfWeek: String)

object MyDate {
  // Day of week operations
  private val Weekdays = Seq("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
  private val Weekend = Seq("Saturday", "Sunday")
  private val DayOfWeekValues = Weekdays ++ Weekend
  private val NextDayOfWeek = DayOfWeekValues.zip(DayOfWeekValues.tail :+ "Monday").toMap
  // Number of days in a month
  private val NumberOfDaysPerMonthInLeapYear = numberOfDaysPerMonthInYear(daysInFebruary = 29)
  private val NumberOfDaysPerMonthInNormalYear = numberOfDaysPerMonthInYear(daysInFebruary = 28)

  private def numberOfDaysPerMonthInYear(daysInFebruary: Int) = Seq(31, daysInFebruary, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
  // year end
  @VisibleForTesting
  private[isaszol] def isYearEnd(month: Int, day: Int): Boolean = month == 12 && day == 31
  // month end
  @VisibleForTesting
  private[isaszol] def isMonthEnd(year: Int)(month: Int, day: Int): Boolean = {
    val NumberOfDaysInAMonthFunction = if (year % 4 == 0) NumberOfDaysPerMonthInLeapYear else NumberOfDaysPerMonthInNormalYear
    NumberOfDaysInAMonthFunction(month - 1) == day
  }

  // tomorrow
  @VisibleForTesting
  private[isaszol] def tomorrow(currentDate: MyDate): MyDate = {
    val MyDate(year, month, day, dayOfWeek) = currentDate
    if (isYearEnd(month, day))
      MyDate(year + 1, 1, 1, NextDayOfWeek(dayOfWeek))
    else if (isMonthEnd(year)(month, day))
      MyDate(year, month + 1, 1, NextDayOfWeek(dayOfWeek))
    else
      MyDate(year, month, day + 1, NextDayOfWeek(dayOfWeek))
  }

  @tailrec
  def stepWorkdays(date: MyDate, workdays: Int): MyDate = {
    if (Weekend.contains(date.dayOfWeek))
      stepWorkdays(tomorrow(date), workdays)
    else if (workdays == 0)
      date
    else
      stepWorkdays(tomorrow(date), workdays - 1)
  }
}