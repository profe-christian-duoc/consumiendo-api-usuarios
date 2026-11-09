package cl.duoc.consumiendoapi.domain.usecase

import cl.duoc.consumiendoapi.domain.model.Cliente
import cl.duoc.consumiendoapi.domain.model.EstadisticasClientes

class CalcularEstadisticasUseCase {
    operator fun invoke(clientes: List<Cliente>): EstadisticasClientes {
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
