package rs.symphony.cicak.webshop.presentation.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Profile",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Section 1: Account Settings
            item {
                SectionTitle(
                    //icon = Icons.,
                    title = "Account Settings"
                )
            }
            item {
                ProfileRow(icon = Icons.Default.Language, label = "Language")
            }
            item {
                ProfileRow(icon = Icons.Default.Person, label = "Your Info")
            }
            item {
                ProfileRow(icon = Icons.Default.AttachMoney, label = "Default Currency [RSD]")
            }

            // Divider between sections
            item {
                Divider(modifier = Modifier.padding(vertical = 16.dp))
            }

            // Section 2: Orders & Payments
            item {
                SectionTitle(title = "Orders & Payments")
            }
            item {
                ProfileRow(icon = Icons.Default.Description, label = "Your Orders")
            }
            item {
                ProfileRow(icon = Icons.Default.CreditCard, label = "Payment Cards")
            }
            item {
                ProfileRow(icon = Icons.Default.LocalShipping, label = "Addresses")
            }

            // Divider between sections
            item {
                Divider(modifier = Modifier.padding(vertical = 16.dp))
            }

            // Section 3: Other
            item {
                ProfileRow(
                    icon = Icons.AutoMirrored.Filled.Logout,
                    label = "Logout",
                    isLogout = true
                )
            }
        }
    }

}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun ProfileRow(icon: ImageVector, label: String, isLogout: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier.padding(start = 16.dp, end = 8.dp),
            imageVector = icon,
            contentDescription = null
        )
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = if (isLogout) FontWeight.Bold else FontWeight.Normal,
            color = if (isLogout) MaterialTheme.colors.error else MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier.padding(end = 16.dp),
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null
        )
    }
}
