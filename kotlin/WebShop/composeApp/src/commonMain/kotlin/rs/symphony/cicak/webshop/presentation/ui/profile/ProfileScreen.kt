package rs.symphony.cicak.webshop.presentation.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import rs.symphony.cicak.webshop.data.repository.AppModel
import rs.symphony.cicak.webshop.presentation.components.Title
import rs.symphony.cicak.webshop.presentation.ui.main.BlueDark2
import rs.symphony.cicak.webshop.presentation.ui.main.Cyan3
import rs.symphony.cicak.webshop.presentation.ui.main.PinkNeon
import rs.symphony.cicak.webshop.presentation.ui.main.PurpleDark
import rs.symphony.cicak.webshop.presentation.ui.main.Transparent
import rs.symphony.cicak.webshop.presentation.util.getPlatformPadding

@Composable
fun ProfileScreen() {
    val appModel: AppModel = koinInject()
    val user = appModel.user.collectAsState()

    Scaffold(
        backgroundColor = Transparent,
        topBar = {
            Title(
                modifier = Modifier.padding(16.dp, top = 16.dp + getPlatformPadding()),
                text = "Profile",
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
                    title = "${user.value?.name}\n${user.value?.email}"
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
                ) {
                    CoroutineScope(Dispatchers.IO).launch {
                        Firebase.auth.signOut()
                        appModel.setUser(null)
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        modifier = Modifier.padding(bottom = 8.dp),
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = Cyan3
    )
}

@Composable
private fun ProfileRow(
    icon: ImageVector,
    label: String,
    isLogout: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(BlueDark2, PurpleDark)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(
                onClick = {
                    onClick()
                },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 16.dp, end = 8.dp)
                .size(24.dp)
                .shadow(8.dp, shape = CircleShape)
                .background(Color.Transparent),
            imageVector = icon,
            tint = Cyan3,
            contentDescription = null
        )
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = if (isLogout) FontWeight.Bold else FontWeight.Normal,
            color = if (isLogout) PinkNeon else Cyan3
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier.padding(end = 16.dp),
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            tint = PinkNeon,
            contentDescription = null
        )
    }
}
