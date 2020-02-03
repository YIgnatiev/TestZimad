package com.yaroslav.testzimad.response

import android.os.Parcel
import android.os.Parcelable

data class Data(
    var title: String,
    var url: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0!!.writeString(title)
        p0.writeString(url)
    }
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}
