package dividezero

import dotty.tools.dotc.ast.Trees.*
import dotty.tools.dotc.ast.tpd
import dotty.tools.dotc.core.Constants.Constant
import dotty.tools.dotc.core.Contexts.Context
import dotty.tools.dotc.core.Decorators.*
import dotty.tools.dotc.core.StdNames.*
import dotty.tools.dotc.core.Symbols.*
import dotty.tools.dotc.plugins.{PluginPhase, StandardPlugin}
import dotty.tools.dotc.report
import dotty.tools.dotc.transform.{Pickler, Staging}

class DivideZero extends StandardPlugin:
  val name: String = "divideZero"
  override val description: String = "divide zero check"

  override def initialize(options: List[String])(using Context): List[PluginPhase] =
    (new DivideZeroPhase) :: Nil

class DivideZeroPhase extends PluginPhase:
  import tpd.*

  val phaseName = "divideZero"

  override val runsAfter = Set(Pickler.name)
  override val runsBefore = Set(Staging.name)

  override def transformApply(tree: Apply)(implicit ctx: Context): Tree =
    tree match
      case Apply(Select(rcvr, nme.DIV), List(Literal(Constant(0))))
        if rcvr.tpe <:< defn.IntType =>
        report.error("dividing by zero", tree.pos)
      case _ =>
        ()
    tree
end DivideZeroPhase