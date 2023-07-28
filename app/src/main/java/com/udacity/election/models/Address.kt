package com.udacity.election.models

data class Address (
        var line1: String,
        var line2: String? = null,
        var city: String,
        var state: String,
        var zip: String
) {
    fun getFormattedStr(): String {
        var res = line1.plus("\n")
        if (!line2.isNullOrEmpty()) {
            res = res.plus(line2).plus("\n")
        }
        res = res.plus("$city, $state $zip")
        return res
    }
}