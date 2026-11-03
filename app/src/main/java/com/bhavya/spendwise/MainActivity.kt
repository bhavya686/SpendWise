package com.bhavya.spendwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bhavya.spendwise.presentation.navigation.Screen
import com.bhavya.spendwise.presentation.navigation.SpendWiseBottomBar
import com.bhavya.spendwise.presentation.navigation.SpendWiseNavGraph
import com.bhavya.spendwise.ui.theme.SpendWiseTheme
import androidx.compose.foundation.layout.padding

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SpendWiseTheme {
                SpendWiseApp()
            }
        }
    }
}

@Composable
fun SpendWiseApp() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            SpendWiseBottomBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Screen.HOME) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        SpendWiseNavGraph(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .then(Modifier)
                .padding(innerPadding)
        )
    }
}
