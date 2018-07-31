package isaszol

import org.scalatest.Matchers
import MyDate._
import org.junit.Test

class MyDateTest extends Matchers {

  @Test
  def testYearEnd(): Unit = {
    isYearEnd(12, 31) should be (true)
    isYearEnd(10, 31) should be (false)
  }

  @Test
  def testMonEndHappyPath(): Unit = {
    isMonthEnd(2011)(10, 31) should be (true)
    isMonthEnd(2011)(10, 30) should be (false)
    isMonthEnd(2018)(2, 28) should be (true)
    isMonthEnd(2016)(2, 28) should be (false)
  }

  @Test
  def testMonEndForLeapYear(): Unit = {
    isMonthEnd(2018)(2, 28) should be (true)
    isMonthEnd(2016)(2, 28) should be (false)
    isMonthEnd(2016)(2, 29) should be (true)
  }

  @Test
  def testTomorrowWithNewYearsEve(): Unit = {
    tomorrow(MyDate(2017, 12, 31, "Friday")) shouldBe MyDate(2018, 1, 1, "Saturday")
  }

  @Test
  def testTomorrowWithMonthEnd(): Unit = {
    tomorrow(MyDate(2016, 2, 29, "Sunday")) shouldBe MyDate(2016, 3, 1, "Monday")
  }

  @Test
  def testTomorrowWithSimpleDayIncrease(): Unit = {
    tomorrow(MyDate(2018, 5, 5, "Wednesday")) shouldBe MyDate(2018, 5, 6, "Thursday")
  }

  @Test
  def testStepWorkdays(): Unit = {
    stepWorkdays(MyDate(2018, 6, 7, "Friday"), 1) shouldBe MyDate(2018, 6, 10, "Monday")
    stepWorkdays(MyDate(2018, 5, 5, "Wednesday"), 5) shouldBe MyDate(2018, 5, 12, "Wednesday")
  }

}