package com.example.unscramblegame.core.views.word

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textview.MaterialTextView

class CustomTextView : MaterialTextView, UpdateText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttrs: Int) : super(
        context,
        attributeSet,
        defStyleAttrs
    )

    override fun updateText(text: String) {
        setText(text)
    }
}

interface UpdateText {

    fun updateText(text: String)
}