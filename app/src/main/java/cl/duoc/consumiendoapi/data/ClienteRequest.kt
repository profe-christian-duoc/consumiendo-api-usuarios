package cl.duoc.consumiendoapi.data

data class ClienteRequest(
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val tipo: String
)