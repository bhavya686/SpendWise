package com.bhavya.spendwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bhavya.spendwise.presentation.components.AmountText
import com.bhavya.spendwise.presentation.components.SectionHeader
import com.bhavya.spendwise.presentation.components.SpendWiseButton
import com.bhavya.spendwise.presentation.components.SpendWiseCard
import com.bhavya.spendwise.presentation.components.SpendWiseOutlinedButton
import com.bhavya.spendwise.presentation.components.TransactionRow
import com.bhavya.spendwise.ui.theme.SpendWiseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SpendWiseTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    ComponentPreviewScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ComponentPreviewScreen(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "SpendWise",
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = "UI component preview",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            SpendWiseCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Available balance",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    AmountText(amountPaise = 0L)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Your balance will appear here.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            SectionHeader(
                title = "Recent activity",
                actionText = "See all",
                onActionClick = {}
            )

            TransactionRow(
                title = "Example expense",
                category = "Food",
                date = "Today",
                amountPaise = 12550L,
                isIncome = false
            )

            TransactionRow(
                title = "Example income",
                category = "Salary",
                date = "Today",
                amountPaise = 500000L,
                isIncome = true
            )

            SpendWiseButton(
                text = "Primary button",
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )

            SpendWiseOutlinedButton(
                text = "Secondary button",
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
