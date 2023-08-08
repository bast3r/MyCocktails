package by.dazerty.mycocktails.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.dazerty.mycocktails.R
import by.dazerty.mycocktails.databinding.FragmentCreateCocktailBinding
import by.dazerty.mycocktails.presentation.view.AddNewIngredientView
import by.dazerty.mycocktails.presentation.viewmodel.CocktailItemViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.UUID

class CreateCocktailFragment : Fragment() {

    private var _binding: FragmentCreateCocktailBinding? = null
    private val binding get() = _binding!!

    private val itemViewModel by lazy {
        ViewModelProvider(this)[CocktailItemViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateCocktailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            (it.get(CocktailDetailFragment.COCKTAIL_DETAIL_ID) as? UUID)?.let { id ->
                itemViewModel.setCurrentItemId(id)
                itemViewModel.loadItem(id)

                //TODO change fragment title to Edit mode
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { context ->
            binding.addNewCocktailIngredients.addView(createIngredientView(context))
        }

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.addNewCocktailSave.setOnClickListener {
            if (validateForm()) {
                itemViewModel.saveItem(
                    title = binding.addNewCocktailTitle.text.toString(),
                    description = binding.addNewCocktailDescription.text.toString(),
                    recipe = binding.addNewCocktailRecipe.text.toString(),
                )
                Toast.makeText(context, R.string.item_save, Toast.LENGTH_LONG).show()
                findNavController().navigateUp()
            } else {
                Snackbar.make(
                    binding.addNewCocktailSave,
                    R.string.msg_please_check_and_try_again,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        binding.addNewCocktailCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setObservers() {
        itemViewModel.currentCocktailItem.observe(viewLifecycleOwner) {
            it?.let {
                binding.addNewCocktailTitle.setText(it.title)
                binding.addNewCocktailDescription.setText(it.description)
                binding.addNewCocktailRecipe.setText(it.recipe)
                //TODO show current ingredients
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        with (binding.addNewCocktailTitle) {
            if (text.isNullOrEmpty()) {
                error = getString(R.string.msg_should_be_set)
                isValid = false
            } else {
                error = null
            }
        }

        if (itemViewModel.ingredientsIsEmpty()) {
            Snackbar.make(
                binding.addNewCocktailSave,
                R.string.msg_please_add_one_igredient_as_least,
                Snackbar.LENGTH_LONG
            ).show()
            isValid = false
        }

        return isValid
    }

    private fun createIngredientView(context: Context): AddNewIngredientView {
        return AddNewIngredientView(context).apply {
            onSaveItem = { item ->
                itemViewModel.addIngredient(item)
                binding.addNewCocktailIngredients.addView(createIngredientView(context))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}