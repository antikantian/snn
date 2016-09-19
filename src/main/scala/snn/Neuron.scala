package snn

import scala.math._

trait Neuron[A] {
  def activation: Activ[A]
  def activationP: ActivDeriv[A]
}

object Neuron {
  def sigmoidNeuron: Neuron[Double] = new Neuron[Double] {
    def activation: Activ[Double] = (a: Double) => sigmoid(a)
    def activationP: ActivDeriv[Double] = (a: Double) => sigmoidP(a)
  }

  def tanhNeuron: Neuron[Double] = new Neuron[Double] {
    def activation: Activ[Double] = (a: Double) => tanh(a)
    def activationP: ActivDeriv[Double] = (a: Double) => tanhP(a)
  }

  def reclu: Neuron[Double] = new Neuron[Double] {
    def activation: Activ[Double] = (a: Double) => reclu(a)
    def activationP: ActivDeriv[Double] = (a: Double) => recluP(a)
  }

  def sigmoid(x: Double): Double = {
    1 / (1 + exp(-1 * x))
  }

  def sigmoidP(a: Double): Double = {
    val s = sigmoid(a)
    s * (1 - s)
  }

  def tanhP(a: Double): Double = {
    val s = tanh(a)
    1 - pow(s, 2)
  }

  def reclu(a: Double): Double = {
    log(1 + exp(a))
  }

  def recluP(a: Double): Double = {
    sigmoid(a)
  }

  def linear(x: Double): Double = x

  def softmax(x: Vec[Double]): Double = {
    sm(x)
  }

}

*/


