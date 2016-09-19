package snn

import cats.data._

trait Tensor {
  import Tensor._

  def data: Array[Float]
  def shape: Shape

  def num: Int = shape.samples
  def channels: Int = shape.channels
  def height: Int = shape.height
  def width: Int = shape.width

  def reshape(s: Int, d: Int, h: Int, w: Int)
  def permute(dim1: Int, dim2: Int, dim3: Int, dim4: Int)
  def offset(s: Int, d: Int = 0, h: Int = 0, w: Int = 0): TensorError Xor Int = {
    if (channels < 0 || s >= num || d < 0 || d >= channels || h < 0 || h >= height || w < 0 || w >= width) {
      Xor.Left(OffsetError(s"Index: ($s, $d, $h, $w), Bound: [$num, $channels, $height, $width]."))
    } else Xor.Right(((s * channels + d) * height + h) * width + w)
  }

}

object Tensor {

  def apply(samples: Int, channels: Int, height: Int, width: Int): Tensor = ???
  def apply(source: Tensor): Tensor = ???


  case class Shape(samples: Int, channels: Int, height: Int, width: Int)

  sealed trait TensorError { def message: String }
  case class OffsetError(message: String) extends TensorError

}