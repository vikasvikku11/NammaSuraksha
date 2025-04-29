package com.example.nammasuraksha.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nammasuraksha.RolePage
import com.example.nammasuraksha.models.pages.AdminLogin
import com.example.nammasuraksha.models.pages.AdminSignUpPage
import com.example.nammasuraksha.models.pages.HomePages.HomePage
import com.example.nammasuraksha.models.pages.UserLoginPage
import com.example.nammasuraksha.models.pages.UserSignUp

@Composable

fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ROUTES.ROLEPAGE.name) {
        composable(ROUTES.ROLEPAGE.name) { RolePage(navController) }
        composable(ROUTES.USERLOGIN.name) {
            UserLoginPage(navController)
        }
        composable(ROUTES.ADMINLOGIN.name) {
            AdminLogin(navController)
        }

        composable(ROUTES.USERSIGNUP.name) {
            UserSignUp()
        }
        composable(ROUTES.ADMINSIGNUP.name) {
            AdminSignUpPage(navController)
        }

        composable(ROUTES.ADMINHOMEPAGE.name) {
            HomePage(navController)
        }

        composable("uploadData") { UploadDataScreen(navController) }
        composable("hotspotMap") { HotspotMapScreen(navController) }
        composable("predictionScreen") { PredictionScreen(navController) }
        composable("statsDashboard") { StatisticsDashboard(navController) }
        composable("userReports") { UserReportsScreen(navController) }
        composable("login") { LoginScreen(navController) }

    }
}
