package com.udacity.election.representative.model

import com.udacity.election.network.models.Office
import com.udacity.election.network.models.Official

data class Representative (
        val official: Official,
        val office: Office
)