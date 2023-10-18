import android.os.Parcel
import android.os.Parcelable

data class StoryItem(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: String,
    val lat: Double,
    val lon: Double
) : Parcelable {
    // Implementasi Parcelable
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeString(description)
        writeString(photoUrl)
        writeString(createdAt)
        writeDouble(lat)
        writeDouble(lon)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<StoryItem> {
            override fun createFromParcel(parcel: Parcel) = StoryItem(parcel)
            override fun newArray(size: Int): Array<StoryItem?> = arrayOfNulls(size)
        }
    }
}
