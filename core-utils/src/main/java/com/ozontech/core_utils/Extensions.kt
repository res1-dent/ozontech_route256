package com.ozontech.core_utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ozontech.core_utils.di.DiComponent
import com.ozontech.core_utils.di.DiStorage
import kotlin.reflect.KClass

fun <T : ViewBinding> ViewGroup.inflate(
    inflateBinding: (
        inflater: LayoutInflater,
        root: ViewGroup?,
        attachToRoot: Boolean
    ) -> T, attachToRoot: Boolean = false
): T {
    val inflater = LayoutInflater.from(context)
    return inflateBinding(inflater, this, attachToRoot)
}


@Suppress("UNCHECKED_CAST")
fun <T: DiComponent> Fragment.getComp(component: KClass<T>): T {
    return (requireContext().applicationContext as DiStorage<T>).initAndGet(component)
}

@Suppress("UNCHECKED_CAST")
fun <T: DiComponent> Fragment.releaseComp(component: KClass<T>){
    (requireContext().applicationContext as DiStorage<T>).release(component)
}



