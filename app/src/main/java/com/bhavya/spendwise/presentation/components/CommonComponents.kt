package com.bhavya.spendwise.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.Locale

import com.bhavya.spendwise.ui.theme.ErrorRed
import com.bhavya.spendwise.ui.theme.SpendWiseCorners
import com.bhavya.spendwise.ui.theme.SpendWiseSpacing
import com.bhavya.spendwise.ui.theme.SuccessGreen

// Formats integer paise as Indian Rupees.
fun formatRupees(amountPaise: Long): String {
    val formatter = NumberFormat.getCurrencyInstance(
        Locale.Builder()
            .setLanguage("en")
            .setRegion("IN")
            .build()
    )
    return formatter.format(amountPaise / 100.0)
}

// Primary action button.
@Composable
fun SpendWiseButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(SpendWiseCorners.medium),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = text)
    }
}

// Outlined secondary action button.
@Composable
fun SpendWiseOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(SpendWiseCorners.medium)
    ) {
        Text(text = text)
    }
}

// Reusable content card.
@Composable
fun SpendWiseCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(SpendWiseCorners.large),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            content()
        }
    }
}

// Section title with an optional action.
@Composable
fun SectionHeader(
    title: String,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )

        if (actionText != null && onActionClick != null) {
            androidx.compose.material3.TextButton(
                onClick = onActionClick
            ) {
                Text(text = actionText)
            }
        }
    }
}

// Displays a monetary amount.
@Composable
fun AmountText(
    amountPaise: Long,
    modifier: Modifier = Modifier,
    isIncome: Boolean? = null
) {
    val amountColor = when (isIncome) {
        true -> SuccessGreen
        false -> ErrorRed
        null -> MaterialTheme.colorScheme.onSurface
    }

    Text(
        text = formatRupees(amountPaise),
        modifier = modifier,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = amountColor
    )
}

// Reusable transaction list item.
@Composable
fun TransactionRow(
    title: String,
    category: String,
    date: String,
    amountPaise: Long,
    isIncome: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SpendWiseCard(
            modifier = Modifier.size(44.dp)
        ) {
            androidx.compose.foundation.layout.Box(
                modifier = Modifier.size(44.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title.take(1).uppercase(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Spacer(modifier = Modifier.width(SpendWiseSpacing.standard))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "$category · $date",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.width(SpendWiseSpacing.small))

        AmountText(
            amountPaise = amountPaise,
            isIncome = isIncome
        )
    }
}
