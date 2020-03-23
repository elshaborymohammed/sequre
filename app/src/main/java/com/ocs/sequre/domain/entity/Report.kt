package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Report(

    @field:SerializedName("patientName")
    @Expose
    val patientName: String? = null,

    @field:SerializedName("dosage")
    @Expose
    val dosage: String? = null,

    @field:SerializedName("doctor_name")
    @Expose
    val doctorName: String? = null,

    @field:SerializedName("diagnosis")
    @Expose
    val diagnosis: String? = null,

    @field:SerializedName("comment")
    @Expose
    val comment: String? = null,

    @field:SerializedName("start_date")
    @Expose
    val startDate: String? = null
)