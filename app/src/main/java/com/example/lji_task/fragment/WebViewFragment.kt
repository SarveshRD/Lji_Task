package com.example.lji_task.fragment


import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lji_task.HomeTabViewModel
import com.example.lji_task.R
import com.example.lji_task.base.ApiRenderState
import com.example.lji_task.base.BaseFrag
import com.example.lji_task.databinding.FragmentWebViewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WebViewFragment :
    BaseFrag<FragmentWebViewBinding, HomeTabViewModel>(R.layout.fragment_web_view)  {

    override val hasProgress: Boolean = false
    override fun getViewBinding() = FragmentWebViewBinding.inflate(layoutInflater)
    override val vm: HomeTabViewModel by viewModels()

    private val args: WebViewFragmentArgs by navArgs()


    override fun init() {
        setUpListener()
        getArgs()
    }

    private fun getArgs() {
        arguments?.let {
            loadWebView(args.url)
        }
    }

    private fun loadWebView(mUrl: String?) {
        mUrl?.let {
            binding.wv.loadUrl(it)
        }
    }

    private fun setUpListener() {
        with(binding) {
            binding.ivBackArrow.setOnClickListener(this@WebViewFragment)
        }
    }

    override fun renderState(apiRenderState: ApiRenderState) {
        when (apiRenderState) {
            is ApiRenderState.Loading -> {
                showProgress()
            }
            is ApiRenderState.ApiSuccess<*> -> {

                when (apiRenderState.result) {

                }
            }
            else -> {}
        }
    }


    override fun onClick(v: View) {
        super.onClick(v)
        with(binding) {
            when (v) {
                ivBackArrow -> {
                    findNavController().popBackStack()
                }
                else -> {

                }
            }
        }
    }



}