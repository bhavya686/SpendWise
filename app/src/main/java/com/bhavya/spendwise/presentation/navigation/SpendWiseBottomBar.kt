package com.bhavya.spendwise.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private data class NavigationItem(
    val route: String,
    val label: String,
    val symbol: String
)

private val navigationItems = listOf(
    NavigationItem(Screen.HOME, "Home", "⌂"),
    NavigationItem(Screen.ACTIVITY, "Activity", "≡"),
    NavigationItem(Screen.INSIGHTS, "Insights", "◷"),
    NavigationItem(Screen.BUDGET, "Budget", "▣")
)

@Composable
fun SpendWiseBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        NavigationBarItem(
            selected = currentRoute == Screen.HOME,
            onClick = { onNavigate(Screen.HOME) },
            icon = {
                Text("⌂", style = MaterialTheme.typography.titleLarge)
            },
            label = { Text("Home") },
            colors = navigationItemColors()
        )

        NavigationBarItem(
            selected = currentRoute == Screen.ACTIVITY,
            onClick = { onNavigate(Screen.ACTIVITY) },
            icon = {
                Text("≡", style = MaterialTheme.typography.titleLarge)
            },
            label = { Text("Activity") },
            colors = navigationItemColors()
        )

        NavigationBarItem(
            selected = false,
            onClick = { onNavigate(Screen.ADD_TRANSACTION) },
            icon = {
                Surface(
                    modifier = Modifier.size(38.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "+",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            },
            label = { Text("Add") },
            colors = navigationItemColors()
        )

        NavigationBarItem(
            selected = currentRoute == Screen.INSIGHTS,
            onClick = { onNavigate(Screen.INSIGHTS) },
            icon = {
                Text("◷", style = MaterialTheme.typography.titleLarge)
            },
            label = { Text("Insights") },
            colors = navigationItemColors()
        )

        NavigationBarItem(
            selected = currentRoute == Screen.BUDGET,
            onClick = { onNavigate(Screen.BUDGET) },
            icon = {
                Text("▣", style = MaterialTheme.typography.titleLarge)
            },
            label = { Text("Budget") },
            colors = navigationItemColors()
        )
    }
}

@Composable
private fun navigationItemColors() =
    NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
