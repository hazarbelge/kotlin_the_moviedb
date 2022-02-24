package com.hazarbelge.themoviedb.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

import com.hazarbelge.themoviedb.common.getViewModelViaKoin

import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VM: ViewModel, VB: ViewBinding> : Fragment() {

    private val viewModelClass: Class<VM> by lazy {
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
    }

    protected abstract val binding: VB

    protected val viewModel: VM by lazy { getViewModelViaKoin(viewModelClass) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        return binding.root
    }

    protected fun startActivity(classCanonicalName: String, bundlePair: Pair<String, Bundle>? = null ) {
        Intent().apply {
            bundlePair?.let { putExtra(it.first, it.second) }
            setClassName(requireContext(), classCanonicalName)
            startActivity(this)
        }
    }
}