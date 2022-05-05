package com.example.databindingmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.databindingmvvm.databinding.ActivityMainBinding
import com.example.databindingmvvm.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        initViews()

    }

    private fun initViews() {
        val viewModel = makeApiCall()
        activityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.setVariable(BR.viewModel, viewModel)
        activityMainBinding.executePendingBindings()

        val itemDecoration = DividerItemDecoration(this, VERTICAL)
        activityMainBinding.recyclerView.addItemDecoration(itemDecoration)
    }

    private fun makeApiCall(): MainViewModel {
        viewModel.getRecyclerListDataObserver().observe(this) {
            if (it != null) {
                activityMainBinding.progressBar.isVisible = false
                viewModel.setAdapterData(it.items)
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.makeApiCall("newyork")
        return viewModel
    }
}