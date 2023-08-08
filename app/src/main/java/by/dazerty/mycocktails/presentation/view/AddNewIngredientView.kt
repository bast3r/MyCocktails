package by.dazerty.mycocktails.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import by.dazerty.mycocktails.R
import by.dazerty.mycocktails.databinding.LayoutNewIngredientBinding
import by.dazerty.mycocktails.domain.model.IngredientModel

class AddNewIngredientView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    lateinit var onSaveItem: (item: IngredientModel) -> Unit

    private val b by lazy {
        LayoutNewIngredientBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    init {
        b.addNewIngredientSave.setOnClickListener {
            if (validateItem()) {
                onSaveItem.invoke(IngredientModel(
                    title = b.addNewCocktailIngredientName.text.toString(),
                    count = b.addNewCocktailIngredientCount.text.toString().toDouble()
                ))
                b.addNewIngredientSave.isEnabled = false
            }
        }
    }

    private fun validateItem(): Boolean {
        var isValid = true

        with (b.addNewCocktailIngredientName) {
            if (text.isNullOrEmpty()) {
                error = context.getString(R.string.msg_should_be_set)
                isValid = false
            } else {
                error
            }
        }

        with (b.addNewCocktailIngredientCount) {
            if (text.isNullOrEmpty()) {
                error = context.getString(R.string.msg_should_be_set)
                isValid = false
            } else {
                error
            }
        }

        return isValid
    }

//    fun populate(model: CreditOrder) {
//        this.model = model
//        b.creditOrderDate.text = model.date
//        b.creditOrderTag.populate(model.displayStatusModel)
//        b.creditOrderTitle.text = model.headboard
//        b.creditOrderAmount.populate(model.amount)
//        model.term?.let {
//            b.creditOrderTerm.isVisible = true
//            b.creditOrderTerm.populate(it)
//        } ?: run { b.creditOrderTerm.isVisible = false }
//        b.creditOrderRate.populate(model.rate)
//        model.buttonText?.let {
//            b.creditOrderButton.isVisible = true
//            b.creditOrderButton.text = it
//        } ?: run { b.creditOrderButton.isVisible = false }
//    }
}