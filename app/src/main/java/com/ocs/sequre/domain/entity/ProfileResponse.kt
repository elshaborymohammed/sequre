package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ocs.sequre.domain.entity.Dependent
import com.ocs.sequre.domain.entity.Profile

data class ProfileResponse(
    @SerializedName("dependents")
    @Expose
    val dependents: List<Dependent>?
) : Profile()