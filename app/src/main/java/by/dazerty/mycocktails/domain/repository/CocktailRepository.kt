package by.dazerty.mycocktails.domain.repository

import androidx.lifecycle.LiveData
import by.dazerty.mycocktails.domain.model.CocktailModel
import java.util.UUID

interface CocktailRepository {

    fun createNewItem(newItem: CocktailModel)

    fun updateItem(updatedItem: CocktailModel)

    fun deleteItems(id: UUID)

    fun getAllItems(): LiveData<ArrayList<CocktailModel>>

    fun getItemById(id: UUID): CocktailModel?
}