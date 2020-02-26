package com.ocs.sequre.app.helper

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Type

/**
 * this helper contains all mock data paths and method which convert string into objects
 */
object MockDataPathHelper {
    const val MOCK_DATA_PATH_LOGIN_SUCCESS = "auth/login/success.json"
    const val MOCK_DATA_PATH_LOGIN_ERROR = "auth/login/error.json"

    const val MOCK_DATA_PATH_REGISTER_SUCCESS = "auth/register/success.json"
    const val MOCK_DATA_PATH_REGISTER_ERROR = "auth/register/error.json"
}
