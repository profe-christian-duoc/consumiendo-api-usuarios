package cl.duoc.consumiendoapi.data

data class Cliente(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val tipo: String
)