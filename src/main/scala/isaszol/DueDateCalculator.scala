package isaszol

import isaszol.MyDate.stepWorkdays

object DueDateCalculator {

  private val WorkingHoursPerDay = 8

  def dueDate(submitDateAndTime: DateAndTime, turnaroundInHours: Int): DateAndTime = {
    val DateAndTime(submitDate, submitTime) = submitDateAndTime
    val wholeWorkingDays = turnaroundInHours / WorkingHoursPerDay
    val plusHours = turnaroundInHours % WorkingHoursPerDay
    (wholeWorkingDays, plusHours) match {
      case (0, 0) => submitDateAndTime
      case (workdays, 0) => DateAndTime(stepWorkdays(submitDate, workdays), submitTime)
      case (workdays, hours) =>
        val (adjustedTime, daysToAdd) = MyMilitaryTime.stepWorkinghours(submitTime, hours)
        DateAndTime(stepWorkdays(submitDate, workdays + daysToAdd), adjustedTime)
    }
  }

}

case class DateAndTime(myDate: MyDate, myTime: MyMilitaryTime)