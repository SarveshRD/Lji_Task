package com.example.lji_task.fragment


import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lji_task.HomeTabViewModel
import com.example.lji_task.R
import com.example.lji_task.RecycleViewAdapter
import com.example.lji_task.base.ApiRenderState
import com.example.lji_task.base.BaseFrag
import com.example.lji_task.data.Item
import com.example.lji_task.data.LjiApiResponse
import com.example.lji_task.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFrag<FragmentHomeBinding, HomeTabViewModel>(R.layout.fragment_home) {


    override val hasProgress: Boolean = false
    override val vm: HomeTabViewModel by viewModels()

    private lateinit var adapter: RecycleViewAdapter
    private lateinit var dataList: List<Item>


    override fun getViewBinding(): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)

    override fun init() {
        setUpClickListener()
        binding.homeCategories.etHomeSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().lowercase()

                val filteredList = dataList.sortedWith(
                    compareByDescending<Item> {
                        it.name.contains(searchText, ignoreCase = true)
                    }.thenBy {
                        it.name.lowercase()
                    }
                )

                adapter.filterList(filteredList)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun renderState(apiRenderState: ApiRenderState) {
        when (apiRenderState) {
            is ApiRenderState.Loading -> {
                showProgress()
            }

            is ApiRenderState.ApiSuccess<*> -> {
                when (apiRenderState.result) {
                    is LjiApiResponse -> {
                        val model = apiRenderState.result
                        println(model)

                        setupArticlesRecyclerView(model.items)

                    }
                }
            }

            is ApiRenderState.ValidationError -> {
                apiRenderState.message?.let {
                    println("ValidationError*********" + getString(it))
                }
            }

            is ApiRenderState.ApiError<*> -> {
                println("ApiError*********" + apiRenderState.error.toString())

            }

            else -> {}
        }
    }


    private fun setUpClickListener() {
        vm.getData()
    }

    private fun setupArticlesRecyclerView(data: List<Item>) {

        dataList = data

        if (dataList.isNotEmpty()) {
            adapter = RecycleViewAdapter(data)
            binding.homeCategories.rvPopularNearYou.adapter = adapter
            adapter.setOnItemClickListener(object :
                RecycleViewAdapter.OnItemClickListener {
                override fun onItemClick(position: Int, v: View) {
                    val action = HomeFragmentDirections.actionHomeFragmentToWebViewFragment(
                        url = dataList[position].htmlUrl
                    )
                    findNavController().navigate(action)
                }
            }
            )
        }


    }

}