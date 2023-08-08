package by.dazerty.mycocktails.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.dazerty.mycocktails.data.repository.MemoryCocktailRepository
import by.dazerty.mycocktails.domain.model.CocktailModel
import by.dazerty.mycocktails.domain.model.IngredientModel
import by.dazerty.mycocktails.domain.repository.CocktailRepository
import java.util.UUID

class CocktailItemViewModel: ViewModel() {

    private val cocktailRepository: CocktailRepository = MemoryCocktailRepository

    private val _currentCocktailItem: MutableLiveData<CocktailModel?> = MutableLiveData(CocktailModel(title = ""))
    val currentCocktailItem: LiveData<CocktailModel?> = _currentCocktailItem

    private var currentItemId: UUID? = null

    fun loadItem(id: UUID) {
        _currentCocktailItem.value = cocktailRepository.getItemById(id)
    }

    fun setCurrentItemId(id: UUID) {
        currentItemId = id
    }

    fun saveItem(title: String, description: String?, recipe: String?) {
        currentCocktailItem.value?.let {
            it.title = title
            it.description = description
            it.recipe = recipe

            if (currentItemId != null) {
                cocktailRepository.updateItem(it)
            } else {
                cocktailRepository.createNewItem(it)
            }
        }
    }

    fun ingredientsIsEmpty(): Boolean {
        return currentCocktailItem.value?.ingredients?.isEmpty() ?: true
    }

    fun addIngredient(item: IngredientModel) {
        currentCocktailItem.value?.ingredients?.add(item)
    }
}