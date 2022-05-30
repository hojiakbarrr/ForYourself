package com.example.foryourself.ui.fragmentsAdd

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foryourself.R
import com.example.foryourself.adapter.TypeAdapter
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.databinding.CatFragmentBinding
import com.example.foryourself.databinding.DialogFiltrBinding
import com.example.foryourself.utils.LoadingDialog
import com.example.foryourself.utils.dialogbutton
import com.example.foryourself.utils.lastElements
import com.example.foryourself.utils.toast
import com.example.foryourself.viewmodels.detail.CatViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CatFragment : Fragment(), SearchView.OnQueryTextListener {
    private val args by navArgs<CatFragmentArgs>()
    private val binding: CatFragmentBinding by lazy {
        CatFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel: CatViewModel by viewModels()
    private lateinit var typeAdapter: TypeAdapter
    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext(), getString(R.string.loading_please_wait))
    }
    private var list = listOf<Result>()
    private var check: Boolean = false


    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        typeAdapter = TypeAdapter()
        exclusiveAdapters()
        onClickItem()

        viewModel.allOrderss(args.product).observe(viewLifecycleOwner) {
            list = it?.lastElements()?.toMutableList()!!
            typeAdapter.diffor.submitList(list)


//            val tt = it?.sortedByDescending { it.price!!.toInt() }
//            tt?.forEach {
////                println(it.price)
//                Log.d("posle", it.price!!)
//            }
//
//
//            it?.forEach {
////                println(it.price)
//                Log.d("do", it.price!!)
//            }


        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { message -> toast(message) }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { status ->
            try {
                if (status) loadingDialog.show() else loadingDialog.dismiss()
            } catch (e: Exception) {
            }
        }
        binding.tvCategoryCat.text = args.product
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.radio.setOnClickListener {

            val dialogBinding = DialogFiltrBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogBinding.root)
                .setPositiveButton(R.string.action_confirm, null)
                .create()
            dialog.setOnShowListener {
                dialogBinding.apply {
                    if (check) dorogoy.isChecked = true
                    else deshevyi.isChecked = true

                    dorogoy.setOnClickListener {
                        check = true
                    }
                    deshevyi.setOnClickListener {
                        check = false
                    }
                }
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                    dialogBinding.apply {
                        if (check) typeAdapter.diffor.submitList(list.sortedByDescending { it.price?.toInt() })
                        else typeAdapter.diffor.submitList(list.sortedBy { it.price?.toInt() })
                    }
                    dialog.dismiss()
                }
            }
            dialog.show()
        }
        binding.searchCat.setOnQueryTextListener(this)

    }

    private fun exclusiveAdapters() {
        binding.rvCategoriesCat.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            binding.rvCategoriesCat.adapter = typeAdapter
        }
    }

    private fun onClickItem() {
        typeAdapter.onItemClick_cate = { t ->
            toast(t.title.toString())
        }
    }

    override fun onQueryTextSubmit(searchText: String?): Boolean {
        if (searchText != null) {
            viewModel.searchProducts(searchText = searchText, args.product)
            viewModel.searchLiveData.observe(viewLifecycleOwner) {
                typeAdapter.diffor.submitList(it?.lastElements()?.toMutableList())
            }

        }
        return false
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            val searchText = newText.lowercase()
            viewModel.searchProducts(searchText = searchText, args.product)
                .observe(viewLifecycleOwner) {
                    typeAdapter.diffor.submitList(it?.lastElements()?.toMutableList())
                }
        } else {
            viewModel.allOrderss(args.product).observe(viewLifecycleOwner) {
                typeAdapter.diffor.submitList(it?.lastElements()?.toMutableList())
            }
        }
        return false
    }


}