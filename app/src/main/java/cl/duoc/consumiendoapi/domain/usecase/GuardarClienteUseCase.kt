package cl.duoc.consumiendoapi.domain.usecase

import cl.duoc.consumiendoapi.data.model.ClienteRequest
import cl.duoc.consumiendoapi.data.repository.ClientesRepository

class GuardarClienteUseCase(
    private val repository: ClientesRepository = ClientesRepository()
) {
    suspend fun crear(cliente: ClienteRequest) = repository.crearCliente(cliente)
    suspend fun actualizar(id: Int, cliente: ClienteRequest) = repository.actualizarCliente(id, cliente)
}
