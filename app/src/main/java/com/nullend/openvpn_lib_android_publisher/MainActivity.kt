package com.nullend.openvpn_lib_android_publisher

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.nullend.openvpn_lib_android_publisher.ui.theme.Openvpn_lib_android_publisherTheme
import de.blinkt.openvpn.LaunchVPN
import de.blinkt.openvpn.OpenVpnApi
import de.blinkt.openvpn.OpenVpnConnectionNetstatNotifier
import de.blinkt.openvpn.OpenVpnConnectionStateNotifier
import de.blinkt.openvpn.activities.DisconnectVPN


class MainActivity : ComponentActivity() {

    private val mOpenVpnApi: OpenVpnApi = OpenVpnApi()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityResultNotifier.addListener(::onActivityResult)
        OpenVpnConnectionStateNotifier.setListener { onVpnConnectionStateChanged(it) }
        OpenVpnConnectionNetstatNotifier.setListener { byteIn, byteOut ->  onVpnConnectionNetstatChanged(byteIn, byteOut)}
        setContent {
            Openvpn_lib_android_publisherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenCompose(::connect, ::disconnect)
                }
            }
        }
    }

    private fun onActivityResult(resultCode: Int) {
        if (resultCode == RESULT_OK) {
            connect()
        }
    }

    private fun connect() {
        val config = OvpnTestConfig.config.trim()
        val name = OvpnTestConfig.name
        val username = OvpnTestConfig.username
        val password = OvpnTestConfig.password
        val profile = mOpenVpnApi.createVpnProfile(this, config, name, username, password)
        val intent = Intent(this, LaunchVPN::class.java)
        intent.putExtra(LaunchVPN.EXTRA_KEY, profile.uuid.toString())
        intent.putExtra(LaunchVPN.EXTRA_START_REASON, "main profile list")
        intent.action = Intent.ACTION_MAIN
        startActivity(intent)
    }

    private fun disconnect() {
        val disconnectVPN = Intent(this, DisconnectVPN::class.java)
        disconnectVPN.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(disconnectVPN)
    }

    private fun onVpnConnectionStateChanged(state: String) {
        Log.d("--OVPN_STATE--", state)
    }

    private fun onVpnConnectionNetstatChanged(byteIn: String, byteOut: String) {
        Log.d("--NETSTAT--", "$byteIn;$byteOut")
    }
}


@Composable
fun ScreenCompose(onStart: ()-> Unit, onStop: ()-> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        Button(onClick = { onStart() }) {
            Text(text = "connect")
        }
        Button(onClick = { onStop() }) {
            Text(text = "disconnect")
        }
    }
}




