package com.ocs.sequre.app.base

import java.util.regex.Pattern

object Patterns {
    val NAME: Pattern = Pattern.compile("^[a-zA-Z]+$")
    val PHONE: Pattern = Pattern.compile("^[0-9]{10}$")
}