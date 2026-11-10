package cl.duoc.consumiendoapi.ui.clientes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.consumiendoapi.data.usecase.ClientesUseCases
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClientesViewModel(
    private val clientesUseCases: ClientesUseCases = ClientesUseCases()
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ClientesUiState>(ClientesUiState.Cargando)

    private val _eliminandoId = MutableStateFlow<Int?>(null)
    val eliminandoId = _eliminandoId.asStateFlow()

    private val _mensajeOperacion = MutableStateFlow<String?>(null)
    val mensajeOperacion = _mensajeOperacion.asStateFlow()

    val uiState = _uiState.asStateFlow()

    init {
        cargarClientes()
    }

    fun cargarClientes() {
        viewModelScope.launch {
            _uiState.value = ClientesUiState.Cargando

            try {
                val clientes = clientesUseCases.obtenerClientes()
                _uiState.value = ClientesUiState.Exito(clientes)

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {
                _uiState.value = ClientesUiState.Error(
                    e.message ?: "No se pudieron cargar los clientes."
                )
            }
        }
    }

    fun eliminarCliente(id: Int) {
        if (_eliminandoId.value != null) return

        _eliminandoId.value = id
        _mensajeOperacion.value = null

        viewModelScope.launch {
            try {
                clientesUseCases.eliminarCliente(id)

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {
                _mensajeOperacion.value =
                    "No se pudo eliminar: ${e.message}"
                _eliminandoId.value = null
                return@launch
            }
            _mensajeOperacion.value = "Usuario eliminado correctamente."

            try {
                // 2. Consultar nuevamente los registros
                val clientes = clientesUseCases.obtenerClientes()
                _uiState.value = ClientesUiState.Exito(clientes)

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {
                // No afirmamos que DELETE falló.
                _mensajeOperacion.value =
                    "El usuario fue eliminado, pero no se pudo " +
                            "actualizar la lista. Pulsa Reintentar."
                _uiState.value = ClientesUiState.Error(
                    "No se pudo actualizar la lista de usuarios."
                )

            } finally {
                _eliminandoId.value = null
            }
        }
    }

    fun limpiarMensaje() {
        _mensajeOperacion.value = null
    }
}
