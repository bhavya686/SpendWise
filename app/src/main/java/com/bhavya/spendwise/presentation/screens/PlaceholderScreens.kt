package com.bhavya.spendwise.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    PlaceholderScreen(
        title = "Home",
        description = "Your financial overview will appear here."
    )
}

@Composable
fun ActivityScreen() {
    PlaceholderScreen(
        title = "Activity",
        description = "Your income and expense history will appear here."
    )
}

@Composable
fun AddTransactionScreen() {
    PlaceholderScreen(
        title = "Add Transaction",
        description = "You will be able to record income and expenses here."
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
