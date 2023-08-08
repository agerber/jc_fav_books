package edu.uchicago.gerber.favs.presentation.widgets

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.amazonaws.mobile.client.AWSMobileClient
import edu.uchicago.gerber.favs.AuthActivity

import edu.uchicago.gerber.favs.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(titleText: String) {
      val context = LocalContext.current
      TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent
        ),
        title = {
            Text(
                text = titleText,
                modifier = Modifier
                    .fillMaxWidth(),
                style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )

        },
        actions = {
            // RowScope here, so these icons will be placed horizontally
            IconButton(
                onClick = {
//                    AWSMobileClient.getInstance().signOut()
//                    //force user back through the Auth
//                    val intent = Intent(context, AuthActivity::class.java)
//                    startActivity(context, intent, null)

                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = "log-out"
                )
            }
        }
    )

}