package by.dazerty.mycocktails.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.dazerty.mycocktails.domain.model.CocktailModel
import by.dazerty.mycocktails.domain.repository.CocktailRepository
import java.util.UUID

object MemoryCocktailRepository: CocktailRepository {

    private val _items = MutableLiveData<ArrayList<CocktailModel>>().apply {
        value = ArrayList()
    }
    val items: LiveData<ArrayList<CocktailModel>>
        get() = _items

    override fun createNewItem(newItem: CocktailModel) {
        _items.value?.add(newItem)
    }

    override fun updateItem(updatedItem: CocktailModel) {
        _items.value?.let { items ->
            val currentItem = items.firstOrNull { it.id == updatedItem.id }
            val currentItemIndex = items.indexOf(currentItem)
            items.remove(currentItem)
            items.add(currentItemIndex, updatedItem)
        }
    }

    override fun deleteItems(id: UUID) {
        _items.value?.let { items ->
            val currentItem = items.firstOrNull { it.id == id }
            items.remove(currentItem)
        }
    }

    override fun getAllItems(): LiveData<ArrayList<CocktailModel>> {
        return items
    }

    override fun getItemById(id: UUID): CocktailModel? {
        return _items.value?.let { items ->
            items.firstOrNull { it.id == id }
        }
    }
}