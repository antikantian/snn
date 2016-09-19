package snn

import snn.matrix._

trait PropagatedLayer {

  /** The input to this layer. */
  def pInput: ColumnVector[Double]

  /** The output from this layer. */
  def pOutput: ColumnVector[Double]

  /** The value of the first derivative of the activation function for this layer. */
  def pDfDa: ColumnVector[Double]

  /** The weights for this layer. */
  def pWeights: Matrix[Double]

  /** The activation for this layer */
  def pActivation: Activation

}

trait PropagatedInputLayer {

  /** The output from this layer. */
  def pOutput: ColumnVector[Double]

}