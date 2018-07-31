package isaszol

import org.junit.Test
import org.scalatest.Matchers

import DueDateCalculator.determineDueDateFor

class DueDateCalculatorTest extends Matchers {

  @Test
  def withZeroTurnaroundTimeShouldReturnTheInput(): Unit = {
    val input = DateAndTime(MyDate(2011, 1, 12, "Monday"), MyMilitaryTime(1100))
    determineDueDateFor(input, turnaroundInHours = 0) shouldBe input
  }

  @Test
  def testWholeDaysTurnaround(): Unit = {
    determineDueDateFor(
      DateAndTime(MyDate(2013, 3, 15, "Wednesday"), MyMilitaryTime(1444)),
      turnaroundInHours = 3 * 8
    ) shouldBe DateAndTime(MyDate(2013, 3, 20, "Monday"), MyMilitaryTime(1444))
  }

  @Test
  def testTurnaroundWithIntraDayHours(): Unit = {
    determineDueDateFor(
      DateAndTime(MyDate(2015, 4, 11, "Tuesday"), MyMilitaryTime(1432)),
      turnaroundInHours = 3 * 8 + 2
    ) shouldBe DateAndTime(MyDate(2015, 4, 14, "Friday"), MyMilitaryTime(1632))
  }

  @Test
  def testWithDayRollForward(): Unit = {
    determineDueDateFor(
      DateAndTime(MyDate(2016, 6, 1, "Friday"), MyMilitaryTime(1432)),
      turnaroundInHours = 7
    ) shouldBe DateAndTime(MyDate(2016, 6, 4, "Monday"), MyMilitaryTime(1332))
  }


}
