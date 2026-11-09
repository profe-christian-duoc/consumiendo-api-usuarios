package cl.duoc.consumiendoapi.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.consumiendoapi.domain.usecase.CalcularEstadisticasUseCase
import cl.duoc.consumiendoapi.presentation.clientes.ClientesUiState
import cl.duoc.consumiendoapi.presentation.clientes.ClientesViewModel
import cl.duoc.consumiendoapi.presentation.components.TarjetaEstadistica
import cl.duoc.consumiendoapi.ui.theme.*
import java.util.Locale
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect

@Composable
fun DashboardScreen(
    onAgregarUsuario: () -> Unit,
    onRevisarUsuarios: () -> Unit,
    viewModel: ClientesViewModel = viewModel(),
    calcularEstadisticasUseCase: CalcularEstadisticasUseCase = CalcularEstadisticasUseCase()
) {

    val estado by viewModel.uiState.collectAsStateWithLifecycle()
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.cargarClientes()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "¡Bienvenido!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Resumen general de tus clientes",
            color = AzulSecundario
        )

        when (val actual = estado) {

            is ClientesUiState.Cargando -> {
                CircularProgressIndicator()
            }

            is ClientesUiState.Error -> {
                Text(
                    text = actual.mensaje,
                    color = MaterialTheme.colorScheme.error
                )

                Button(
                    onClick = { viewModel.cargarClientes() }
                ) {
                    Text("Reintentar")
                }
            }

            is ClientesUiState.Exito -> {

                val datos = calcularEstadisticasUseCase(actual.clientes)

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        TarjetaEstadistica(
                            titulo = "Clientes totales",
                            valor = datos.total.toString(),
                            colorBorde = AzulPrincipal
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        TarjetaEstadistica(
                            titulo = "Clientes VIP",
                            valor = datos.vip.toString(),
                            colorBorde = BordeOro
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        TarjetaEstadistica(
                            titulo = "Regulares",
                            valor = datos.regulares.toString(),
                            colorBorde = BordePlata
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        TarjetaEstadistica(
                            titulo = "Edad promedio",
                            valor = String.format(
                                Locale.getDefault(),
                                "%.1f",
                                datos.edadPromedio
                            ),
                            colorBorde = AzulSecundario
                        )
                    }
                }
            }
        }

        HorizontalDivider()

        Button(
            onClick = onAgregarUsuario,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Añadir usuario")
        }

        OutlinedButton(
            onClick = onRevisarUsuarios,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Revisar usuarios")
        }
    }
}
