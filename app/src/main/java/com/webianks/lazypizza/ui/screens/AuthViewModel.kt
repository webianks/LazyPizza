package com.webianks.lazypizza.ui.screens

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.webianks.lazypizza.data.repository.DataStoreCartRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

sealed class AuthState {
    object PhoneNumberInput : AuthState()
    object Loading : AuthState()
    data class CodeInput(val phoneNumber: String, val error: Boolean = false) : AuthState()
    data class AuthError(val message: String) : AuthState()
    object SignedIn : AuthState()
}

class AuthViewModel(
    private val firebaseAuth: FirebaseAuth,
    private val cartRepository: DataStoreCartRepository
) : ViewModel() {

    private val _authState =
        MutableStateFlow<AuthState>(AuthState.PhoneNumberInput)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _timerValue = MutableStateFlow(60)
    val timerValue: StateFlow<Int> = _timerValue.asStateFlow()

    private var timerJob: Job? = null
    private var verificationId: String? = null
    private var resendingToken: PhoneAuthProvider.ForceResendingToken? = null

    init {
        if (firebaseAuth.currentUser != null) {
            _authState.value = AuthState.SignedIn
        }
    }

    private fun getVerificationCallbacks(phoneNumber: String) =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential, phoneNumber)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                _authState.value = AuthState.AuthError(e.message ?: "Verification failed")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                this@AuthViewModel.verificationId = verificationId
                this@AuthViewModel.resendingToken = token
                _authState.value = AuthState.CodeInput(phoneNumber)
                startTimer()
            }
        }

    fun sendOtp(phoneNumber: String, activity: Activity) {
        _authState.value = AuthState.Loading
        verifyPhoneNumber(activity, phoneNumber)
    }

    fun verifyOtp(otp: String) {
        val currentState = _authState.value
        if (currentState is AuthState.CodeInput) {
            verificationId?.let {
                _authState.value = AuthState.Loading
                val credential = PhoneAuthProvider.getCredential(it, otp)
                signInWithPhoneAuthCredential(credential, currentState.phoneNumber)
            }
        }
    }

    fun resendCode(phoneNumber: String, activity: Activity) {
        val currentState = _authState.value
        if (currentState is AuthState.CodeInput) {
            _authState.value = currentState.copy(error = false)
        }
        resendingToken?.let { token ->
            verifyPhoneNumber(activity, phoneNumber, token)
        } ?: run {
            _authState.value =
                AuthState.AuthError("Can't resend code, please try again from the beginning.")
        }
    }

    private fun verifyPhoneNumber(
        activity: Activity,
        phoneNumber: String,
        resendingToken: PhoneAuthProvider.ForceResendingToken? = null
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(getVerificationCallbacks(phoneNumber))

        resendingToken?.let {
            optionsBuilder.setForceResendingToken(it)
        }

        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

    fun goBackToPhoneInput() {
        _authState.value = AuthState.PhoneNumberInput
        timerJob?.cancel()
    }

    fun logout() {
        viewModelScope.launch {
            firebaseAuth.currentUser?.let { cartRepository.clearUserCart(it.uid) }
            firebaseAuth.signOut()
            _authState.value = AuthState.PhoneNumberInput
        }
    }

    private fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        phoneNumber: String
    ) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModelScope.launch {
                        task.result?.user?.let { cartRepository.transferGuestCartToUser(it.uid) }
                        _authState.value = AuthState.SignedIn
                    }
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        _authState.value = AuthState.CodeInput(phoneNumber, error = true)
                    } else {
                        _authState.value =
                            AuthState.AuthError(task.exception?.message ?: "Authentication failed")
                    }
                }
            }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            flow {
                for (i in 60 downTo 0) {
                    emit(i)
                    delay(1000)
                }
            }.collect {
                _timerValue.value = it
            }
        }
    }
}