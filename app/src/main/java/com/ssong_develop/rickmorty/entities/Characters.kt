package com.ssong_develop.rickmorty.entities

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ssong_develop.rickmorty.R
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Entity(tableName = "characters_table")
data class Characters(
    var page: Int = 0,
    /** The id of the character */
    @SerialName("id")
    @PrimaryKey
    val id: Int = -1,
    /** The name of the character */
    @SerialName("name")
    val name: String = "",
    /** The status of the character('Alive','Dead','unknown') */
    @SerialName("status")
    val status: String = "",
    /** The species of the character */
    @SerialName("species")
    val species: String = "",
    /** The type or subspecies of the character */
    @SerialName("type")
    val type: String = "",
    /** The gender of the character */
    @SerialName("gender")
    val gender: String = "",
    /** Name and link to the character's origin location */
    @SerialName("origin")
    @Embedded
    var origin: Origin? = null,
    /** Last Known location */
    @SerialName("location")
    @Embedded
    var location: Location? = null,
    /** Link to the character's image. */
    @SerialName("image")
    val image: String = "",
    /** List of episodes in which this character appeared*/
    @SerialName("episode")
    val episode: List<String> = emptyList(),
    /** Link to the character's own URL endpoint */
    @SerialName("url")
    val url: String = "",
    /** Time at which the character was created in the database */
    @SerialName("created")
    val created: String = ""
) : Parcelable {

    @Serializable
    @Parcelize
    data class Origin(
        @SerialName("name")
        val originName: String,
        @SerialName("url")
        val originUrl: String
    ) : Parcelable

    @Parcelize
    @Serializable
    data class Location(
        @SerialName("name")
        val locationName: String,
        @SerialName("url")
        val locationUrl: String
    ) : Parcelable
}

enum class Status(val status: String, @ColorRes val colorString: Int) {
    ALIVE("Alive", R.color.alive), DEAD("Dead", R.color.dead), UNKNOWN("unknown", R.color.gray);

    companion object {
        fun color(status: String): Int? = values().find { it.status == status }?.colorString
    }
}