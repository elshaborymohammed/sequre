package com.ocs.sequre.app.helper

import android.app.ActivityManager
import android.app.Service
import android.content.Context

object AndroidComponentHelper {

    open fun isServiceRunning(context: Context): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (Service::class.qualifiedName == service.service.className) {
                return true
            }
        }
        return false
    }


    fun MutableList<Int>.swap(index1: Int, index2: Int) {
        val tmp = this[index1] // 'this' corresponds to the list
        this[index1] = this[index2]
        this[index2] = tmp
    }
}