package com.infoshell.presentation.ui.catalog.category

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.infoshell.book24.R
import com.infoshell.book24.databinding.FragmentCategoryBinding
import com.infoshell.presentation.enity.DisplayCategory
import com.infoshell.presentation.ui.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {
    override var layoutId: Int = R.layout.fragment_category
    private val viewModel: CategoryViewModel by viewModel()
    private val categoriesDataAdapter: CategoryDataAdapter by inject()

    private val onClickHandler = object : CategoryDataAdapter.Companion.OnItemClickListener {
        override fun handle(view: View, displayCategory: DisplayCategory) {
            val category = bundleOf("category" to displayCategory)
            view.findNavController().navigate(R.id.navigate_to_category_products, category)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerView: RecyclerView = viewBinding!!.viewEmployees
        recyclerView.setHasFixedSize(true)

        categoriesDataAdapter.onClickHandler = onClickHandler
        recyclerView.adapter = categoriesDataAdapter

        viewModel.getAllCategories()
        viewModel.categoryData.observe(viewLifecycleOwner, Observer { value ->
            if (value != null) {
                categoriesDataAdapter.setCategoriesList(value)
            } else {
                val navController = findNavController()
                navController.navigate(R.id.unauthorizedProfile)
            }
        })
    }
}
