package com.github.jirobird.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout

class UntochebleConstrainLayout:ConstraintLayout {
    constructor(context: Context?):super(context) {

    }

    constructor(context: Context, attrs: AttributeSet?):super(context,attrs){

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean { return true }
}