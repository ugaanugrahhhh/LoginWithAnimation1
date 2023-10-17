package com.dicoding.picodiploma.loginwithanimation.data

import android.os.Parcel
import android.os.Parcelable

data class StoryItem(
    val id: Int,
    val name: String,
    val description: String,
    val photoUrl: String
) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(description)
        dest.writeString(photoUrl)
    }

    companion object CREATOR : Parcelable.Creator<StoryItem> {
        override fun createFromParcel(parcel: Parcel): StoryItem {
            return StoryItem(
                parcel.readInt(),
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readString()!!
            )
        }

        override fun newArray(size: Int): Array<StoryItem?> {
            return arrayOfNulls(size)
        }
    }
}
