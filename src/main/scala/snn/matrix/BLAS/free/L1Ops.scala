package snn.matrix.BLAS.free

import cats.free.Free
import freasymonad.free

@free trait L1Ops {

  type L1IO[A] = Free[L1Op, A]

  sealed trait L1Op[A]

  /**  Setup Givens rotation */
  def rotg(a: Double, b: Double, c: Double, s: Double): L1IO[Unit]

  /** Setup modified Givens rotation */
  def rotmg(d1: Double, d2: Double, x1: Double, y1: Double, param: Array[Double]): L1IO[Unit]

  /** Apply Givens rotation */
  def rot(n: Int, x: Array[Double], incx: Int, y: Array[Double], incy: Int, c: Double, s: Double): L1IO[Unit]

  /** Apply modified Givens rotation */
  def rotm(n: Int, x: Array[Double], incx: Int, y: Array[Double], incy: Int, param: Array[Double]): L1IO[Unit]

  /** Swap x and y */
  def swap(n: Int, x: Array[Double], incx: Int, y: Array[Double], incy: Int): L1IO[Unit]

  /**  x = a * x */
  def scale(n: Int, a: Double, x: Array[Double], incx: Int): L1IO[Unit]

  /** Copy x into y */
  def copy(n: Int, x: Array[Double], incx: Int, y: Array[Double], incy: Int): L1IO[Unit]

  /** y = a * x + y */
  def axpy(n: Int, a: Double, x: Array[Double], incx: Int, y: Array[Double], incy: Int): L1IO[Unit]

  /** Dot product */
  def dot(n: Int, x: Array[Double], incx: Int, y: Array[Double], incy: Int): L1IO[Double]

  /** Dot product with extended precision accumulation */
  def sdot(n: Int, sx: Array[Float], incx: Int, sy: Array[Float], incy: Int): L1IO[Float]

  /** Euclidean norm */
  def nrm2(n: Int, x: Array[Double], incx: Int): L1IO[Double]

  /** Sum of absolute values */
  def asum(n: Int, x: Array[Double], incx: Int): L1IO[Double]

  /** Index of max absolute values */
  def iamax(n: Int, x: Array[Double], incx: Int): L1IO[Int]

}

