package com.example.unscramblegame.load.views.retry

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.view.View.BaseSavedState

class RetrySavedState : BaseSavedState {

    private lateinit var state: RetryUiState

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn) {
        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            parcelIn.readSerializable(
                RetryUiState::class.java.classLoader,
                RetryUiState::class.java
            ) as RetryUiState
        } else {
            parcelIn.readSerializable() as RetryUiState
        }
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSerializable(state)
    }

    fun restore(): RetryUiState = state

    fun save(uiState: RetryUiState) {
        state = uiState
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<RetrySavedState> {
        override fun createFromParcel(parcel: Parcel): RetrySavedState =
            RetrySavedState(parcel)

        override fun newArray(size: Int): Array<RetrySavedState?> =
            arrayOfNulls(size)
    }
}