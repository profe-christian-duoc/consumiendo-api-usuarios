package cl.duoc.consumiendoapi.ui.form

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FormularioScreen(
    clienteId: Int?,
    onVolver: () -> Unit,
    viewModel: ClienteFormViewModel = viewModel()
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val editando = clienteId != null

    LaunchedEffect(clienteId) {
        if (clienteId != null) {
            viewModel.cargarCliente(clienteId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextButton(onClick = onVolver) {
            Text("← Volver")
        }

        Text(
            text = if (editando) "Editar usuario" else "Nuevo usuario",
            style = MaterialTheme.typography.headlineMedium
        )

        if (estado.guardado) {
            Text(
                text = if (editando)
                    "¡Usuario actualizado correctamente!"
                else
                    "¡Usuario creado correctamente!",
                color = MaterialTheme.colorScheme.primary
            )

            Button(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continuar")
            }

        } else if (estado.cargando) {
            CircularProgressIndicator()

        } else {

            OutlinedTextField(
                value = estado.nombre,
                onValueChange = viewModel::cambiarNombre,
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estado.apellido,
                onValueChange = viewModel::cambiarApellido,
                label = { Text("Apellido") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estado.edad,
                onValueChange = viewModel::cambiarEdad,
                label = { Text("Edad") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Tipo de cliente",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = estado.tipo == "regular",
                    onClick = {
                        viewModel.cambiarTipo("regular")
                    },
                    label = { Text("Regular") }
                )

                FilterChip(
                    selected = estado.tipo == "vip",
                    onClick = {
                        viewModel.cambiarTipo("vip")
                    },
                    label = { Text("VIP") }
                )
            }

            if (estado.error != null) {
                Text(
                    text = estado.error!!,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    viewModel.guardar(clienteId)
                },
                enabled = !estado.guardando,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (estado.guardando) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        if (editando) "Actualizar usuario"
                        else "Guardar usuario"
                    )
                }
            }
        }
    }
}
