package cl.duoc.consumiendoapi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.consumiendoapi.data.Cliente
import cl.duoc.consumiendoapi.data.ClientesRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ClientesUiState {
    data object Cargando : ClientesUiState

    data class Exito(
        val clientes: List<Cliente>
    ) : ClientesUiState

    data class Error(
        val mensaje: String
    ) : ClientesUiState
}

class ClientesViewModel(
    private val repository: ClientesRepository = ClientesRepository()
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ClientesUiState>(ClientesUiState.Cargando)

    val uiState = _uiState.asStateFlow()

    init {
        cargarClientes()
    }

    fun cargarClientes() {
        viewModelScope.launch {
            _uiState.value = ClientesUiState.Cargando

            try {
                val clientes = repository.obtenerClientes()
                _uiState.value = ClientesUiState.Exito(clientes)

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {
                _uiState.value = ClientesUiState.Error(
                    "No se pudo conectar con el servidor. " +
                            "Comprueba la conexión e inténtalo nuevamente."
                )
            }
        }
    }
}