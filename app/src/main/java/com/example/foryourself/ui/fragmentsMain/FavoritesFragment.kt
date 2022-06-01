package com.example.foryourself.ui.fragmentsMain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foryourself.R
import com.example.foryourself.adapter.FavoritesAdapter
import com.example.foryourself.adapter.TypeAdapter
import com.example.foryourself.databinding.FavoritesFragmentBinding
import com.example.foryourself.databinding.TypeFragmentBinding
import com.example.foryourself.utils.LoadingDialog
import com.example.foryourself.utils.snaketoast
import com.example.foryourself.utils.toast
import com.example.foryourself.viewmodels.main.FavoritesViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private val binding: FavoritesFragmentBinding by lazy {
        FavoritesFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var favoritesAdapter: FavoritesAdapter
    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext(), getString(R.string.loading_please_wait))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesAdapter = FavoritesAdapter()
        preparadapter()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        onClickItem()


        viewModel.getOneOrder()
        viewModel.orderLiveData.observe(viewLifecycleOwner) {
            favoritesAdapter.diffor.submitList(it)
        }
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteOrder(favoritesAdapter.diffor.currentList[position])
                snaketoast("${favoritesAdapter.diffor.currentList[position].title} ${"было удалено из избранных"}",requireView())
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recFavor)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun preparadapter() {
        binding.recFavor.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            binding.recFavor.adapter = favoritesAdapter
        }
    }

    private fun onClickItem() {
        favoritesAdapter.onItemClicked = { t ->
            toast(t.title.toString())
        }
    }

}