package by.dazerty.mycocktails.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.dazerty.mycocktails.R
import by.dazerty.mycocktails.databinding.LayoutCocktailItemBinding
import by.dazerty.mycocktails.domain.model.CocktailModel
import java.util.Random
import java.util.UUID

class CocktailListAdapter: ListAdapter<CocktailModel, CocktailListAdapter.CocktailViewHolder>(CocktailDiffUtils()) {

    var onCocktailClick: (id: UUID) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        return CocktailViewHolder(LayoutCocktailItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val cocktail = getItem(position)
        holder.itemView.setOnClickListener {
            onCocktailClick.invoke(cocktail.id)
        }
        holder.bind(cocktail)
    }

    inner class CocktailViewHolder(private val binding : LayoutCocktailItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(cocktail: CocktailModel) {
            binding.itemTitle.text = cocktail.title

            Random().nextInt().let {
                if (it % 2 == 0) {
                    binding.itemImage.setImageResource(R.drawable.test_img_1)
                } else {
                    binding.itemImage.setImageResource(R.drawable.test_img_2)
                }
            }
        }
    }

    class CocktailDiffUtils: DiffUtil.ItemCallback<CocktailModel>() {

        override fun areItemsTheSame(oldItem: CocktailModel, newItem: CocktailModel): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: CocktailModel, newItem: CocktailModel): Boolean {
            return oldItem == newItem
        }

    }
}