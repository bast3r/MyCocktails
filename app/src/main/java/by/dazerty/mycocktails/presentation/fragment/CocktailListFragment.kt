package by.dazerty.mycocktails.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.dazerty.mycocktails.R
import by.dazerty.mycocktails.databinding.FragmentCocktailListBinding
import by.dazerty.mycocktails.presentation.adapter.CocktailListAdapter
import by.dazerty.mycocktails.presentation.fragment.CocktailDetailFragment.Companion.COCKTAIL_DETAIL_ID
import by.dazerty.mycocktails.presentation.viewmodel.CocktailListViewModel

class CocktailListFragment : Fragment() {

    private var _binding: FragmentCocktailListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this)[CocktailListViewModel::class.java]
    }
    private val cocktailAdapter = CocktailListAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCocktailListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cocktailListRecycler.adapter = cocktailAdapter

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        cocktailAdapter.onCocktailClick = { id ->
            findNavController().navigate(
                R.id.cocktailListFragment_to_cocktailDetailFragment,
                Bundle().apply {
                    putSerializable(COCKTAIL_DETAIL_ID, id)
                }
            )
        }
    }

    private fun setObservers() {
        viewModel.getItems().observe(viewLifecycleOwner) {
            cocktailAdapter.submitList(it)

            setVisibility(it.size > 0)
        }
    }

    private fun setVisibility(hasItems: Boolean) {
        binding.cocktailListRecycler.isVisible = hasItems
        binding.cocktailListFirstItemHint.isVisible = !hasItems
        binding.cocktailListImage.isVisible = !hasItems
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}