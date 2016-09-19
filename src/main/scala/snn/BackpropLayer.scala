package snn

import snn.matrix._

trait BackpropLayer {

  /** Del-sub-z-sub-l of E */
  def bpDazzle: ColumnVector[Double]

  /** The error due to this layer */
  def bpErrGrad: ColumnVector[Double]

  /** The value of the first derivative of the activation function for this layer */
  def bpDfDa: ColumnVector[Double]

  /** The input to this layer */
  def bpIn: ColumnVector[Double]

  /** The output from this layer */
  def bpOut: ColumnVector[Double]

  /** The weights for this layer */
  def bpW: Matrix[Double]

  /** The activation for this layer */
  def bpA: Activation

}