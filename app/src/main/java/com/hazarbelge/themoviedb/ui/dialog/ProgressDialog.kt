package com.hazarbelge.themoviedb.ui.dialog

import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.AppCompatImageView
import com.flyco.dialog.widget.base.BaseDialog
import com.hazarbelge.themoviedb.R

class ProgressDialog(context: Context) : BaseDialog<ProgressDialog>(context) {

    private var icon: AppCompatImageView? = null
    private var dots: AppCompatImageView? = null

    override fun setUiBeforShow() {
        val loadAnimation = AnimationUtils.loadAnimation(context, R.anim.progress_rotate)
        loadAnimation.interpolator = LinearInterpolator()
        dots?.startAnimation(loadAnimation)
    }

    override fun onCreateView(): View {
        widthScale(0.5f)

        val view = View.inflate(context, R.layout.layout_progress_dialog, null)
        dots = view.findViewById(R.id.progress_dots)
        icon = view.findViewById(R.id.progress_icon)
        return view
    }

    override fun show() {
        Log.d("show_progress_dialog", "")
        super.show()
    }

    override fun dismiss() {
        Log.d("dismiss_progress_dialog", "")
        super.dismiss()
    }
}