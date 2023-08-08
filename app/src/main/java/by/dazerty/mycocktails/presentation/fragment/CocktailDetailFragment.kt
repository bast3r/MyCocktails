package by.dazerty.mycocktails.presentation.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.dazerty.mycocktails.R
import by.dazerty.mycocktails.databinding.FragmentCocktailDetailBinding
import by.dazerty.mycocktails.presentation.viewmodel.CocktailItemViewModel
import java.util.Random
import java.util.UUID

class CocktailDetailFragment : Fragment() {

    private var _binding: FragmentCocktailDetailBinding? = null
    private val binding get() = _binding!!

    private val itemViewModel by lazy {
        ViewModelProvider(this)[CocktailItemViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCocktailDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            (it.get(COCKTAIL_DETAIL_ID) as UUID).let { id ->
                itemViewModel.loadItem(id)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setListeners()
    }

    private fun setObservers() {
        itemViewModel.currentCocktailItem.observe(viewLifecycleOwner) {
            it?.let { model ->
                Random().nextInt().let { random ->
                    if (random % 2 == 0) {
                        binding.cocktailDetailImage.setImageResource(R.drawable.test_img_1)
                    } else {
                        binding.cocktailDetailImage.setImageResource(R.drawable.test_img_2)
                    }
                }

                with (binding.cocktailDetailItems) {
                    addView(
                        createTextView(
                            currentText = model.title,
                            styleId = android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle,
                            margin = Margin(10, 10, 10, 15)
                        )
                    )

                    model.description?.let { description ->
                        addView(
                            createTextView(
                                currentText = description,
                                styleId = android.R.style.TextAppearance_DeviceDefault_Small,
                                margin = Margin(10, 10, 10, 15)
                            )
                        )
                    }

                    model.ingredients.forEach { item ->
                        addView(
                            createTextView(
                                currentText = "__",
                                styleId = android.R.style.TextAppearance_DeviceDefault_Small,
                                margin = Margin(10, 10, 10, 5)
                            )
                        )
                        addView(
                            createTextView(
                                currentText = "${item.title} - ${item.count}",
                                styleId = android.R.style.TextAppearance_DeviceDefault_Small,
                                margin = Margin(10, 10, 10, 5)
                            )
                        )
                    }

                    model.recipe?.let { recipe ->
                        addView(
                            createTextView(
                                currentText = getString(R.string.recipe_title),
                                styleId = android.R.style.TextAppearance_DeviceDefault_Small,
                                margin = Margin(10, 20, 10, 10)
                            )
                        )
                        addView(
                            createTextView(
                                currentText = recipe,
                                styleId = android.R.style.TextAppearance_DeviceDefault_Small,
                                margin = Margin(10, 10, 10, 5)
                            )
                        )
                    }
                }
            }
        }
    }

    inner class Margin(val left: Int, val top: Int, val right: Int, val bottom: Int)

    private fun createTextView(currentText: String, styleId: Int, margin: Margin): TextView {
        return TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            ).apply {
                setMargins(margin.left,margin.top,margin.right,margin.bottom);
            }
            text = currentText
            gravity = Gravity.CENTER_HORIZONTAL
            setTextAppearance(styleId)
        }
    }

    private fun setListeners() {
        binding.cocktailDetailEditButton.setOnClickListener {
            findNavController().navigate(
                R.id.cocktailDetailFragment_to_newCocktailFragment,
                itemViewModel.currentCocktailItem.value?.let {
                    Bundle().apply {
                        putSerializable(COCKTAIL_DETAIL_ID, it.id)
                    }
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val COCKTAIL_DETAIL_ID = "COCKTAIL_DETAIL_ID"
    }
}