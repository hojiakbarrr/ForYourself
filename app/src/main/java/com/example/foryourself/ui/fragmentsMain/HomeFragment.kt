package com.example.foryourself.ui.fragmentsMain

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foryourself.data.currentUser.CurrentUser
import com.example.foryourself.R
import com.example.foryourself.utils.SharedPreferences
import com.example.foryourself.adapter.BestSellerAdapter
import com.example.foryourself.adapter.ExclusiveAdapter
import com.example.foryourself.databinding.HomeFragmentBinding
import com.example.foryourself.utils.LoadingDialog
import com.example.foryourself.utils.lastElements
import com.example.foryourself.utils.toast
import com.example.foryourself.utils.toastUP
import com.example.foryourself.viewmodels.main.HomeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener,
    ExclusiveAdapter.IconClickListener {
    private lateinit var binding: HomeFragmentBinding

    private lateinit var exclusiveAdapter: ExclusiveAdapter
    private lateinit var bestAdapter: BestSellerAdapter
    private val viewModel: HomeViewModel by viewModels()
    private val imageList = ArrayList<SlideModel>()
    private lateinit var personName: String
    private lateinit var personEmail: String
    private lateinit var idgoogle: String
    private lateinit var positionnn: String
    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext(), getString(R.string.loading_please_wait))
    }

    private fun exclusiveAdapters() {
        binding.recExclusive.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = exclusiveAdapter
        }
        binding.recBests.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = bestAdapter
        }
    }


    override fun onStart() {
        super.onStart()
        viewModel.getallOrders(requireActivity()).observe(viewLifecycleOwner) {
            exclusiveAdapter.diffor.submitList(it?.lastElements()?.toMutableList())
        }
        viewModel.orderLiveData.observe(viewLifecycleOwner) {
            bestAdapter.productList = it?.lastElements()?.toMutableList()!!
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { message -> toast(message) }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { status ->
            try {
                if (status) loadingDialog.show()
                else loadingDialog.dismiss()
            } catch (e: Exception) {
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (acct == null) {
            val action = HomeFragmentDirections.actionHomeFragmentToSplashFragment()
            Navigation.findNavController(requireView()).navigate(action)
        } else {
            personEmail = acct.email.toString()
            personName = acct.displayName.toString()
            idgoogle = acct.id.toString()
            viewModel.getuserOrder(
                acct.email.toString(), acct.displayName.toString(),
                acct.id.toString(), requireActivity()
            )
            SharedPreferences().saveCurrentUser(
                CurrentUser(
                    email = acct.email!!,
                    name = acct.displayName!!,
                    id = acct.id!!
                ), requireActivity()
            )

            val ee = SharedPreferences().getCurrentUser(requireActivity()).email
            val rr = SharedPreferences().getCurrentUser(requireActivity()).name

            Log.d("Share", ee)
            Log.d("Sharess", rr)

            println("${SharedPreferences().getCurrentUser(requireActivity())}eeeee")
            println("${SharedPreferences().getCurrentUser(requireActivity())}eeeee")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(layoutInflater, container, false)
        exclusiveAdapter = ExclusiveAdapter(this)
        bestAdapter = BestSellerAdapter()
        exclusiveAdapters()
        swipe()
        return binding.root
    }

    private fun swipe() {
        binding.apply {
            swipeToRefresh.setOnRefreshListener(this@HomeFragment)
            swipeToRefresh.setColorSchemeColors(
                Color.RED, Color.BLUE, Color.DKGRAY
            )
        }
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ee = SharedPreferences().getCurrentUser(requireActivity())

        if (ee.id != "" ) {
            viewModel.getallOrders(requireActivity()).observe(viewLifecycleOwner) {
//            exclusiveAdapter.diffor.submitList(it?.lastElements()?.toMutableList())
                bestAdapter.productList = it?.lastElements()?.toMutableList()!!
            }
            viewModel.orderLiveData.observe(viewLifecycleOwner) {
                bestAdapter.productList = it?.lastElements()?.toMutableList()!!
            }
            viewModel.errorLiveData.observe(viewLifecycleOwner) { message -> toastUP(message) }

            viewModel.loadingLiveData.observe(viewLifecycleOwner) { status ->
                try {
                    if (status) loadingDialog.show()
                    else loadingDialog.dismiss()
                } catch (e: Exception) {
                }
            }
            viewModel.loadingtoastLiveData.observe(viewLifecycleOwner) {
                toastUP(it)
            }

        }


        viewModel.getReklama().observe(viewLifecycleOwner) { it ->
            it?.forEach {

                imageList.add(SlideModel(it.reklama1!!.url, scaleType = ScaleTypes.FIT))
                imageList.add(SlideModel(it.reklama2!!.url, scaleType = ScaleTypes.FIT))
                imageList.add(SlideModel(it.reklama3!!.url, scaleType = ScaleTypes.FIT))
                binding.apply {
                    imageSlider.setImageList(imageList)
                    imageSlider.setItemClickListener(object : ItemClickListener {
                        override fun onItemSelected(position: Int) {
                            toast(position.toString())
                        }
                    })
                }
            }
        }





        binding.apply {
            exclusiveTxt.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToTypeFragment("Эксклюзив")
                Navigation.findNavController(it).navigate(action)
            }

            bestsellerTxt.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToTypeFragment("Бестселлер")
                Navigation.findNavController(it).navigate(action)
            }

        }

        onClickItem()
        onClickItemBestSeller()




    }

    private fun onClickItemBestSeller() {
        bestAdapter.onItemClickBestseller = {
            viewModel.addToFav(it, requireActivity())
                .observe(viewLifecycleOwner) { product ->
                    bestAdapter.updateItem(product)
                }
            viewModel.favLiveData.observe(viewLifecycleOwner) {
                toastUP(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE
    }

    private fun onClickItem() {
        exclusiveAdapter.onItemClick = { t ->
            viewModel.addToFav(t, requireActivity())
                .observe(viewLifecycleOwner) { product ->
//                exclusiveAdapter.updateItem(product, positionnn)
                }

            t.isFavorite = true
            val index = exclusiveAdapter.diffor.currentList.indexOf(t)
            exclusiveAdapter.notifyItemChanged(index)

            viewModel.favLiveData.observe(viewLifecycleOwner) {
                toastUP(it)
            }
        }
    }

    override fun onRefresh() {
        binding.apply {
            swipeToRefresh.isRefreshing = true
            swipeToRefresh.postDelayed({
                swipeToRefresh.isRefreshing = false
                if (!swipeToRefresh.isRefreshing) {
                    viewModel.allOrdersREFRESH(personName, personEmail, idgoogle, requireActivity())
                    viewModel.getReklama()
                }
            }, 1500)
        }
    }

    override fun itemClick(position: Int) {
        positionnn = position.toString()
    }

}