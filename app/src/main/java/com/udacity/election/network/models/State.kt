package com.udacity.election.network.models

import com.udacity.election.network.models.AdministrationBody

data class State (
    val name: String,
    val electionAdministrationBody: AdministrationBody
)