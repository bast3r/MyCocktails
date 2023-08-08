package by.dazerty.mycocktails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import by.dazerty.mycocktails.data.repository.MemoryCocktailRepository
import by.dazerty.mycocktails.domain.repository.CocktailRepository

class CocktailListViewModel: ViewModel() {

    private val cocktailRepository: CocktailRepository = MemoryCocktailRepository

    fun getItems() = cocktailRepository.getAllItems()
}