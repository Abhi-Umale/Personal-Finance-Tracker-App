package com.example.personalfinancerackerapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routs{

    @Serializable
    object SignupAndLoginScreen: Routs()

}
