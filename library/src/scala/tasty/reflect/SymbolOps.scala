package scala.tasty
package reflect

/** Tasty reflect symbol */
trait SymbolOps extends Core {

  // Symbol

  implicit class SymbolAPI(self: Symbol) {

    /** Owner of this symbol. The owner is the symbol in which this symbol is defined */
    def owner given (ctx: Context): Symbol = kernel.Symbol_owner(self)

    /** Flags of this symbol */
    def flags given (ctx: Context): Flags = kernel.Symbol_flags(self)

    /** This symbol is private within the resulting type */
    def privateWithin given (ctx: Context): Option[Type] = kernel.Symbol_privateWithin(self)

    /** This symbol is protected within the resulting type */
    def protectedWithin given (ctx: Context): Option[Type] = kernel.Symbol_protectedWithin(self)

    /** The name of this symbol */
    def name given (ctx: Context): String = kernel.Symbol_name(self)

    /** The full name of this symbol up to the root package */
    def fullName given (ctx: Context): String = kernel.Symbol_fullName(self)

    /** The position of this symbol */
    def pos given (ctx: Context): Position = kernel.Symbol_pos(self)

    def localContext given (ctx: Context): Context = kernel.Symbol_localContext(self)

    /** The comment for this symbol, if any */
    def comment given (ctx: Context): Option[Comment] = kernel.Symbol_comment(self)

    /** Unsafe cast as to PackageSymbol. Use IsPackageSymbol to safely check and cast to PackageSymbol */
    def asPackageDef given (ctx: Context): PackageDefSymbol = self match {
      case IsPackageDefSymbol(self) => self
      case _ => throw new Exception("not a PackageDefSymbol")
    }

    /** Unsafe cast as to ClassSymbol. Use IsClassDefSymbol to safely check and cast to ClassSymbol */
    def asClassDef given (ctx: Context): ClassDefSymbol = self match {
      case IsClassDefSymbol(self) => self
      case _ => throw new Exception("not a ClassDefSymbol")
    }

    /** Unsafe cast as to DefSymbol. Use IsDefDefSymbol to safely check and cast to DefSymbol */
    def asDefDef given (ctx: Context): DefDefSymbol = self match {
      case IsDefDefSymbol(self) => self
      case _ => throw new Exception("not a DefDefSymbol")
    }

    /** Unsafe cast as to ValSymbol. Use IsValDefSymbol to safely check and cast to ValSymbol */
    def asValDef given (ctx: Context): ValDefSymbol = self match {
      case IsValDefSymbol(self) => self
      case _ => throw new Exception("not a ValDefSymbol")
    }

    /** Unsafe cast as to TypeSymbol. Use IsTypeDefSymbol to safely check and cast to TypeSymbol */
    def asTypeDef given (ctx: Context): TypeDefSymbol = self match {
      case IsTypeDefSymbol(self) => self
      case _ => throw new Exception("not a TypeDefSymbol")
    }

    /** Unsafe cast as to BindSymbol. Use IsBindSymbol to safely check and cast to BindSymbol */
    def asBindDef given (ctx: Context): BindSymbol = self match {
      case IsBindSymbol(self) => self
      case _ => throw new Exception("not a BindSymbol")
    }

    /** Annotations attached to this symbol */
    def annots given (ctx: Context): List[Term] = kernel.Symbol_annots(self)

    def isDefinedInCurrentRun given (ctx: Context): Boolean = kernel.Symbol_isDefinedInCurrentRun(self)

    def isLocalDummy given (ctx: Context): Boolean = kernel.Symbol_isLocalDummy(self)
    def isRefinementClass given (ctx: Context): Boolean = kernel.Symbol_isRefinementClass(self)
    def isAliasType given (ctx: Context): Boolean = kernel.Symbol_isAliasType(self)
    def isAnonymousClass given (ctx: Context): Boolean = kernel.Symbol_isAnonymousClass(self)
    def isAnonymousFunction given (ctx: Context): Boolean = kernel.Symbol_isAnonymousFunction(self)
    def isAbstractType given (ctx: Context): Boolean = kernel.Symbol_isAbstractType(self)
    def isClassConstructor given (ctx: Context): Boolean = kernel.Symbol_isClassConstructor(self)

    def isType given (ctx: Context): Boolean = kernel.matchTypeSymbol(self).isDefined
    def isTerm given (ctx: Context): Boolean = kernel.matchTermSymbol(self).isDefined
  }

  // PackageSymbol

  object IsPackageDefSymbol {
    def unapply(symbol: Symbol) given (ctx: Context): Option[PackageDefSymbol] =
      kernel.matchPackageDefSymbol(symbol)
  }

  implicit class PackageDefSymbolAPI(self: PackageDefSymbol) {
    def tree given (ctx: Context): PackageDef =
      kernel.PackageDefSymbol_tree(self)
  }

  // TypeSymbol

  object IsTypeSymbol {
    def unapply(symbol: Symbol) given (ctx: Context): Option[TypeSymbol] =
      kernel.matchTypeSymbol(symbol)
  }

  // ClassSymbol

  object IsClassDefSymbol {
    def unapply(symbol: Symbol) given (ctx: Context): Option[ClassDefSymbol] =
      kernel.matchClassDefSymbol(symbol)
  }

  object ClassDefSymbol {
    /** The ClassSymbol of a global class definition */
    def of(fullName: String) given (ctx: Context): ClassDefSymbol =
      kernel.ClassDefSymbol_of(fullName)
  }

  implicit class ClassDefSymbolAPI(self: ClassDefSymbol) {
    /** ClassDef tree of this defintion */
    def tree given (ctx: Context): ClassDef =
      kernel.ClassDefSymbol_tree(self)

    /** Fields directly declared in the class */
    def fields given (ctx: Context): List[Symbol] =
      kernel.ClassDefSymbol_fields(self)

    /** Field with the given name directly declared in the class */
    def field(name: String) given (ctx: Context): Option[Symbol] =
      kernel.ClassDefSymbol_field(self)(name)

    /** Get non-private named methods defined directly inside the class */
    def classMethod(name: String) given (ctx: Context): List[DefDefSymbol] =
      kernel.ClassDefSymbol_classMethod(self)(name)

    /** Get all non-private methods defined directly inside the class, exluding constructors */
    def classMethods given (ctx: Context): List[DefDefSymbol] =
      kernel.ClassDefSymbol_classMethods(self)

    /** Get named non-private methods declared or inherited */
    def method(name: String) given (ctx: Context): List[DefDefSymbol] =
      kernel.ClassDefSymbol_method(self)(name)

    /** Get all non-private methods declared or inherited */
    def methods given (ctx: Context): List[DefDefSymbol] =
      kernel.ClassDefSymbol_methods(self)

    /** Fields of a case class type -- only the ones declared in primary constructor */
    def caseFields given (ctx: Context): List[ValDefSymbol] =
      kernel.ClassDefSymbol_caseFields(self)

    /** The class symbol of the companion module class */
    def companionClass given (ctx: Context): Option[ClassDefSymbol] =
      kernel.ClassDefSymbol_companionClass(self)

    /** The symbol of the companion module */
    def companionModule given (ctx: Context): Option[ValDefSymbol] =
      kernel.ClassDefSymbol_companionModule(self)

    /** The symbol of the class of the companion module */
    def moduleClass given (ctx: Context): Option[Symbol] =
      kernel.ClassDefSymbol_moduleClass(self)
  }

  // TypeSymbol

  object IsTypeDefSymbol {
    def unapply(symbol: Symbol) given (ctx: Context): Option[TypeDefSymbol] =
      kernel.matchTypeDefSymbol(symbol)
  }

  implicit class TypeDefSymbolAPI(self: TypeDefSymbol) {
    /** TypeDef tree of this definition */
    def tree given (ctx: Context): TypeDef =
      kernel.TypeDefSymbol_tree(self)

    def isTypeParam given (ctx: Context): Boolean =
      kernel.TypeDefSymbol_isTypeParam(self)
  }

  // TypeBindSymbol

  object IsTypeBindSymbol {
    def unapply(symbol: Symbol) given (ctx: Context): Option[TypeBindSymbol] =
      kernel.matchTypeBindSymbol(symbol)
  }

  implicit class TypeBindSymbolAPI(self: TypeBindSymbol) {
    /** TypeBind pattern of this definition */
    def tree given (ctx: Context): TypeBind =
      kernel.TypeBindSymbol_tree(self)
  }

  // TermSymbol

  object IsTermSymbol {
    def unapply(symbol: Symbol) given (ctx: Context): Option[TermSymbol] =
      kernel.matchTermSymbol(symbol)
  }

  // DefSymbol

  object IsDefDefSymbol {
    def unapply(symbol: Symbol) given (ctx: Context): Option[DefDefSymbol] =
      kernel.matchDefDefSymbol(symbol)
  }

  implicit class DefDefSymbolAPI(self: DefDefSymbol) {
    /** DefDef tree of this defintion */
    def tree given (ctx: Context): DefDef =
      kernel.DefDefSymbol_tree(self)

    /** Signature of this defintion */
    def signature given (ctx: Context): Signature =
      kernel.DefDefSymbol_signature(self)
  }

  // ValSymbol

  object IsValDefSymbol {
    def unapply(symbol: Symbol) given (ctx: Context): Option[ValDefSymbol] =
      kernel.matchValDefSymbol(symbol)
  }

  implicit class ValDefSymbolAPI(self: ValDefSymbol) {
    /** ValDef tree of this defintion */
    def tree given (ctx: Context): ValDef =
      kernel.ValDefSymbol_tree(self)

    /** The class symbol of the companion module class */
    def moduleClass given (ctx: Context): Option[ClassDefSymbol] =
      kernel.ValDefSymbol_moduleClass(self)

    def companionClass given (ctx: Context): Option[ClassDefSymbol] =
      kernel.ValDefSymbol_companionClass(self)
  }

  // BindSymbol

  object IsBindSymbol {
    def unapply(symbol: Symbol) given (ctx: Context): Option[BindSymbol] =
      kernel.matchBindSymbol(symbol)
  }

  implicit class BindSymbolAPI(self: BindSymbol) {
    /** Bind pattern of this definition */
    def tree given (ctx: Context): Bind =
      kernel.BindSymbol_tree(self)
  }

  // NoSymbol

  object NoSymbol {
    def unapply(symbol: Symbol) given (ctx: Context): Boolean =
      kernel.matchNoSymbol(symbol)
  }
}
