package com.example.nammasuraksha.models.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun UserSignUp(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var aadhaarNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var otpSent by remember { mutableStateOf(false) }
    var otpInput by remember { mutableStateOf("") }
    var isOtpVerified by remember { mutableStateOf(false) }
    val correctOtp = "123456" // Simulate OTP backend (mocked)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("User Sign Up", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = aadhaarNumber,
            onValueChange = { aadhaarNumber = it.filter { it.isDigit() }.take(12) },
            label = { Text("Aadhaar Number") },
            placeholder = { Text("12-digit Aadhaar") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            enabled = !otpSent
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (!otpSent) {
            Button(
                onClick = {
                    if (aadhaarNumber.length == 12) {
                        otpSent = true
                        // Simulate sending OTP via backend
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = aadhaarNumber.length == 12
            ) {
                Text("Send OTP")
            }
        } else {
            OutlinedTextField(
                value = otpInput,
                onValueChange = { otpInput = it.take(6) },
                label = { Text("Enter OTP") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (otpInput == correctOtp) {
                        isOtpVerified = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = otpInput.length == 6
            ) {
                Text("Verify OTP")
            }

            if (isOtpVerified) {
                Text(
                    "OTP Verified ✅",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else if (otpInput.isNotEmpty() && otpInput != correctOtp) {
                Text(
                    "Incorrect OTP ❌",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                // Handle sign up
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isOtpVerified
        ) {
            Text("Create User Account")
        }
    }
}
