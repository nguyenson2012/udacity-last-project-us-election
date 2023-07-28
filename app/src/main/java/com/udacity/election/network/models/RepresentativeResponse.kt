package com.udacity.election.network.models

import com.udacity.election.network.models.Office
import com.udacity.election.network.models.Official

data class RepresentativeResponse(
    val offices: List<Office>,
    val officials: List<Official>
)