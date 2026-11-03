package com.bhavya.spendwise.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bhavya.spendwise.presentation.components.SpendWiseCard
import com.bhavya.spendwise.ui.theme.SpendWiseSpacing
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.compose.foundation.clickable
import com.bhavya.spendwise.presentation.components.SectionHeader
import com.bhavya.spendwise.presentation.components.formatRupees

private enum class TransactionType {
    EXPENSE,
    INCOME
}

private val expenseCategories = listOf(
    "Food & Drink",
    "Transport",
    "Shopping",
    "Education",
    "Entertainment",
    "Bills & Utilities",
    "Health",
    "Other"
)

private val incomeCategories = listOf(
    "Salary",
    "Allowance",
    "Freelance",
    "Investment",
    "Gift",
    "Other"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen() {
    var transactionType by remember {
        mutableStateOf(TransactionType.EXPENSE)
    }

    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    val today = LocalDate.now()

    var selectedDateMillis by remember {
        mutableStateOf(
            today.atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli()
        )
    }

    var showDatePicker by remember { mutableStateOf(false) }
    var showCategoryMenu by remember { mutableStateOf(false) }
    var validationMessage by remember { mutableStateOf<String?>(null) }
    var saveMessage by remember { mutableStateOf<String?>(null) }

    val categories = when (transactionType) {
        TransactionType.EXPENSE -> expenseCategories
        TransactionType.INCOME -> incomeCategories
    }

    val selectedDate = Instant.ofEpochMilli(selectedDateMillis)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()

    val formattedDate = selectedDate.format(
        DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Screen heading
        Column {
            Text(
                text = "New entry",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Record your income or expenses.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Transaction type selector
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Transaction type",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TransactionTypeButton(
                    text = "Expense",
                    selected = transactionType == TransactionType.EXPENSE,
                    selectedColor = MaterialTheme.colorScheme.error,
                    modifier = Modifier.weight(1f)
                ) {
                    transactionType = TransactionType.EXPENSE
                    selectedCategory = ""
                    validationMessage = null
                    saveMessage = null
                }

                TransactionTypeButton(
                    text = "Income",
                    selected = transactionType == TransactionType.INCOME,
                    selectedColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                ) {
                    transactionType = TransactionType.INCOME
                    selectedCategory = ""
                    validationMessage = null
                    saveMessage = null
                }
            }
        }

        // Amount input
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Amount",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it.filter { character ->
                        character.isDigit() || character == '.'
                    }
                    validationMessage = null
                    saveMessage = null
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("0.00") },
                leadingIcon = {
                    Text(
                        text = "₹",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
            )
        }

        // Category selector
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Category",
                style = MaterialTheme.typography.titleMedium
            )

            Box {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    placeholder = { Text("Select a category") },
                    trailingIcon = {
                        Text("▾")
                    }
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable {
                            showCategoryMenu = true
                        }
                )

                DropdownMenu(
                    expanded = showCategoryMenu,
                    onDismissRequest = {
                        showCategoryMenu = false
                    },
                    modifier = Modifier.fillMaxWidth(0.85f)
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = category
                                showCategoryMenu = false
                                validationMessage = null
                                saveMessage = null
                            }
                        )
                    }
                }
            }
        }

        // Optional description
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Description (optional)",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                    saveMessage = null
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("What was this transaction for?") },
                minLines = 2,
                maxLines = 4
            )
        }

        // Date selector
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Date",
                style = MaterialTheme.typography.titleMedium
            )

            SpendWiseCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = { showDatePicker = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "▦   $formattedDate",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Validation and save feedback
        if (validationMessage != null) {
            Text(
                text = validationMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (saveMessage != null) {
            Text(
                text = saveMessage!!,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Save action
        Button(
            onClick = {
                val parsedAmount = try {
                    BigDecimal(amount.trim())
                } catch (_: NumberFormatException) {
                    null
                }

                val validAmount = parsedAmount != null &&
                    parsedAmount > BigDecimal.ZERO &&
                    parsedAmount.scale().coerceAtLeast(0) <= 2 &&
                    parsedAmount.multiply(BigDecimal(100))
                        .stripTrailingZeros().scale() <= 0

                when {
                    amount.isBlank() -> {
                        validationMessage = "Please enter an amount."
                        saveMessage = null
                    }

                    !validAmount -> {
                        validationMessage =
                            "Enter a valid amount with up to 2 decimal places."
                        saveMessage = null
                    }

                    selectedCategory.isBlank() -> {
                        validationMessage = "Please select a category."
                        saveMessage = null
                    }

                    else -> {
                        validationMessage = null
                        saveMessage =
                            "Form validated. Transaction storage will be connected in the data layer."
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(SpendWiseSpacing.medium)
        ) {
            Text(
                text = "Save transaction",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
    }

    // Date picker dialog
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDateMillis
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            selectedDateMillis = it
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun TransactionTypeButton(
    text: String,
    selected: Boolean,
    selectedColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val containerColor = if (selected) {
        selectedColor
    } else {
        MaterialTheme.colorScheme.surface
    }

    val contentColor = if (selected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    androidx.compose.material3.Surface(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(12.dp),
        color = containerColor,
        contentColor = contentColor,
        border = if (selected) null else
            androidx.compose.foundation.BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outline
            )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun HomeScreen() {
    val greeting = when (java.time.LocalTime.now().hour) {
        in 5..11 -> "Good morning"
        in 12..16 -> "Good afternoon"
        in 17..20 -> "Good evening"
        else -> "Hello"
    }

    val currentDate = LocalDate.now().format(
        DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.ENGLISH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = greeting,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Your money, in one place.",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = currentDate,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        SpendWiseCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Available balance",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = formatRupees(0L),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Your balance will update as you add transactions.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "This month")

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SummaryCard(
                title = "Income",
                amountPaise = 0L,
                modifier = Modifier.weight(1f),
                amountColor = MaterialTheme.colorScheme.primary
            )

            SummaryCard(
                title = "Expenses",
                amountPaise = 0L,
                modifier = Modifier.weight(1f),
                amountColor = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Monthly budget")

        Spacer(modifier = Modifier.height(12.dp))

        SpendWiseCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "No budget set up yet",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Create a budget to track your spending and see how much you have left.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Recent transactions")

        Spacer(modifier = Modifier.height(12.dp))

        SpendWiseCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No transactions yet",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Your recent income and expenses will appear here.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun SummaryCard(
    title: String,
    amountPaise: Long,
    modifier: Modifier = Modifier,
    amountColor: Color
) {
    SpendWiseCard(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = formatRupees(amountPaise),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = amountColor
            )
        }
    }
}

@Composable
fun ActivityScreen() {
    PlaceholderScreen(
        title = "Activity",
        description = "Your income and expense history will appear here."
    )
}

@Composable
fun InsightsScreen() {
    PlaceholderScreen(
        title = "Insights",
        description = "Your spending analytics will appear here."
    )
}

@Composable
fun BudgetScreen() {
    PlaceholderScreen(
        title = "Budget",
        description = "Your category budgets will appear here."
    )
}

@Composable
private fun PlaceholderScreen(
    title: String,
    description: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
