package com.bhavya.spendwise.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bhavya.spendwise.presentation.screens.ActivityScreen
import com.bhavya.spendwise.presentation.screens.AddTransactionScreen
import com.bhavya.spendwise.presentation.screens.BudgetScreen
import com.bhavya.spendwise.presentation.screens.HomeScreen
import com.bhavya.spendwise.presentation.screens.InsightsScreen

@Composable
fun SpendWiseNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HOME,
        modifier = modifier
    ) {
        composable(Screen.HOME) {
            HomeScreen()
        }

        composable(Screen.ACTIVITY) {
            ActivityScreen()
        }

        composable(Screen.ADD_TRANSACTION) {
            AddTransactionScreen()
        }

        composable(Screen.INSIGHTS) {
            InsightsScreen()
        }

        composable(Screen.BUDGET) {
            BudgetScreen()
        }
    }
}
