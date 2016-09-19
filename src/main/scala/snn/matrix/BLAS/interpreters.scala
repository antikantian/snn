package snn
package matrix
package BLAS

import cats.data._
import com.github.fommil.netlib.{ BLAS => NetlibBLAS }

object interpreters {

  import snn.matrix.BLAS.free.{ L1Ops, L2Ops }

  type BLASReader[A] = Reader[NetlibBLAS, A]

  val l1Interpreter = new L1Ops.Interp[BLASReader[?]] {
    def rotg(a: Double, b: Double, c: Double, s: Double): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.drotg(a.toDoubleW, b.toDoubleW, c.toDoubleW, s.toDoubleW))
    
    def rotmg(d1: Double, d2: Double, x1: Double, y1: Double, param: Array[Double]): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.drotmg(d1.toDoubleW, d2.toDoubleW, x1.toDoubleW, y1, param))
    
    def rot(n: Int, x: Array[Double], incx: Int, y: Array[Double], incy: Int, c: Double, s: Double): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.drot(n, x, incx, y, incy, c, s))
    
    def rotm(n: Int, x: Array[Double], incx: Int, y: Array[Double], incy: Int, param: Array[Double]): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.drotm(n, x, incx, y, incy, param))

    def swap(n: Int, x: Array[Double], incx: Int, y: Array[Double], incy: Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dswap(n, x, incx, y, incy))

    def scale(n: Int, a: Double, x: Array[Double], incx: Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dscal(n, a, x, incx))
    
    def copy(n: Int, x: Array[Double], incx: Int, y: Array[Double], incy: Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dcopy(n, x, incx, y, incy))
    
    def axpy(n: Int, a: Double, x: Array[Double], incx: Int, y: Array[Double], incy: Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.daxpy(n, a, x, incx, y, incy))
    
    def dot(n: Int, x: Array[Double], incx: Int, y: Array[Double], incy: Int): BLASReader[Double] =
      Reader[NetlibBLAS, Double](_.ddot(n, x, incx, y, incy))
    
    def sdot(n: Int, sx: Array[Float], incx: Int, sy: Array[Float], incy: Int): BLASReader[Float] =
      Reader[NetlibBLAS, Float](_.sdot(n, sx, incx, sy, incy))
    
    def nrm2(n: Int, x: Array[Double], incx: Int): BLASReader[Double] =
      Reader[NetlibBLAS, Double](_.dnrm2(n, x, incx))
    
    def asum(n: Int, x: Array[Double], incx: Int): BLASReader[Double] =
      Reader[NetlibBLAS, Double](_.dasum(n, x, incx))
    
    def iamax(n: Int, x: Array[Double], incx: Int): BLASReader[Int] =
      Reader[NetlibBLAS, Int](_.idamax(n, x, incx))
  }

  val l2Interpreter = new L2Ops.Interp[BLASReader[?]] {
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
        incy  : Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dgemv(trans, m, n, alpha, a, lda, x, incx, beta, y, incy))

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
        incy  : Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dgbmv(trans, m, n, kl, ku, alpha, a, lda, x, incx, beta, y, incy))

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
        incy  : Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dsymv(uplo, n, alpha, a, lda, x, incx, beta, y, incy))

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
        incy  : Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dsbmv(uplo, n, k, alpha, a, lda, x, incx, beta, y, incy))

    def spmv(
        uplo  : String,
        n     : Int,
        alpha : Double,
        ap    : Array[Double],
        x     : Array[Double],
        incx  : Int,
        beta  : Double,
        y     : Array[Double],
        incy  : Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dspmv(uplo, n, alpha, ap, x, incx, beta, y, incy))

    def trmv(
        uplo  : String,
        trans : String,
        diag  : String,
        n     : Int,
        a     : Array[Double],
        lda   : Int,
        x     : Array[Double],
        incx  : Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dtrmv(uplo, trans, diag, n, a, lda, x, incx))

    def tbmv(
        uplo  : String,
        trans : String,
        diag  : String,
        n     : Int,
        k     : Int,
        a     : Array[Double],
        lda   : Int,
        x     : Array[Double],
        incx  : Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dtbmv(uplo, trans, diag, n, k, a, lda, x, incx))

    def tpmv(
        uplo  : String,
        trans : String,
        diag  : String,
        n     : Int,
        ap    : Array[Double],
        x     : Array[Double],
        incx  : Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dtpmv(uplo, trans, diag, n, ap, x, incx))

    def trsv(
        uplo  : String,
        trans : String,
        diag  : String,
        n     : Int,
        a     : Array[Double],
        lda   : Int,
        x     : Array[Double],
        incx  : Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dtrsv(uplo, trans, diag, n, a, lda, x, incx))

    def tbsv(
        uplo  : String,
        trans : String,
        diag  : String,
        n     : Int,
        k     : Int,
        a     : Array[Double],
        lda   : Int,
        x     : Array[Double],
        incx  : Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dtbsv(uplo, trans, diag, n, k, a, lda, x, incx))

    def tpsv(
        uplo  : String,
        trans : String,
        diag  : String,
        n     : Int,
        ap    : Array[Double],
        x     : Array[Double],
        incx  : Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dtpsv(uplo, trans, diag, n, ap, x, incx))

    def ger(
        m     : Int,
        n     : Int,
        alpha : Double,
        x     : Array[Double],
        incx  : Int,
        y     : Array[Double],
        incy  : Int,
        a     : Array[Double],
        lda   : Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dger(m, n, alpha, x, incx, y, incy, a, lda))

    def syr(
        uplo  : String,
        n     : Int,
        alpha : Double,
        x     : Array[Double],
        incx  : Int,
        a     : Array[Double],
        lda   : Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dsyr(uplo, n, alpha, x, incx, a, lda))

    def spr(
        uplo  : String,
        n     : Int,
        alpha : Double,
        x     : Array[Double],
        incx  : Int,
        ap    : Array[Double]): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dspr(uplo, n, alpha, x, incx, ap))

    def syr2(
        uplo  : String,
        n     : Int,
        alpha : Double,
        x     : Array[Double],
        incx  : Int,
        y     : Array[Double],
        incy  : Int,
        a     : Array[Double],
        lda   : Int): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dsyr2(uplo, n, alpha, x, incx, y, incy, a, lda))

    def spr2(
        uplo  : String,
        n     : Int,
        alpha : Double,
        x     : Array[Double],
        incx  : Int,
        y     : Array[Double],
        incy  : Int,
        ap    : Array[Double]): BLASReader[Unit] =
      Reader[NetlibBLAS, Unit](_.dspr2(uplo, n, alpha, x, incx, y, incy, ap))
  }
  
}