package cl.duoc.consumiendoapi.presentation.clientes

import cl.duoc.consumiendoapi.domain.model.Cliente

sealed interface ClientesUiState {
    data object Cargando : ClientesUiState

    data class Exito(
        val clientes: List<Cliente>
    ) : ClientesUiState

    data class Error(
        val mensaje: String
    ) : ClientesUiState
}
