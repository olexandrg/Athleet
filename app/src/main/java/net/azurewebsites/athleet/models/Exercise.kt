package net.azurewebsites.athleet.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Exercise (
    val exerciseId: Int,
    val exerciseName: String,
    val description: String,
    val measureUnits: String,
    val defaultReps: Int,
    val exerciseSets: Int,
    val unitCount: Int
) : Parcelable



//public int ExerciseId { get; set; }
//public string ExerciseName { get; set; }
//public string Description { get; set; }
//public string MeasureUnits { get; set; }
//public int DefaultReps { get; set; }
//public int exerciseSets { get; set; }
//public decimal unitCount { get; set; }