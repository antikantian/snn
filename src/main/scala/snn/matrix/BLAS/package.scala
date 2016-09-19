package snn.matrix

import org.netlib.util.doubleW

package object BLAS {

  import scala.collection.mutable
  import snn.matrix.{Transposition, UpperOrLower}
  import snn.matrix.{VectorLike, MatrixLike, PlaneRotation}

  implicit class WrappedDouble(x: Double) {
    def toDoubleW: doubleW = new doubleW(x)
  }

  /**
   * Operations referencing O(n) elements and performing O(n) flops
   *   - vector scaling
   *   - linear combination of two vectors
   *   - vector dot product and norm
   *   - plane rotations
   */
  trait Level1 {
    /**
     * Scale a vector or a matrix
     *
     * i.e. x := α x
     */
    def scale(alpha: Double, x: mutable.IndexedSeq[Double]): Unit

    /**
     * Copy x into y
     *
     * i.e. y(0:n) := x(0:n) where n is the length of x
     */
    def copy(x: mutable.IndexedSeq[Double], y: mutable.IndexedSeq[Double]): Unit

    /**
     * Linear combination
     *
     * y := α x + y
     */
    def axpy(alpha: Double, x: mutable.IndexedSeq[Double], y: mutable.IndexedSeq[Double]): Unit

    /**
     * Transform each (x(i), y(i)) into its image by the given plane rotation
     *
     * This may be interpreted as one of the following matrix product, performed
     * in place:
     * <pre>
     *         [ x(0) y(0) ] G^T
     *         [ x(1) y(1) ]
     *         [ ......... ]
     * </pre>
     * or
     * <pre>
     *         G [ x(0) y(0) .... ]
     *           [ x(1) y(1) .... ]
     * </pre>
     * where G is this rotation and G^T^ its transpose (which is also its
     * inverse).
     */
    def rot(x:VectorLike, y:VectorLike, g:PlaneRotation): Unit
  }

  /**
   * Operations referencing O(n^2^) elements and performing O(n^2^) flops
   *   - product of a matrix and a vector
   *   - rank-1 updates
   */
  trait Level2 {

    /**
     * Product of a general matrix and a vector
     *
     * y := α op(A) x + β y where op(A) = A or A^T^
     *
     * @param trans decides which alternative for op(A) shall be used.
     */
    def gemv(trans: Transposition.Value,
             alpha: Double, a: MatrixLike,
             x: VectorLike, beta: Double, y: VectorLike): Unit

    /**
     * Rank-1 update for a general matrix
     *
     * A := α x y^T^ + A
     */
    def ger(alpha: Double, x: VectorLike, y: VectorLike, a: MatrixLike): Unit
  }

  /**
   * Operations referencing O(n^2^) elements and performing O(n^3^) flops
   *   - multiplication of a pair of matrices, supporting different type for
   *     each of them (general, symmetric, triangular, etc)
   *   - solution of triangularised system of equations for many right-hand
   *     sides
   *
   * Level 3 operations are the critical ones for performance.
   */
  trait Level3 {
    /**
     * Product of two general matrices
     *
     * C := α op(A) op(B) + β C where op(X) = X or X^T^
     *
     * @param transA decides which alternative for op(A) shall be used.
     * @param transB decides which alternative for op(B) shall be used.
     *
     * The matrix C is thus modified in-place whereas matrices A and B are
     * not modified. Result is unpredictable if C overlaps with any of A or B.
     *
     * @todo We could check whether there are such overlaps.
     */
    def gemm(transA:Transposition.Value, transB:Transposition.Value,
             alpha:Double, a:MatrixLike, b:MatrixLike,
             beta:Double, c:MatrixLike): Unit

    /**
     * Symmetric rank-k updates
     *
     * Either
     *
     *     C := α A A^T + β C (1)
     *
     * or
     *
     *     C := α A^T A + β C (2)
     *
     * where either the upper or lower triangle is updated while the other
     * triangle is not touched.
     *
     * @param trans When its value is Transpose, then (1) is
     * performed, whereas when it is NoTranspose, then (2) is performed.
     *
     * @param uplo When it is Upper (resp. Lower), then only the upper
     * (resp. lower) triangle of C is updated.
     */
    def syrk(uplo:UpperOrLower.Value, trans:Transposition.Value,
             alpha:Double, a:MatrixLike, beta:Double, c:MatrixLike): Unit
  }


}