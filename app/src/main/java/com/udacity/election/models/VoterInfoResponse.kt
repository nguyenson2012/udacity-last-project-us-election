package com.udacity.election.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class VoterInfoResponse (
    val election: Election,
    val state: List<State>? = null,
    val electionElectionOfficials: List<ElectionOfficial>? = null
    val pollingLocations: String? = null,
    val contests: String? = null,
)