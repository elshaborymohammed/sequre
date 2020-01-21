package com.ocs.sequre.presentation.ui.fragment.landing

import androidx.navigation.fragment.findNavController
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment

class SplashFragment : BaseFragment() {

    override fun layoutRes(): Int {
        return R.layout.fragment_splash
    }

    override fun onResume() {
        super.onResume()

//        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//        val calendar = Calendar.getInstance(Locale.getDefault())
//        val currentTime = calendar.time
//        val dateString = currentTime.time
//
//        Log.i(
//            "Date",
//            "calendar ${calendar.timeInMillis} - current date: $currentTime - epoch time: ${calendar.timeInMillis / 1000} - date: ${Date(1576599516050)} "
//        )
        Thread(
            Runnable {
                Thread.sleep(2000)
                activity?.runOnUiThread {
                    findNavController().navigate(R.id.action_splashFragment_to_auth_graph)
                }
            }
        ).start()
    }

}