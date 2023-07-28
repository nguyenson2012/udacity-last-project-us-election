package com.udacity.election.models

import com.squareup.moshi.Json

data class Office (
    val name: String,
    @Json(name="divisionId") val division: Division,
    @Json(name="officialIndices") val officialList: List<Int>
) {
    fun getListRepresentatives(officials: List<Official>): List<Representative> {
        return this.officialList.map { index ->
            Representative(officials[index], this)
        }
    }
}
