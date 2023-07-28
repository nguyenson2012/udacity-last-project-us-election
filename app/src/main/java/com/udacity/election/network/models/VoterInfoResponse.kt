package com.udacity.election.network.models

import com.squareup.moshi.JsonClass
import com.udacity.election.network.models.Election
import com.udacity.election.network.models.ElectionOfficial
import com.udacity.election.network.models.State

@JsonClass(generateAdapter = true)
class VoterInfoResponse (
    val election: Election,
    val pollingLocations: String? = null, //TODO: Future Use
    val contests: String? = null, //TODO: Future Use
    val state: List<State>? = null,
    val electionElectionOfficials: List<ElectionOfficial>? = null
)