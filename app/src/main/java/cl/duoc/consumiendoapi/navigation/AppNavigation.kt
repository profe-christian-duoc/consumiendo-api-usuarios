
package cl.duoc.consumiendoapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.consumiendoapi.ui.ClientesScreen
import cl.duoc.consumiendoapi.ui.DashboardScreen
import cl.duoc.consumiendoapi.ui.FormularioScreen
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier

object Rutas {
    const val INICIO = "inicio"
    const val USUARIOS = "usuarios"
    const val FORMULARIO = "formulario"
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rutas.INICIO,
        modifier = Modifier.safeDrawingPadding()
    ) {

        composable(Rutas.INICIO) {
            DashboardScreen(
                onAgregarUsuario = {
                    navController.navigate(Rutas.FORMULARIO)
                },
                onRevisarUsuarios = {
                    navController.navigate(Rutas.USUARIOS)
                }
            )
        }

        composable(Rutas.USUARIOS) {
            ClientesScreen(
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable(Rutas.FORMULARIO) {
            FormularioScreen(
                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}
