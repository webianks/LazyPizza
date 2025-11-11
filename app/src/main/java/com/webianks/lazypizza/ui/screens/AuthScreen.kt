package com.webianks.lazypizza.ui.screens

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.webianks.lazypizza.data.countryCallingCodes
import com.webianks.lazypizza.ui.components.PrimaryGradientButton
import com.webianks.lazypizza.ui.theme.AppTextStyles
import com.webianks.lazypizza.ui.theme.LazyPizzaTheme
import com.webianks.lazypizza.ui.theme.Primary

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val rawText = text.text
        if (rawText.isEmpty() || !rawText.startsWith("+")) {
            return TransformedText(text, OffsetMapping.Identity)
        }

        val digits = rawText.substring(1)
        val countryCode =
            findCountryCode(digits) ?: return TransformedText(text, OffsetMapping.Identity)

        val nationalNumber = digits.substring(countryCode.toString().length)
        val formattedNationalNumber = nationalNumber.chunked(4).joinToString(" ")
        val formattedText = "+$countryCode $formattedNationalNumber"

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val countryCodeLength = countryCode.toString().length
                if (offset <= countryCodeLength) {
                    return offset
                } else {
                    val nationalNumberOffset = offset - countryCodeLength
                    val spaces = (nationalNumberOffset - 1).coerceAtLeast(0) / 4
                    return (offset + 1 + spaces).coerceAtMost(formattedText.length)
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                val countryCodeLength = countryCode.toString().length
                if (offset <= countryCodeLength + 1) {
                    return offset
                } else {
                    val nationalNumberOffset = offset - (countryCodeLength + 1)
                    val spaces = (nationalNumberOffset - 1).coerceAtLeast(0) / 5
                    return (offset - 1 - spaces).coerceAtLeast(0)
                }
            }
        }

        return TransformedText(AnnotatedString(formattedText), offsetMapping)
    }

    private fun findCountryCode(digits: String): Int? {
        return (3 downTo 1).asSequence()
            .mapNotNull { len ->
                digits.takeIf { it.length >= len }?.substring(0, len)?.toIntOrNull()
            }
            .firstOrNull { it in countryCallingCodes }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel,
    windowSizeClass: WindowSizeClass
) {
    val authState by viewModel.authState.collectAsState()
    val timerValue by viewModel.timerValue.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val activity = LocalActivity.current

    val isExpandedScreen = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded

    var uiState by remember { mutableStateOf<AuthState>(AuthState.PhoneNumberInput) }

    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthState.AuthError -> {
                snackBarHostState.showSnackbar(
                    message = state.message,
                    duration = SnackbarDuration.Short
                )
                viewModel.goBackToPhoneInput()
            }

            is AuthState.SignedIn -> {
                navController.popBackStack()
            }

            is AuthState.PhoneNumberInput, is AuthState.CodeInput -> {
                uiState = state
            }

            AuthState.Loading -> {
                // Do nothing, keep the current uiState to show loading overlay
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState) {
                Snackbar(
                    snackbarData = it,
                    containerColor = Primary,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            val authFlowModifier = if (isExpandedScreen) {
                Modifier.widthIn(max = 400.dp)
            } else {
                Modifier.fillMaxWidth()
            }

            AuthFlow(
                modifier = authFlowModifier,
                state = uiState,
                timerValue = timerValue,
                onPhoneNumberEntered = { phoneNumber ->
                    activity?.let {
                        viewModel.sendOtp(
                            phoneNumber,
                            it
                        )
                    }
                },
                onOtpEntered = { otp -> viewModel.verifyOtp(otp) },
                onResendCode = { phoneNumber ->
                    activity?.let {
                        viewModel.resendCode(
                            phoneNumber,
                            it
                        )
                    }
                },
                onContinueWithoutSigningIn = { navController.popBackStack() },
            )

            if (authState is AuthState.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable(enabled = false, onClick = {}),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingIndicator()
                }
            }
        }
    }
}

@Composable
fun AuthFlow(
    modifier: Modifier = Modifier,
    state: AuthState,
    timerValue: Int,
    onPhoneNumberEntered: (String) -> Unit,
    onOtpEntered: (String) -> Unit,
    onResendCode: (String) -> Unit,
    onContinueWithoutSigningIn: () -> Unit
) {
    val isPhoneInputState = state is AuthState.PhoneNumberInput

    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isContinueEnabled by remember { mutableStateOf(false) }

    val otp = remember { mutableStateListOf("", "", "", "", "", "") }
    val focusRequesters = remember { (0 until 6).map { FocusRequester() } }

    val isPhoneNumberPotentiallyValid = { number: String ->
        if (!number.startsWith("+")) {
            false
        } else {
            val digits = number.substring(1)
            val countryCode = (3 downTo 1).asSequence()
                .mapNotNull { len ->
                    digits.takeIf { it.length >= len }?.substring(0, len)?.toIntOrNull()
                }
                .firstOrNull { it in countryCallingCodes }

            if (countryCode == null) {
                false
            } else {
                val nationalNumber = digits.substring(countryCode.toString().length)
                nationalNumber.length in 7..12
            }
        }
    }

    val validatePhoneNumber = { number: String ->
        if (isPhoneNumberPotentiallyValid(number)) {
            isError = false
            errorMessage = ""
        } else {
            isError = true
            if (!number.startsWith("+")) {
                errorMessage = "Phone number must start with '+' (country code)."
            } else {
                val digits = number.substring(1)
                val countryCode = (3 downTo 1).asSequence()
                    .mapNotNull { len ->
                        digits.takeIf { it.length >= len }?.substring(0, len)?.toIntOrNull()
                    }
                    .firstOrNull { it in countryCallingCodes }
                if (countryCode == null) {
                    errorMessage = "Invalid country code."
                } else {
                    errorMessage = "Please enter a valid number of digits (7-12)."
                }
            }
        }
    }

    Column(
        modifier = modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 184.dp,
                bottom = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Welcome to LazyPizza",
            style = AppTextStyles.Title1SemiBold.copy(fontWeight = FontWeight.Medium),
            color = colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = if (isPhoneInputState) "Enter your phone number" else "Enter code",
            style = AppTextStyles.Body3Regular,
            color = colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(20.dp))

        if (isPhoneInputState) {
            BasicTextField(
                value = phoneNumber,
                onValueChange = {
                    val newText = it.text
                    if (newText.length > 20) return@BasicTextField
                    val filtered = if (newText.startsWith("+")) {
                        "+" + newText.substring(1).filter { c -> c.isDigit() }
                    } else {
                        newText.filter { c -> c.isDigit() }
                    }
                    phoneNumber = it.copy(text = filtered)

                    if (isError) {
                        isError = false
                    }
                    isContinueEnabled = isPhoneNumberPotentiallyValid(filtered)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = AppTextStyles.Body1Regular.copy(
                    fontSize = 15.sp,
                    color = colorScheme.onSurface
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                visualTransformation = PhoneNumberVisualTransformation(),
                cursorBrush = SolidColor(colorScheme.primary),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .background(
                                color = colorScheme.inverseSurface,
                                shape = CircleShape
                            )
                            .border(
                                width = 1.dp,
                                color = if (isError) colorScheme.error else Color.Transparent,
                                shape = CircleShape
                            )
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        innerTextField()
                        if (phoneNumber.text.isEmpty()) {
                            Text(
                                text = "+XX XXX XXX XXXX",
                                style = AppTextStyles.Body1Regular.copy(fontSize = 15.sp),
                                color = colorScheme.secondary
                            )
                        }
                    }
                }
            )
            if (isError) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = errorMessage,
                    color = colorScheme.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        } else if (state is AuthState.CodeInput) {
            BasicTextField(
                value = state.phoneNumber,
                onValueChange = { /* Do nothing as it's read-only */ },
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = AppTextStyles.Body1Regular.copy(
                    fontSize = 15.sp,
                    color = colorScheme.onSurface
                ),
                readOnly = true,
                singleLine = true,
                visualTransformation = PhoneNumberVisualTransformation(),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .background(
                                color = colorScheme.inverseSurface,
                                shape = CircleShape
                            )
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        innerTextField()
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 0 until 6) {
                    BasicTextField(
                        value = otp[i],
                        onValueChange = {
                            if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                                otp[i] = it
                                if (it.isNotEmpty() && i < 5) {
                                    focusRequesters[i + 1].requestFocus()
                                }
                            }
                        },
                        modifier = Modifier
                            .width(56.dp)
                            .height(48.dp)
                            .focusRequester(focusRequesters[i])
                            .onKeyEvent {
                                if (it.key == Key.Backspace && otp[i].isEmpty() && i > 0) {
                                    focusRequesters[i - 1].requestFocus()
                                    true
                                } else {
                                    false
                                }
                            }
                            .border(
                                width = 1.dp,
                                color = if (state.error) Primary else Color.Transparent,
                                shape = CircleShape
                            ),
                        textStyle = AppTextStyles.Body1Regular.copy(
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            color = colorScheme.onSurface
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        cursorBrush = SolidColor(colorScheme.primary),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        color = if (state.error) Color.Transparent else colorScheme.inverseSurface,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                innerTextField()
                                if (otp[i].isEmpty()) {
                                    Text(
                                        text = "0",
                                        style = AppTextStyles.Body1Regular.copy(
                                            fontSize = 15.sp,
                                            textAlign = TextAlign.Center
                                        ),
                                        color = colorScheme.secondary
                                    )
                                }
                            }
                        }
                    )
                    if (i < 5) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
            if (state.error) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Incorrect code. Please try again.",
                        color = Primary,
                        style = AppTextStyles.Body4Regular,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryGradientButton(
            buttonModifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            text = if (isPhoneInputState) "Continue" else "Confirm",
            enabled = if (isPhoneInputState) isContinueEnabled else otp.joinToString("").length == 6,
            onClick = {
                if (isPhoneInputState) {
                    validatePhoneNumber(phoneNumber.text)
                    if (!isError) {
                        onPhoneNumberEntered(phoneNumber.text)
                    }
                } else {
                    onOtpEntered(otp.joinToString(""))
                }
            },
        )
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            onClick = onContinueWithoutSigningIn
        ) {
            Text(
                text = "Continue without signing in",
                style = AppTextStyles.Title3,
                color = colorScheme.primary
            )
        }

        if (state is AuthState.CodeInput) {
            if (timerValue > 0) {
                Text(
                    text = "You can request a new code in 00:${
                        timerValue.toString().padStart(2, '0')
                    }",
                    style = AppTextStyles.Body3Regular,
                    color = colorScheme.secondary
                )
            } else {
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    onClick = { onResendCode(state.phoneNumber) }) {
                    Text(
                        text = "Resend code",
                        style = AppTextStyles.Title3,
                        color = colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Phone Number Input")
@Composable
fun AuthFlowPhoneNumberPreview() {
    LazyPizzaTheme {
        AuthFlow(
            state = AuthState.PhoneNumberInput,
            timerValue = 0,
            onPhoneNumberEntered = {},
            onOtpEntered = {},
            onResendCode = {},
            onContinueWithoutSigningIn = {}
        )
    }
}

@Preview(showBackground = true, name = "Code Input")
@Composable
fun AuthFlowCodeInputPreview() {
    LazyPizzaTheme {
        AuthFlow(
            state = AuthState.CodeInput("+91 1234 567 890", false),
            timerValue = 25,
            onPhoneNumberEntered = {},
            onOtpEntered = {},
            onResendCode = {},
            onContinueWithoutSigningIn = {}
        )
    }
}

@Preview(showBackground = true, name = "Code Input Error")
@Composable
fun AuthFlowCodeInputErrorPreview() {
    LazyPizzaTheme {
        AuthFlow(
            state = AuthState.CodeInput("+91 1234 567 890", true),
            timerValue = 0,
            onPhoneNumberEntered = {},
            onOtpEntered = {},
            onResendCode = {},
            onContinueWithoutSigningIn = {}
        )
    }
}
