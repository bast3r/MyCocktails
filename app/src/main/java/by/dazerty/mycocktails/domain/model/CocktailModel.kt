package by.dazerty.mycocktails.domain.model

import java.util.UUID

data class CocktailModel(

    var id: UUID = UUID.randomUUID(),
    var title: String,
    var imageId: String? = null,
    var description: String? = null,
    var recipe: String? = null,
    var ingredients: ArrayList<IngredientModel> = ArrayList()
)