package snn

import fs2._

import snn.matrix._
import snn.util.invariant._

trait Network {
  def checkedWeights: Vector[Matrix[Double]]
  def layers: Vector[Layer]
  def learningRate: Double
}

object Network {

  def checkDimensions(w1: Matrix[Double], w2: Matrix[Double]): Matrix[Double] =
    if (w1.rows == w2.cols) w2
    else throw NetworkError("Inconsistent dimensions in weight matrix")

  def buildNetwork(weights: Vector[Matrix[Double]], lr: Double, act: Activation): Network = {
    def buildLayer(w: Matrix[Double]): Layer =
      new Layer {
        val weights: Matrix[Double] = w
        val activation: Activation = act
      }

    new Network {
      val checkedWeights: Vector[Matrix[Double]] = Stream.emits(weights).scan1(checkDimensions).toVector
      val layers: Vector[Layer] = checkedWeights.map(buildLayer)
      val learningRate: Double = lr
    }
  }

  def propagate(layerJ: PropagatedLayer, layerK: Layer): PropagatedLayer = {
    new PropagatedLayer {
      override def pDfDa: ColumnVector[Double] = ???

      override def pActivation: Activation = layerK.activation

      override def pWeights: ColumnVector[Double] = ???

      override def pInput: ColumnVector[Double] = layerJ.pOutput

      override def pOutput: ColumnVector[Double] = ???
    }
  }

  def propagateNet(input: ColumnVector[Double], net: Network): List[PropagatedLayer] = {

    val layer0: PropagatedInputLayer =
      new PropagatedInputLayer { val pOutput: ColumnVector[Double] = validatedInputs }

    def calcs = Stream.emits(net.layers).scan(layer0)(propagate)
  }

  def errorGrad(dazzle: ColumnVector[Double], fDa: ColumnVector[Double], input: ColumnVector[Double]): ColumnVector[Double] =
    dazzle.hadamardProduct(fDa) <> input.transpose


  def backpropagate(layerJ: PropagatedLayer, layerK: BackpropLayer): BackpropLayer = {
    val fDaK = layerK.bpDfDa
    val fDaJ = layerJ.pDfDa
    val dazzleK = layerK.bpDazzle
    val wKT = layerK.bpW.transpose
    val dazzleJ = wKT <> dazzleK.hadamardProduct(fDaK)

    new BackpropLayer {
      val bpDazzle = dazzleJ
      val bpErrGrad = errorGrad(dazzleJ, fDaJ, layerJ.pInput)
      val bpDfDa = layerJ.pDfDa
      val bpIn = layerJ.pInput
      val bpOut = layerJ.pOutput
      val bpW = layerJ.pWeights
      val bpA = layerJ.pActivation
    }
  }

  def backpropagateFinalLayer(l: PropagatedLayer, t: ColumnVector[Double]): BackpropLayer = {
    val dazzle = l.pOutput - t
    val fDa = l.pDfDa

    new BackpropLayer {
      val bpDazzle = dazzle
      val bpErrGrad = errorGrad(dazzle, fDa, l.pInput)
      val bpDfDa = l.pDfDa
      val bpIn = l.pInput
      val bpOut = l.pOutput
      val bpW = l.pWeights
      val bpA = l.pActivation
    }
  }

  def backpropagateNet(target: ColumnVector[Double], layers: Vector[PropagatedLayer]): Vector[BackpropLayer] = {
    val hiddenLayers = layers.init
    val layerL = backpropagateFinalLayer(layers.last, target)

    hiddenLayers.scanRight(layerL)(backpropagate)
  }

  def update(rate: Double, layer: BackpropLayer): Layer = {
    val wOld = layer.bpW
    val delW = layer.bpErrGrad.scale(rate)
    val wNew = wOld - delW

    new Layer {
      val weights = wNew
      val activation = layer.bpA
    }
  }


}