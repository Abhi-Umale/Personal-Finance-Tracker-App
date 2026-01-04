package com.example.personalfinancerackerapp.ui.signupandlogin

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.personalfinancerackerapp.auth.authviewmodel.AuthViewModel

@SuppressLint("InvalidColorHexValue")
@Composable
fun SignupAndLoginScreen(viewModel: AuthViewModel, navController: NavController) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isLogin by remember { mutableStateOf(true) }
    var isSelected by remember { mutableStateOf(true) }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f/3f)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF4DC9A9),
                        Color(0xFF30437A)
                    )
                )
            ),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 160.dp)
                .fillMaxWidth()
        ) {
            Text( "Hey !",
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                if (isLogin) "Welcome Back" else "Create New Account",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 270.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 70.dp, topEnd = 70.dp)
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = {
                    isLogin = true
                    isSelected = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .drawBehind {
                        if (isSelected) {
                            val y = size.height
                            drawLine(
                                color = Color(0xFF30437A),
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = 5.dp.toPx()
                            )
                        }
                    }
            ) {
                Text(
                    "Login",
                    fontSize = 24.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF30437A)
                )
            }

            Spacer(modifier = Modifier.width(30.dp))
            TextButton(
                onClick = {
                    isLogin = false
                    isSelected = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .drawBehind {
                        if (!isSelected) {
                            val y = size.height
                            drawLine(
                                color = Color(0xFF30437A),
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = 5.dp.toPx()
                            )
                        }
                    }
            ) {
                Text(
                    "Sign Up",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF30437A)
                )
            }
        }
        if (isLogin) {
            Spacer(modifier = Modifier.height(50.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Username
            if (!isLogin) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "UserName",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = userName,
                        onValueChange = { userName = it },
                        placeholder = { Text("UserName") },
                        leadingIcon = {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Username"
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                VerticalDivider(
                                    color = Color(0xFF30437A),
                                    thickness = 1.5.dp,
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        ),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.DarkGray,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedPlaceholderColor = Color(0xFF30437A),
                            unfocusedPlaceholderColor = Color.Black,
                            focusedLeadingIconColor = Color(0xFF30437A),
                            unfocusedLeadingIconColor = Color.Black,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .height(54.dp)
                            .border(
                                2.dp, color = Color(0xFF30437A),
                                shape = RoundedCornerShape(20.dp)
                            )
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            //Email
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "E-mail",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("E-mail") },
                    leadingIcon = {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email"
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            VerticalDivider(
                                color = Color(0xFF30437A),
                                thickness = 1.5.dp,
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.DarkGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedPlaceholderColor = Color(0xFF30437A),
                        unfocusedPlaceholderColor = Color.Black,
                        focusedLeadingIconColor = Color(0xFF30437A),
                        unfocusedLeadingIconColor = Color.Black,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(54.dp)
                        .border(
                            2.dp, color = Color(0xFF30437A),
                            shape = RoundedCornerShape(20.dp)
                        )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            //Password
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "PassWord",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password") },
                    leadingIcon = {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.BlurOn,
                                contentDescription = "Password"
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            VerticalDivider(
                                color = Color(0xFF30437A),
                                thickness = 1.5.dp,
                            )
                        }
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.DarkGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedPlaceholderColor = Color(0xFF30437A),
                        unfocusedPlaceholderColor = Color.Black,
                        focusedLeadingIconColor = Color(0xFF30437A),
                        unfocusedLeadingIconColor = Color.Black,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(54.dp)
                        .border(
                            2.dp, color = Color(0xFF30437A),
                            shape = RoundedCornerShape(20.dp)
                        )
                )
            }
        }
        if (isLogin) {
            Spacer(modifier = Modifier.height(60.dp))
        } else {
            Spacer(modifier = Modifier.height(70.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Login Button
            Button(
                onClick = {
                    if (isLogin) {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            viewModel.login(email, password) { success ->
                                if (success) {
                                    navController.navigate("dashboard")
                                } else {
                                    Toast.makeText(context, "Login Fail", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please Fill all fields", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                            viewModel.signup(email, password) { success ->
                                if (success) {
                                    navController.navigate("dashboard")
                                } else {
                                    Toast.makeText(context, "Sign Up Fail", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please Fill all fields", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4DC9A9),
                    contentColor = Color(0xFF30437A)
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    if (isLogin) "Login" else "Sign Up",
                    color = Color(0xFF30437A),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            if (isLogin) {
                TextButton(
                    onClick = {
                        isLogin = false
                        isSelected = false
                    }
                ) {
                    Text(
                        "Or sign up here",
                        color = Color(0xFF30437A),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            } else {
                TextButton(
                    onClick = {
                        isLogin = true
                        isSelected = true
                    }
                ) {
                    Text(
                        "Already have account: Login ",
                        color = Color(0xFF30437A),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}
