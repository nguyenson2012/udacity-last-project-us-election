package com.udacity.election.representative.model

import android.os.Parcelable
import com.udacity.election.network.models.Office
import com.udacity.election.network.models.Official
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Representative (
        val official: Official,
        val office: Office
):Parcelable