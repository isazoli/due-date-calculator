package isaszol

import isaszol.MyMilitaryTime._
import org.junit.Test
import org.scalatest.Matchers

class MyMilitaryTimeTest extends Matchers {

  @Test
  def validateMilitaryTimeFromHours(): Unit = {
    toMilitaryTime(10) shouldBe 1000
    toMilitaryTime(9) shouldBe 900
  }

  @Test
  def checkRollForwardToOfficeHours(): Unit = {
    rollForwardToOfficeHours(2115) shouldBe 1315
  }

  @Test
  def stepInOfficeHoursShouldWork(): Unit = {
    stepWorkinghours(MyMilitaryTime(1200), 3) shouldBe(MyMilitaryTime(1500), 0)
  }

  @Test
  def stepZeroHoursShouldReturnTheInput(): Unit = {
    val input = MyMilitaryTime(1234)
    stepWorkinghours(input, 0) shouldBe(input, 0)
  }

  @Test
  def stepWithRollForwardShouldWork(): Unit = {
    val input = MyMilitaryTime(1601)
    stepWorkinghours(input, 4) shouldBe(MyMilitaryTime(1201), 1)
  }


}