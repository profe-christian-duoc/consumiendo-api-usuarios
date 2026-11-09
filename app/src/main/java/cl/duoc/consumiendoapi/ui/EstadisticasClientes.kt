package cl.duoc.consumiendoapi.ui

import cl.duoc.consumiendoapi.data.Cliente

data class EstadisticasClientes(
    val total: Int,
    val vip: Int,
    val regulares: Int,
    val edadPromedio: Double
)

fun calcularEstadisticas(
    clientes: List<Cliente>
): EstadisticasClientes {

    val vip = clientes.count {
        it.tipo.equals("vip", ignoreCase = true)
    }

    val regulares = clientes.count {
        it.tipo.equals("regular", ignoreCase = true)
    }

    val promedio = clientes
        .map { it.edad }
        .average()
        .takeUnless { it.isNaN() } ?: 0.0

    return EstadisticasClientes(
        total = clientes.size,
        vip = vip,
        regulares = regulares,
        edadPromedio = promedio
    )
}
