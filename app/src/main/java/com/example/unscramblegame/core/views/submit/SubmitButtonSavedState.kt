package com.example.unscramblegame.core.views.submit

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.View.BaseSavedState

class SubmitButtonSavedState : BaseSavedState {

    private var enabled: Boolean = false

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn) {
        enabled = parcelIn.readInt() == 1
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeInt(if (enabled) 1 else 0)
    }

    fun save(view: View) {
        enabled = view.isEnabled
    }

    fun restore(view: View) {
        view.isEnabled = enabled
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<SubmitButtonSavedState> {
        override fun createFromParcel(parcel: Parcel): SubmitButtonSavedState =
            SubmitButtonSavedState(parcel)

        override fun newArray(size: Int): Array<SubmitButtonSavedState?> =
            arrayOfNulls(size)
    }
}