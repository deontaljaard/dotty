package scala.tasty.reflect

/** Extension methods on scala.quoted.{Expr|Type} to convert to scala.tasty.Tasty objects */
trait QuotedOps extends Core { self: Printers =>

  implicit class QuotedExprAPI[T](expr: scala.quoted.Expr[T]) {
    /** View this expression `quoted.Expr[T]` as a `Term` */
    def unseal given (ctx: Context): Term =
      kernel.QuotedExpr_unseal(expr)

    /** Checked cast to a `quoted.Expr[U]` */
    def cast[U: scala.quoted.Type] given (ctx: Context): scala.quoted.Expr[U] =
      kernel.QuotedExpr_cast[U](expr)
  }

  implicit class QuotedTypeAPI[T <: AnyKind](tpe: scala.quoted.Type[T]) {
    /** View this expression `quoted.Type[T]` as a `TypeTree` */
    def unseal given (ctx: Context): TypeTree =
      kernel.QuotedType_unseal(tpe)
  }

  implicit class TermToQuotedAPI(term: Term) {
    /** Convert `Term` to an `quoted.Expr[Any]` */
    def seal given (ctx: Context): scala.quoted.Expr[Any] =
      kernel.QuotedExpr_seal(term)
  }

  implicit class TypeToQuotedAPI(tpe: Type) {
    /** Convert `Type` to an `quoted.Type[_]` */
    def seal given (ctx: Context): scala.quoted.Type[_] =
      kernel.QuotedType_seal(tpe)
  }
}
