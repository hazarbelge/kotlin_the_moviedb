package com.hazarbelge.themoviedb.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewbinding.ViewBinding
import com.hazarbelge.themoviedb.common.getViewModelViaKoin
import com.hazarbelge.themoviedb.common.ErrorUtils
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {

    private val errorUi by lazy { ErrorUtils(this) }

    private val viewModelClass: Class<VM> by lazy {
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
    }

    protected abstract val binding: VB

    protected val viewModel: VM by lazy { getViewModelViaKoin(viewModelClass) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)

        viewModel.apply {
            serverMessageObserver.observe(this@BaseActivity) {
                errorUi.errorMessage(it)
            }
        }
    }

    protected fun startActivity(classCanonicalName: String, bundlePair: Pair<String, Bundle>? = null ) {
        Intent().apply {
            bundlePair?.let { putExtra(it.first, it.second) }
            setClassName(this@BaseActivity, classCanonicalName)
            startActivity(this)
        }
    }
}