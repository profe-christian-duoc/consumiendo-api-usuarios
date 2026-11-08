package cl.duoc.consumiendoapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cl.duoc.consumiendoapi.ui.ClientesScreen
import cl.duoc.consumiendoapi.ui.theme.ClientesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClientesAppTheme {
                ClientesScreen()
            }
        }
    }
}
