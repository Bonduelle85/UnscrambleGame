package com.example.unscramblegame.load.views.progress

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.ProgressBar

class CustomProgress : ProgressBar, UpdateProgress {

    private lateinit var uiState: ProgressUiState

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttrs: Int) : super(
        context,
        attributeSet,
        defStyleAttrs
    )

    override fun updateUiState(outer: ProgressUiState) {
        uiState = outer
        uiState.show(this)
    }

    override fun updateUi(visibility: Int) {
        this.visibility = visibility
    }

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val state = ProgressSavedState(it)
            state.save(uiState)
            return state
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as ProgressSavedState
        super.onRestoreInstanceState(restoredState.superState)
        updateUiState(restoredState.restore())
    }

}

interface UpdateProgress {

    fun updateUiState(outer: ProgressUiState)

    fun updateUi(visibility: Int)
}