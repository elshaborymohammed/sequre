package com.ocs.sequre.data.remote.model.response.error

enum class ErrorStatus {
    VALIDATION(0), ERROR(1);

    private var type = 0

    constructor(type: Int) {
        this.type = type
    }

    fun type(): Int {
        return type
    }
}