package cl.duoc.consumiendoapi.ui
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.consumiendoapi.data.Cliente
import cl.duoc.consumiendoapi.ui.theme.*

@Composable
fun ClientesScreen(
    onVolver: () -> Unit,
    onEditarUsuario: (Int) -> Unit,
    viewModel: ClientesViewModel = viewModel()
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

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

            if (estado is ClientesUiState.Exito) {
                Surface(
                    color = FondoConexion,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "● Conectado",
                        color = VerdeConexion,
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 8.dp
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

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

                            Column {
                                ClienteCard(cliente)

                                OutlinedButton(
                                    onClick = {
                                        onEditarUsuario(cliente.id)
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Editar usuario")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ClienteCard(cliente: Cliente) {

    val esVip = cliente.tipo.equals("vip", ignoreCase = true)
    val colorFondo = if (esVip) Oro else Plata
    val colorBorde = if (esVip) BordeOro else BordePlata

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorFondo,
            contentColor = TextoPrincipal
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            colorBorde
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${cliente.nombre} ${cliente.apellido}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = cliente.tipo.uppercase(),
                    fontWeight = FontWeight.Bold,
                    color = if (esVip) Color(0xFF755000)
                    else Color.DarkGray
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = colorBorde
            )

            Text("ID: ${cliente.id}")
            Text("Nombre: ${cliente.nombre}")
            Text("Apellido: ${cliente.apellido}")
            Text("Edad: ${cliente.edad} años")
            Text("Tipo: ${cliente.tipo}")
        }
    }
}