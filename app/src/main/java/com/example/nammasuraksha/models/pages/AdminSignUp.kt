package com.example.nammasuraksha.models.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun AdminSignUpPage(modifier: Modifier = Modifier) {
    var department by remember { mutableStateOf("") }
    var uniqueId by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val departments = listOf("Police", "Government Official", "Traffic Control", "Transport Department")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Admin Sign Up",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

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
            label = { Text("Email Address") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Select Department:")
        DepartmentDropdown(departments) { selected ->
            department = selected
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uniqueId,
            onValueChange = { uniqueId = it },
            label = { Text("Enter Unique ID (Badge/Employee No.)") },
            placeholder = { Text("If none, auto-generated") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val finalId = if (uniqueId.isBlank()) {
                    generateAutoId(department)
                } else {
                    uniqueId
                }
                // Handle signup logic with finalId
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Admin Account")
        }
    }
}

@Composable
fun DepartmentDropdown(items: List<String>, onItemSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Box {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            label = { Text("Department") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                }
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selectedText = item
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

// If uniqueId is not provided, generate something smart
fun generateAutoId(department: String): String {
    val prefix = when (department) {
        "Police" -> "POL"
        "Government Official" -> "GOV"
        "Traffic Control" -> "TRA"
        "Transport Department" -> "TRD"
        else -> "ADM"
    }
    val randomNum = (1000..9999).random()
    return "$prefix-$randomNum"
}
