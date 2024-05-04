package com.example.unscramblegame.views.submit

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton

class SubmitButton : MaterialButton, UpdateSubmitButton {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttrs: Int) : super(
        context,
        attributeSet,
        defStyleAttrs
    )

    override fun changeEnabled(enabled: Boolean) {
        isEnabled = enabled
    }

    override fun onSaveInstanceState(): Parcelable {
        super.onSaveInstanceState().let {
            val state = SubmitButtonSavedState(it)
            state.save(this)
            return state
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as SubmitButtonSavedState
        super.onRestoreInstanceState(restoredState.superState)
        restoredState.restore(this)
    }
}

interface UpdateSubmitButton {

    fun changeEnabled(enabled: Boolean)
}