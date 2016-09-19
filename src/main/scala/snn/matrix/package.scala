package snn

package object matrix {

  sealed trait MatrixOrder
  case object RowMajor extends MatrixOrder
  case object ColumnMajor extends MatrixOrder

}