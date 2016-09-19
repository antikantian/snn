package snn.matrix.BLAS.naive

import scala.collection.mutable

import snn.matrix.BLAS.Level1
import snn.matrix.{PlaneRotation, VectorLike}
import spire.syntax.cfor._

/** Reference implementation using textbook loops */
trait NaiveLevel1 extends Level1 {
  def scale(alpha: Double, x: mutable.IndexedSeq[Double]): Unit =
    cforRange(0 until x.length) { k => x(k) *= alpha }

  def copy(x: mutable.IndexedSeq[Double], y: mutable.IndexedSeq[Double]): Unit =
    cforRange(0 until x.length) { k => y(k) = x(k) }

  def axpy(alpha: Double,
           x: mutable.IndexedSeq[Double], y: mutable.IndexedSeq[Double]): Unit =
    cforRange(0 until x.length) { k => y(k) += alpha * x(k) }

  def rot(x:VectorLike, y:VectorLike, g:PlaneRotation) {
    cforRange(0 until x.length) { i =>
      val xi = g.cs*x(i) + g.sn*y(i)
      y(i) = g.cs*y(i) - g.sn*x(i)
      x(i) = xi
    }
  }
}