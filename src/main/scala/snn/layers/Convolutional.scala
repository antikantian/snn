package snn
package layers

import fs2._

case class Convolutional[A](filters: Int, rows: Int, cols: Int, strideH: Int, strideW: Int) extends Layer[A] {

  val paddingH: Int =
    if (strideH != 1) 0 else rows / 2

  val paddingW: Int =
    if (strideW != 1) 0 else cols / 2

  def forward(input: Tensor, weights: Tensor, output: Tensor) = {
    // [o]utput[x]: [h]eight, [w]idth, [s]ize
    val oh: Int = 1 + (input.height + 2 * paddingH - weights.height) / strideH
    val ow: Int = 1 + (input.width + 2 * paddingW - weights.width) / strideW
    val o
    val oc: Int = input.channels * weights.height * weights.width



    // [S]ource[X]: [n]umber, [c]hannels, [h]eight, [w]idth, [o]ffset
    val sn: Int = input.num
    val sc: Int = input.channels
    val sh: Int = input.height
    val sw: Int = input.width
    val so: Int = sc * sh * sw

    // [K]ernel[X]: [h]eight, [w]idth, [s]ize
    val kh: Int = weight.height
    val kw: Int = weight.height
    val ks: Int = sc * kh * kw

    // [D]estination[X]: [c]hannels, [h]eight, [w]idth, [s]ize
    val dc: Int = weight.num
    val dh: Int = (sh - kh) / strideH + 1
    val dw: Int = (sw - kw) / strideW + 1
    val ds: Int = dh * dw

    // [E]nd[X]: [h]eight, [w]idth
    val eh: Int = sh - kh + 1
    val ew: Int = sw - kw + 1

    // TODO: Change both of these parameter types to spire types
    // TODO: Possibly switch data structure to one of non's deboxed structs
    val dHead: Array[Float] = new Array[Float](sn * ds * dc)
    val mHead: Array[Float] = new Array[Float](ds * ks)

    for {
      sn0 <- Stream.range(0, sn, 1)
      dat = new Array[Float](sn * ds * dc)
      sh0 <- Stream.range(0, eh, strideH)
      sw0 <- Stream.range(0, ew, strideW)
      sc0 <- Stream.range(0, sc, 1)
      so0 = (sn0 * sh + sh0) * sw + sw0
      hid <- Stream.range(0, kh, 1)
    } yield {

    }

    // Convolve
    Stream.range(0, sn, 1) map { sn0 =>
      Stream.range(0, eh, strideH) map { sh0 =>
        Stream.range(0, ew, strideW) map { sw0 =>
          Stream.range(0, sc, 1) map { sc0 =>
            val soff = (sc0 * sh + sh0) * sw + sw0
            Stream.range(0, kh, 1) map { hidx =>
              // Do stuff here
            }
          }
        }
      }
    }





    def strideHeight: Int
    def strideWidth: Int
    def sourceSamples: Int = input.samples
    def sourceDims: Int = input.dims
    def sourceHeight: Int = input.height
    def sourceWidth: Int = input.width
    def destDims: Int = weight.samples
    def kernelHeight: Int = weight.height
    def kernelWidth: Int = weight.width
    def destHeight: Int = (sourceHeight - kernelHeight) / strideHeight + 1
    def destWidth: Int = (sourceWidth - kernelWidth) / strideWidth + 1
    def endHeight: Int = sourceHeight - kernelHeight + 1
    def endWidth: Int = sourceWidth - kernelWidth + 1
    def destSize: Int = destHeight * destWidth
    def kernelSize: Int = sourceDims * kernelHeight * kernelWidth

    def sourceOffset: Int = sourceDims * sourceHeight * sourceWidth
    def destHead: Array[Float] = new Array[Float](sourceSamples * destSize * destDims) // TODO: Change type param to spire
    def matHead: Array[Float] = new Array[Float](destSize * kernelSize) // TODO: Type param to spire
  }



  def strideHeight: Int
  def strideWidth: Int
  def input: Tensor
  def weight: Tensor
  def output: Tensor
  def sourceSamples: Int = input.samples
  def sourceDims: Int = input.dims
  def sourceHeight: Int = input.height
  def sourceWidth: Int = input.width
  def destDims: Int = weight.samples
  def kernelHeight: Int = weight.height
  def kernelWidth: Int = weight.width
  def destHeight: Int = (sourceHeight - kernelHeight) / strideHeight + 1
  def destWidth: Int = (sourceWidth - kernelWidth) / strideWidth + 1
  def endHeight: Int = sourceHeight - kernelHeight + 1
  def endWidth: Int = sourceWidth - kernelWidth + 1
  def destSize: Int = destHeight * destWidth
  def kernelSize: Int = sourceDims * kernelHeight * kernelWidth

  def sourceOffset: Int = sourceDims * sourceHeight * sourceWidth
  def destHead: Array[Float] = new Array[Float](sourceSamples * destSize * destDims) // TODO: Change type param to spire
  def matHead: Array[Float] = new Array[Float](destSize * kernelSize) // TODO: Type param to spire

}