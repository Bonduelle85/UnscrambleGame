package com.example.unscramblegame.load.views.progress

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.view.View.BaseSavedState

class ProgressSavedState : BaseSavedState {

    private lateinit var state: ProgressUiState

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn) {
        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            parcelIn.readSerializable(
                ProgressUiState::class.java.classLoader,
                ProgressUiState::class.java
            ) as ProgressUiState
        } else {
            parcelIn.readSerializable() as ProgressUiState
        }
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSerializable(state)
    }

    fun restore(): ProgressUiState = state

    fun save(uiState: ProgressUiState) {
        state = uiState
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<ProgressSavedState> {
        override fun createFromParcel(parcel: Parcel): ProgressSavedState =
            ProgressSavedState(parcel)

        override fun newArray(size: Int): Array<ProgressSavedState?> =
            arrayOfNulls(size)
    }
}