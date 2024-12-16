package com.androidfung.mainlineinfo

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.androidfung.mainlineinfo.ui.theme.MainlineInfoTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainlineInfoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = getMainlineArtworkVersion(this.baseContext).toString(),
//                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


fun getMainlineArtworkVersion(context : Context) : Long {

    val result = StringBuilder()

    var artVersionCode: Long = 0


    if (Build.VERSION.SDK_INT >= 31) {
        try {
            artVersionCode = context.packageManager
                .getPackageInfo("com.google.android.art", PackageManager.MATCH_APEX)
                .longVersionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }


        try {
            if (artVersionCode == 0L) {
                artVersionCode = context.getPackageManager()
                    .getPackageInfo("com.android.art", PackageManager.MATCH_APEX)
                    .longVersionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }


    return artVersionCode
//    result.append("libart Version is : ").append(artVersionCode)

//    return


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainlineInfoTheme {
        Greeting("Android")
    }
}