package snn

trait Activation {

  /** The activation function */
  def f(x: Double): Double

  /** The derivative of the activation function */
  def dfdx(x: Double): Double

}

object Activation {

  case object Sigmoid extends Activation {
    def f(x: Double): Double =
      1 / (1 + math.exp(-1 * x))

    def dfdx(x: Double): Double =
      f(x) * (1 - x)
  }

  /** TanH */
  case object TanH extends Activation {
    def f(x: Double): Double =
      math.tanh(x)

    def dfdx(x: Double): Double =
      f(x) - math.pow(x, 2)
  }

  /** Identity */
  case object Identity extends Activation {
    def f(x: Double): Double = x

    def dfdx(x: Double): Double = 1
  }

  /** ArcTan */
  case object ArcTan extends Activation {
    def f(x: Double): Double =
      -1  * math.tan(x)

    def dfdx(x: Double): Double =
      1 / (math.pow(x, 2) + 1)
  }

}