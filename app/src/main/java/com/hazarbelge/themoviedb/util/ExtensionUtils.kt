package com.hazarbelge.themoviedb.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import org.koin.android.compat.ViewModelCompat
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

fun <T : ViewModel> ViewModelStoreOwner.getViewModelViaKoin(
    clazz: Class<T>,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition? = null
): T {
    return ViewModelCompat.getViewModel(
        clazz = clazz,
        qualifier = qualifier,
        parameters = parameters,
        owner = this
    )
}