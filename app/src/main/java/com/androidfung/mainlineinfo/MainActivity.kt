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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.androidfung.mainlineinfo.ui.theme.MainlineInfoTheme


class MainActivity : ComponentActivity() {

    private val packages = listOf("com.google.android.art")


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainlineInfoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text("Mainline Info")
                            }
                        )
                    },
                ) { innerPadding ->
                    packages.forEach {
                        val versionCode = getMainlinePackageVersion(this.baseContext, it)
                        val versionText =
                            if (versionCode > 0) versionCode.toString()
                            else "N/A"

                        Text("$it : $versionText", Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

fun getMainlinePackageVersion(context: Context, packageName: String): Long {

    val result = StringBuilder()

    var versionCode: Long = 0


    if (Build.VERSION.SDK_INT >= 31) {
        try {
            versionCode = context.packageManager
                .getPackageInfo(packageName, PackageManager.MATCH_APEX)
                .longVersionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }
    return versionCode
}


fun getBaseArtVersion(context: Context): Long {
    var artVersionCode: Long = 0

    if (Build.VERSION.SDK_INT >= 31) {
        try {
            artVersionCode = context.packageManager
                .getPackageInfo("com.android.art", PackageManager.MATCH_APEX)
                .longVersionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    return artVersionCode
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