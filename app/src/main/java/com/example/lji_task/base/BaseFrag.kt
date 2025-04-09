package com.example.lji_task.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


abstract class BaseFrag<binding : ViewBinding, VM : BaseVM>(
    @LayoutRes private val layoutId: Int,
) : Fragment(), View.OnClickListener {


    protected lateinit var binding: binding

    private var progress: Boolean? = false

    protected abstract fun renderState(apiRenderState: ApiRenderState)
    protected abstract val hasProgress: Boolean
    protected abstract val vm: VM?
    protected abstract fun init()
    abstract fun getViewBinding(): binding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = getViewBinding().apply {
            vm?.let {
//                setVariable(BR.vm, it)

                viewLifecycleOwner.lifecycleScope.launch {
                    it.state().collect {
                        renderState(it)
                    }
                }

            }
//            setVariable(BR.click, this@BaseFrag)
        }

        if (hasProgress) {
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    fun showProgress() {
        progress = true
    }

    fun hideProgress() {
        progress = false
    }

    fun delayedExecutor(millis: Long, executable: () -> Unit) {
        lifecycleScope.launch {
            delay(millis)
            executable.invoke()
        }
    }



    override fun onClick(v: View) {

    }


    fun <T : Any> Fragment.setBackStackData(key: String, data: T, doBack: Boolean = true) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
        if (doBack)
            findNavController().popBackStack()
    }

    fun <T : Any> Fragment.getBackStackData(key: String, result: (T) -> (Unit)) {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
            ?.observe(viewLifecycleOwner) {
                result(it)
            }
    }



    protected fun closeAppDialog() {
        val items = arrayOf<CharSequence>("Yes", "No")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Close App!")
            .setItems(items) { dialog, item ->
                when {
                    (items[item] == "Yes") -> {
                        requireActivity().finish()
                    }
                    (items[item] == "No") -> dialog.dismiss()
                }
            }

        builder.show()
    }



}
