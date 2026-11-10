package cl.duoc.consumiendoapi.ui.clientes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.consumiendoapi.ui.components.ClienteCard
import cl.duoc.consumiendoapi.ui.theme.*
import androidx.compose.runtime.*
import cl.duoc.consumiendoapi.domain.model.Cliente


@Composable
fun ClientesScreen(
    onVolver: () -> Unit,
    onEditarUsuario: (Int) -> Unit,
    viewModel: ClientesViewModel = viewModel()
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()
    val eliminandoId by viewModel.eliminandoId.collectAsStateWithLifecycle()
    val mensajeOperacion by viewModel.mensajeOperacion.collectAsStateWithLifecycle()

    var clienteAEliminar by remember {
        mutableStateOf<Cliente?>(null)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        TextButton(
            onClick = onVolver
        ) {
            Text("← Volver al inicio")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Clientes",
                style = MaterialTheme.typography.headlineMedium
            )
            /*
            if (estado is ClientesUiState.Exito) {
                Surface(
                    color = FondoConexion,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "Conectado",
                        color = VerdeConexion,
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 8.dp
                        )
                    )
                }
            }
             */
        }

        Spacer(modifier = Modifier.height(20.dp))
        if (mensajeOperacion != null) {
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = mensajeOperacion!!,
                        modifier = Modifier.weight(1f)
                    )

                    TextButton(
                        onClick = viewModel::limpiarMensaje
                    ) {
                        Text("Cerrar")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
        when (val actual = estado) {

            is ClientesUiState.Cargando -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ClientesUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = actual.mensaje,
                        color = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(onClick = { viewModel.cargarClientes() }) {
                        Text("Reintentar")
                    }
                }
            }

            is ClientesUiState.Exito -> {
                if (actual.clientes.isEmpty()) {
                    Text("No hay clientes registrados.")
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(
                            items = actual.clientes,
                            key = { it.id }
                        ) { cliente ->


                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                ClienteCard(cliente)

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {

                                    OutlinedButton(
                                        onClick = {
                                            onEditarUsuario(cliente.id)
                                        },
                                        enabled = eliminandoId == null,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Editar")
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            clienteAEliminar = cliente
                                        },
                                        enabled = eliminandoId == null,
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = MaterialTheme.colorScheme.error
                                        ),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        if (eliminandoId == cliente.id) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(18.dp),
                                                strokeWidth = 2.dp
                                            )
                                        } else {
                                            Text("Eliminar")
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        clienteAEliminar?.let { cliente ->

            AlertDialog(
                onDismissRequest = {
                    clienteAEliminar = null
                },

                title = {
                    Text("¿Eliminar usuario?")
                },

                text = {
                    Text(
                        "¿Deseas eliminar a " +
                                "${cliente.nombre} ${cliente.apellido}? " +
                                "Esta acción no se puede deshacer."
                    )
                },

                confirmButton = {
                    TextButton(
                        onClick = {
                            clienteAEliminar = null
                            viewModel.eliminarCliente(cliente.id)
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor =
                                MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Sí, eliminar")
                    }
                },

                dismissButton = {
                    TextButton(
                        onClick = {
                            clienteAEliminar = null
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
