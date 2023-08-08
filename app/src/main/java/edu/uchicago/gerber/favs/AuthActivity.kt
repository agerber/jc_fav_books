package edu.uchicago.gerber.favs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.SignInUIOptions
import com.amazonaws.mobile.client.UserState
import com.amazonaws.mobile.client.UserStateDetails


class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val TAG = AuthActivity::class.java.simpleName

        AWSMobileClient.getInstance()
            .initialize(applicationContext, object : Callback<UserStateDetails> {
                override fun onResult(userStateDetails: UserStateDetails) {
                    Log.d(TAG, userStateDetails.userState.toString())
                    when (userStateDetails.userState) {
                        UserState.SIGNED_IN -> {
                            val i = Intent(this@AuthActivity, MainActivity::class.java)
                            startActivity(i)
                        }

                        UserState.SIGNED_OUT -> showSignIn()
                        else -> {
                            AWSMobileClient.getInstance().signOut()
                            showSignIn()
                        }
                    }
                }

                override fun onError(e: Exception) {
                    Log.e(TAG, e.toString())
                }
            })

    }//end onCreate

    private fun showSignIn() {
        try {
            AWSMobileClient.getInstance().showSignIn(
                this,
                SignInUIOptions.builder().nextActivity(MainActivity::class.java).build()
            )
        } catch (e: Exception) {
            Log.e("AuthActivity", e.toString())
        }
    }

}//end AuthActivity

