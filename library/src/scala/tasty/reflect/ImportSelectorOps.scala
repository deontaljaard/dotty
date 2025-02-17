package scala.tasty
package reflect

trait ImportSelectorOps extends Core {

  implicit class SimpleSelectorAPI(self: SimpleSelector) {
    def selection given (ctx: Context): Id =
      kernel.SimpleSelector_selection(self)
  }

  object SimpleSelector {
    def unapply(importSelector: ImportSelector) given (ctx: Context): Option[Id] =
      kernel.matchSimpleSelector(importSelector).map(_.selection)
  }

  implicit class RenameSelectorAPI(self: RenameSelector) {
    def from given (ctx: Context): Id =
      kernel.RenameSelector_from(self)

    def to given (ctx: Context): Id =
      kernel.RenameSelector_to(self)
  }

  object RenameSelector {
    def unapply(importSelector: ImportSelector) given (ctx: Context): Option[(Id, Id)] =
      kernel.matchRenameSelector(importSelector).map(x => (x.from, x.to))
  }

  implicit class OmitSelectorAPI(self: OmitSelector) {
    def omitted given (ctx: Context): Id =
      kernel.SimpleSelector_omited(self)
  }

  object OmitSelector {
    def unapply(importSelector: ImportSelector) given (ctx: Context): Option[Id] =
      kernel.matchOmitSelector(importSelector).map(_.omitted)
  }

}
