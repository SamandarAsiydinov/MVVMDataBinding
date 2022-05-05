package com.example.databindingmvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.databindingmvvm.adapter.RvAdapter
import com.example.databindingmvvm.model.RecyclerData
import com.example.databindingmvvm.model.RecyclerList
import com.example.databindingmvvm.network.RetroService
import com.example.databindingmvvm.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val recyclerListData: MutableLiveData<RecyclerList> = MutableLiveData()
    private val recyclerListAdapter = RvAdapter()

    fun getRecyclerListDataObserver(): MutableLiveData<RecyclerList> {
        return recyclerListData
    }

    fun getAdapter(): RvAdapter {
        return recyclerListAdapter
    }

    fun setAdapterData(data: ArrayList<RecyclerData>) {
        recyclerListAdapter.submitList(data)
    }

    fun makeApiCall(query: String) {
        val retroInstance = RetrofitInstance.getRetrofitInstance().create(RetroService::class.java)
        val call = retroInstance.getDataFromApi(query)
        call.enqueue(object : Callback<RecyclerList> {
            override fun onResponse(call: Call<RecyclerList>, response: Response<RecyclerList>) {
                if (response.isSuccessful) {
                    recyclerListData.postValue(response.body())
                } else {
                    Log.d("@@@@@", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<RecyclerList>, t: Throwable) {
                recyclerListData.postValue(null)
            }
        })
    }
}