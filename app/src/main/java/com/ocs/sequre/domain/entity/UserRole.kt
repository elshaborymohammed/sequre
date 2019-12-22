package com.ocs.sequre.domain.entity

enum class UserRole {
    UNDEFINED(-1), CORPORATE(0), INDIVIDUAL(1);

    private var type = 0

    constructor(type: Int) {
        this.type = type
    }

    fun type(): Int {
        return type
    }
}