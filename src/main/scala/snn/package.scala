package object snn {

  import matrix._

  type ColumnVector[A] = Matrix[A]

  /**

  /** Activation function */
  type Activ[A] = A => A

  /** Derivative of an activation function */
  type ActivDeriv[A] = A => A

  type Connectivity[A] = Int => Int => Matrix[A]

  type Randomization[G] = G => RandomTransform[G] => Int => Int => (Matrix[Double], Vec[Double])

  type RandomTransform[A] = List[A] => List[A]

  type CostF[A] = Vector[A] => Vector[A] => A

  type CostFP[A] = Vector[A] => Vector[A] => Vector[A]

  type TrainingData[A] = (Vec[A], Vec[A])

  type Selection[A] = List[TrainingData[A]] => List[List[TrainingData[A]]]

  type TrainCompletionPredicate[A] = Network[A] => BackpropTrainer[A] => List[TrainingData[A]] => Int => Boolean

  /**
   * Container for:
   * 1) Receptive field size
   * 2) Stride
   * 3) Zero-padding
   * 4) Number of filters
   * 5) Number of dimensions
   * 6) Weight of input field
   * 7) Height of input field
   * 8) Weight of output field
   * 9) Height of output field
   * */
  case class ConvolutionalSettings(
      fieldSize     : Int,
      stride        : Int,
      zeroPad       : Int,
      filters       : Int,
      dims          : Int,
      inputWeight   : Int,
      inputHeight   : Int,
      outputWeight  : Int,
      outputHeight  : Int)

 */

}