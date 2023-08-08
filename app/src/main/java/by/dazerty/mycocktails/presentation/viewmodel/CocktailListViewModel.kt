package by.dazerty.mycocktails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import by.dazerty.mycocktails.data.repository.MemoryCocktailRepository
import by.dazerty.mycocktails.domain.model.CocktailModel
import by.dazerty.mycocktails.domain.repository.CocktailRepository
import java.util.UUID

class CocktailListViewModel: ViewModel() {

    private val cocktailRepository: CocktailRepository = MemoryCocktailRepository

    fun getItems() = cocktailRepository.getAllItems()

    fun getItemById(id: UUID) = cocktailRepository.getItemById(id)

    fun createNewItem(newItem: CocktailModel) = cocktailRepository.createNewItem(newItem)


}