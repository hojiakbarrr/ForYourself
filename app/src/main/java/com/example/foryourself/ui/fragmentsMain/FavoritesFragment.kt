package com.example.foryourself.ui.fragmentsMain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foryourself.R
import com.example.foryourself.adapter.FavoritesAdapter
import com.example.foryourself.databinding.FavoritesFragmentBinding
import com.example.foryourself.utils.*
import com.example.foryourself.viewmodels.main.FavoritesViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
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
    private lateinit var sum: String
    private lateinit var count: String
    private lateinit var name: String
    private lateinit var telephone: String

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
                count = (count.toInt() - 1).toString()
                sum =
                    ((sum.toInt() - favoritesAdapter.diffor.currentList[position].price!!.toInt()).toString())
                snaketoast(
                    "${favoritesAdapter.diffor.currentList[position].title} ${"было удалено из избранных"}",
                    requireView()
                )
//                favoritesAdapter.notifyItemRemoved(position)
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recFavor)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mycartbtn.setOnClickListener {
            if (count == "0") snaketoast("Список заказов пустой", requireView())
            else {
                bottom()
            }
        }
        viewModel.favoritesUserOrders(requireActivity()).observe(viewLifecycleOwner) {
            favoritesAdapter.diffor.submitList(it)

            count = it.size.toString()
            var sumee = 0
            for (r in it) {
                sumee += r.price!!.toInt()
            }
            sum = sumee.toString()
        }


    }

    private fun bottom() {
        val builder = BottomSheetDialog(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.bottomsheet, null)
        dialogLayout.apply {


            findViewById<TextView>(R.id.сontacseller).setOnClickListener {
                val nomer = findViewById<EditText>(R.id.editnomer).text.toString()
//                println(nomer)

                if (nomer.length < 10) {
                    dialogerror("Неправильный номер")
                } else {
//                    viewModel.postAdminRow()
                }
            }

            findViewById<ImageView>(R.id.cancell).setOnClickListener {
                builder.dismiss()
            }
            findViewById<TextView>(R.id.totalcount).setText(count)
            findViewById<TextView>(R.id.totalsum).setText(sum)
        }

        builder.setContentView(dialogLayout)
        builder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        val builder = BottomSheetDialog(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.bottomsheet, null)
        if (builder != null && builder.isShowing()) {
            builder.cancel()
        }
    }

    private fun preparadapter() {
        binding.recFavor.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    private fun onClickItem() {
        favoritesAdapter.onItemClicked = { t ->
            toast(t.title.toString())
        }
    }

}