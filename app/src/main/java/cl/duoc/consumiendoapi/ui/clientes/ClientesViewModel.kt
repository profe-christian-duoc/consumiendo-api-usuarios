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
                    "No se pudo conectar con el servidor. " +
                            "Comprueba la conexión e inténtalo nuevamente."
                )
            }
        }
    }
}
