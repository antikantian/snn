package snn.datasets.util

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

/** MNIST Idx file format uses octals for some information.
 *  This is just a small macro to deal with octals in a
 *  literate way, since octal number's were removed in Scala 2.11.
 *
 *  This implementation is courtesy of Travis Brown, and amended by jopasserat:
 *  http://stackoverflow.com/questions/16590236/scala-2-10-octal-escape-is-deprecated-how-to-do-octal-idiomatically-now
 *  */
private[datasets] object OctalLiterals {

  implicit class OctallerContext(sc: StringContext) {
    def o(): Int = macro oImpl
  }

  def oImpl(c: blackbox.Context)(): c.Expr[Int] = {
    import c.universe._

    c.Expr(q"""${
      c.prefix.tree match {
        case Apply(_, Apply(_, Literal(Constant(oct: String)) :: Nil) :: Nil) =>
          Integer.decode("0" + oct).toInt
        case _ => c.abort(c.enclosingPosition, "Invalid octal literal.")
      }
    }""")
  }
}