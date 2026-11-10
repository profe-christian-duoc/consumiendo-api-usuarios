package cl.duoc.consumiendoapi.data.usecase

import cl.duoc.consumiendoapi.data.model.ClienteRequest
import cl.duoc.consumiendoapi.data.repository.ClientesRepository
import cl.duoc.consumiendoapi.domain.model.Cliente
import cl.duoc.consumiendoapi.domain.model.EstadisticasClientes

class ClientesUseCases(
    private val repository: ClientesRepository = ClientesRepository()
) {
    suspend fun obtenerClientes(): List<Cliente> = repository.obtenerClientes()

    suspend fun obtenerCliente(id: Int): Cliente = repository.obtenerCliente(id)

    suspend fun crearCliente(cliente: ClienteRequest) = repository.crearCliente(cliente)

    suspend fun actualizarCliente(id: Int, cliente: ClienteRequest) = repository.actualizarCliente(id, cliente)

    fun calcularEstadisticas(clientes: List<Cliente>): EstadisticasClientes {
        val vip = clientes.count { it.tipo.equals("vip", ignoreCase = true) }
        val regulares = clientes.count { it.tipo.equals("regular", ignoreCase = true) }
        val promedio = clientes.map { it.edad }.average().takeUnless { it.isNaN() } ?: 0.0

        return EstadisticasClientes(
            total = clientes.size,
            vip = vip,
            regulares = regulares,
            edadPromedio = promedio
        )
    }
}
