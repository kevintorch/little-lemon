package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun ProfileScreen(navigation: NavHostController, modifier: Modifier = Modifier) {
    ProfileScreenContent(
        modifier,
        firstName = LittleLemonPreferences.getUserFirstName(),
        lastName = LittleLemonPreferences.getUserLastName(),
        email = LittleLemonPreferences.getUserEmail(),
        onLogOut = {
            LittleLemonPreferences.clearUserData()
            navigation.navigate(Onboarding.route)
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    firstName: String = "",
    lastName: String = "",
    email: String = "",
    onLogOut: () -> Unit = {}
) {
    Scaffold(modifier = modifier, topBar = {
        LittleLemonTopAppBar()
    }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(top = 50.dp)) {
                Text("Personal Information", style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.padding(20.dp))
                Text("First Name", style = MaterialTheme.typography.labelSmall)
                Text(
                    firstName,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text("Last Name", style = MaterialTheme.typography.labelSmall)
                Text(
                    lastName,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text("Email", style = MaterialTheme.typography.labelSmall)
                Text(
                    email,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            ElevatedButton(
                onClick = onLogOut,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = contentColorFor(MaterialTheme.colorScheme.secondaryContainer)
                )
            ) {
                Text("Log Out")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    LittleLemonTheme {
        ProfileScreenContent(
            firstName = "Karan",
            lastName = "Kumar",
            email = "karan.kumar@gmail.com"
        )
    }
}