package com.example.lji_task


import com.example.lji_task.base.ApiRenderState
import com.example.lji_task.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeTabViewModel
@Inject constructor(private val repo: HomeTabRepository) : BaseVM(){

    fun getData() {
        scope {
            state.emit(ApiRenderState.Loading)
            repo.getData(onApiError)?.let {
                state.emit(ApiRenderState.ApiSuccess(it))
                return@scope
            }
        }
    }


}