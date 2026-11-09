package cl.duoc.consumiendoapi.domain.usecase

import cl.duoc.consumiendoapi.data.repository.ClientesRepository
import cl.duoc.consumiendoapi.domain.model.Cliente

class ObtenerClienteUseCase(
    private val repository: ClientesRepository = ClientesRepository()
) {
    suspend operator fun invoke(id: Int): Cliente = repository.obtenerCliente(id)
}
