package com.example.unscramblegame.views.input

import android.content.Context
import android.os.Parcelable
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import com.example.unscramblegame.databinding.InputBinding


class CustomInput : FrameLayout, InputAction {

    private var uiState: InputUiState = InputUiState.Initial

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttrs: Int) : super(
        context,
        attributeSet,
        defStyleAttrs
    )

    private val binding = InputBinding.inflate(LayoutInflater.from(context), this, true)

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val state = CustomInputSavedState(it)
            state.save(uiState)
            return state
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as CustomInputSavedState
        super.onRestoreInstanceState(restoredState.superState)
        updateState(restoredState.restore())
    }

    override fun updateState(state: InputUiState) {
        uiState = state
        uiState.update(this)
    }

    override fun clearText() {
        binding.inputEditText.setText("")
    }

    override fun updateError(error: Int) {
        binding.inputLayout.error = binding.inputLayout.context.getString(error)
    }

    override fun updateHasError(hasError: Boolean) {
        binding.inputLayout.isErrorEnabled = hasError
    }

    override fun clearError() {
        binding.inputLayout.error = ""
    }

    fun addTextChangedListener(afterTextChanged: (Editable?) -> Unit) {
        binding.inputEditText.doAfterTextChanged(afterTextChanged)
    }

    fun getText(): String {
        return binding.inputEditText.text.toString()
    }
}

interface InputAction {

    fun updateState(state: InputUiState)

    fun clearText()
    fun updateError(@StringRes error: Int)
    fun clearError()
    fun updateHasError(hasError: Boolean)
}