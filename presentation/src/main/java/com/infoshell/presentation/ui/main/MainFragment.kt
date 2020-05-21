package com.infoshell.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.infoshell.book24.R
import com.infoshell.book24.databinding.FragmentMainBinding
import com.infoshell.presentation.ui.base.BaseFragment

class MainFragment : BaseFragment<FragmentMainBinding>() {

    override var layoutId: Int = R.layout.fragment_main

    private lateinit var navController: NavController

    private val fragmentsChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            notifySearchView(destination.id)
            notifyBackButton(destination.id)
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.setActionBar(viewBinding?.toolbar)
        activity?.actionBar?.title = ""

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (!navController.navigateUp()) requireActivity().finish()
        }

        viewBinding?.let {
            navController =
                Navigation.findNavController(it.rootContent.findViewById<View>(R.id.navFragmentMain))
            it.bottomNavigationHome.setupWithNavController(navController)

            viewBinding?.searchET?.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) navController.navigate(R.id.searchFragment)
            }

            navController.addOnDestinationChangedListener(fragmentsChangedListener)
        }
    }

    private fun notifySearchView(id: Int) {
        when (id) {
            R.id.home, R.id.categories, R.id.category_products, R.id.searchFragment -> {
                viewBinding?.searchET?.clearFocus()
                viewBinding?.searchET?.visibility = View.VISIBLE
            }
            else -> {
                viewBinding?.searchET?.clearFocus()
                viewBinding?.searchET?.visibility = View.GONE
            }
        }
    }

    private fun notifyBackButton(id: Int) {
        when (id) {
            R.id.searchFragment, R.id.category_products, R.id.productFragment -> {
                viewBinding?.toolbar?.visibility = View.VISIBLE
                activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
            }
            R.id.home, R.id.categories -> {
                viewBinding?.toolbar?.visibility = View.VISIBLE
                activity?.actionBar?.setDisplayHomeAsUpEnabled(false)
            }
            else -> {
                viewBinding?.toolbar?.visibility = View.GONE
                activity?.actionBar?.setDisplayHomeAsUpEnabled(false)
                activity?.setActionBar(null)
            }
        }
    }
}