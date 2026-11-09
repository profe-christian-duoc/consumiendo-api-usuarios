package cl.duoc.consumiendoapi.domain.model

data class EstadisticasClientes(
    val total: Int,
    val vip: Int,
    val regulares: Int,
    val edadPromedio: Double
)
