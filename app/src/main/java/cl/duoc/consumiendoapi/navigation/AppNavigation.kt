
package cl.duoc.consumiendoapi.navigation

import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.duoc.consumiendoapi.ui.clientes.ClientesScreen
import cl.duoc.consumiendoapi.ui.dashboard.DashboardScreen
import cl.duoc.consumiendoapi.ui.form.FormularioScreen

object Rutas {
    const val INICIO = "inicio"
    const val USUARIOS = "usuarios"
    const val FORMULARIO = "formulario"
    const val EDITAR = "formulario/{id}"

    fun editarCliente(id: Int) = "formulario/$id"
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
                },
                onEditarUsuario = { id ->
                    navController.navigate(Rutas.editarCliente(id))
                }
            )
        }

        composable(Rutas.FORMULARIO) {
            FormularioScreen(
                clienteId = null,
                onVolver = {
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.INICIO) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Rutas.EDITAR,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { entrada ->

            val id = requireNotNull(
                entrada.arguments?.getInt("id")
            )

            FormularioScreen(
                clienteId = id,
                onVolver = {
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.INICIO) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
