package snn.matrix.BLAS.free

import cats.free.Free
import freasymonad.free

@free trait L2Ops {

  type L2IO[A] = Free[L2Op, A]

  sealed trait L2Op[A]

  /** Matrix vector multiply */
  def gemv(
      trans : String,
      m     : Int,
      n     : Int,
      alpha : Double,
      a     : Array[Double],
      lda   : Int,
      x     : Array[Double],
      incx  : Int,
      beta  : Double,
      y     : Array[Double],
      incy  : Int): L2IO[Unit]

  /** Banded matrix vector multiply */
  def gbmv(
      trans : String,
      m     : Int,
      n     : Int,
      kl    : Int,
      ku    : Int,
      alpha : Double,
      a     : Array[Double],
      lda   : Int,
      x     : Array[Double],
      incx  : Int,
      beta  : Double,
      y     : Array[Double],
      incy  : Int): L2IO[Unit]

  /** Symmetric matrix vector multiply */
  def symv(
      uplo  : String,
      n     : Int,
      alpha : Double,
      a     : Array[Double],
      lda   : Int,
      x     : Array[Double],
      incx  : Int,
      beta  : Double,
      y     : Array[Double],
      incy  : Int): L2IO[Unit]

  /** Symmetric banded matrix vector multiply */
  def sbmv(
      uplo  : String,
      n     : Int,
      k     : Int,
      alpha : Double,
      a     : Array[Double],
      lda   : Int,
      x     : Array[Double],
      incx  : Int,
      beta  : Double,
      y     : Array[Double],
      incy  : Int): L2IO[Unit]

  /** Symmetric packed matrix vector multiply */
  def spmv(
      uplo  : String,
      n     : Int,
      alpha : Double,
      ap    : Array[Double],
      x     : Array[Double],
      incx  : Int,
      beta  : Double,
      y     : Array[Double],
      incy  : Int): L2IO[Unit]

  /** Triangular matrix vector multiply */
  def trmv(
      uplo  : String,
      trans : String,
      diag  : String,
      n     : Int,
      a     : Array[Double],
      lda   : Int,
      x     : Array[Double],
      incx  : Int): L2IO[Unit]

  /** Triangular banded matrix vector multiply */
  def tbmv(
      uplo  : String,
      trans : String,
      diag  : String,
      n     : Int,
      k     : Int,
      a     : Array[Double],
      lda   : Int,
      x     : Array[Double],
      incx  : Int): L2IO[Unit]

  /** Triangular packed matrix vector multiply */
  def tpmv(
      uplo  : String,
      trans : String,
      diag  : String,
      n     : Int,
      ap    : Array[Double],
      x     : Array[Double],
      incx  : Int): L2IO[Unit]

  /** Solving triangular matrix problems */
  def trsv(
      uplo  : String,
      trans : String,
      diag  : String,
      n     : Int,
      a     : Array[Double],
      lda   : Int,
      x     : Array[Double],
      incx  : Int): L2IO[Unit]

  /** Solving triangular banded matrix problems */
  def tbsv(
      uplo  : String,
      trans : String,
      diag  : String,
      n     : Int,
      k     : Int,
      a     : Array[Double],
      lda   : Int,
      x     : Array[Double],
      incx  : Int): L2IO[Unit]

  /** Solving triangular packed matrix problems */
  def tpsv(
      uplo  : String,
      trans : String,
      diag  : String,
      n     : Int,
      ap    : Array[Double],
      x     : Array[Double],
      incx  : Int): L2IO[Unit]

  /** Performs the rank 1 operation: A := alpha * x * y' + A */
  def ger(
      m     : Int,
      n     : Int,
      alpha : Double,
      x     : Array[Double],
      incx  : Int,
      y     : Array[Double],
      incy  : Int,
      a     : Array[Double],
      lda   : Int): L2IO[Unit]

  /** Performs the symmetric rank 1 operation A := alpha * x * x' + A */
  def syr(
      uplo  : String,
      n     : Int,
      alpha : Double,
      x     : Array[Double],
      incx  : Int,
      a     : Array[Double],
      lda   : Int): L2IO[Unit]

  /** Symmetric packed rank 1 operation A := alpha*x*x' + A */
  def spr(
      uplo  : String,
      n     : Int,
      alpha : Double,
      x     : Array[Double],
      incx  : Int,
      ap    : Array[Double]): L2IO[Unit]

  /** Performs the symmetric rank 2 operation, A := alpha*x*y' + alpha*y*x' + A */
  def syr2(
      uplo  : String,
      n     : Int,
      alpha : Double,
      x     : Array[Double],
      incx  : Int,
      y     : Array[Double],
      incy  : Int,
      a     : Array[Double],
      lda   : Int): L2IO[Unit]

  /** Performs the symmetric packed rank 2 operation, A := alpha*x*y' + alpha*y*x' + A */
  def spr2(
      uplo  : String,
      n     : Int,
      alpha : Double,
      x     : Array[Double],
      incx  : Int,
      y     : Array[Double],
      incy  : Int,
      ap    : Array[Double]): L2IO[Unit]

}