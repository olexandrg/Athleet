package net.azurewebsites.athleet.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Exercise (
    val exerciseId: Int,
    val exerciseName: String,
    val description: String,
    val defaultReps: String
) : Parcelable
