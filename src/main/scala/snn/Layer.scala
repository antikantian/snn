package snn

import snn.matrix._

trait Layer {
  def weights: Matrix[Double]
  def activation: Activation
}