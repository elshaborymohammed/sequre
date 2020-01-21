package com.ocs.sequre.app.helper

import android.app.Activity
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.lang.Exception
import java.util.concurrent.TimeUnit

object PhoneAuthHelper {

    const val Tag = "PhoneAuthHelper"
    var verificationId: String = ""

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        mAuth.setLanguageCode("en")
    }

    fun setupVerifyPhoneNumber(countryCode: String, phoneNumber: String, activity: Activity) {
        setupVerifyPhoneNumber(countryCode + phoneNumber, activity)
    }

    private fun setupVerifyPhoneNumber(phoneNumber: String, activity: Activity) {
        PhoneAuthProvider.getInstance(mAuth)
            .verifyPhoneNumber(
                phoneNumber,
                60L,
                TimeUnit.SECONDS,
                activity,
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        Log.i(Tag, "Completed credential = [${credential.smsCode}]")
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        Log.e(Tag, "Failed e = [${e}]")
                        if (e is FirebaseAuthInvalidCredentialsException) {
                            when (e.errorCode) {
                                "ERROR_INVALID_PHONE_NUMBER" -> Log.e(
                                    Tag,
                                    "ERROR_INVALID_PHONE_NUMBER: ${e.message}]"
                                )
                            }

                        }
                    }

                    override fun onCodeAutoRetrievalTimeOut(verificationId: String) {
                        super.onCodeAutoRetrievalTimeOut(verificationId)
                        Log.w(Tag, "TimeOut verificationId = [${verificationId}]")
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        super.onCodeSent(verificationId, token)
                        this@PhoneAuthHelper.verificationId = verificationId
                        Log.i(
                            Tag,
                            "Code Sent verificationId = [${verificationId}], token = [${token}]"
                        )
                    }

                })

    }

    private fun setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber: String, code: String) {
        val firebaseAuthSettings = mAuth.firebaseAuthSettings
        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, code)
    }

    fun setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber: String, vararg code: CharSequence) {
        setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, code.toString())
    }

    fun verifyPhoneNumberWithCode(
        verificationId: String,
        activity: Activity,
        code: CharSequence,
        onSuccess: () -> Unit,
        onFailed: (exception: Exception) -> Unit
    ) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code.toString())
        signInWithPhoneAuthCredential(credential, activity, onSuccess, onFailed)
    }

//    fun verifyPhoneNumberWithCode(
//        verificationId: String,
//        activity: Activity,
//        vararg code: CharSequence
//    ) {
//        verifyPhoneNumberWithCode(verificationId, activity, code.joinToString(separator = ""))
//    }

    private fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        activity: Activity,
        onSuccess: () -> Unit,
        onFailed: (exception: Exception) -> Unit
    ) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                    onSuccess()
                } else { // Sign in failed, display a message and update the UI
                    onFailed(task.exception!!)
                }
            }
    }

    fun signOut() {
        mAuth.signOut()
    }
}