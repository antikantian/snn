package snn.util

object invariant {

  case class LayerError(msg: String) extends Exception
  case class MatrixError(msg: String) extends Exception
  case class NetworkError(msg: String) extends Exception

}