package cl.duoc.consumiendoapi.data.model

data class ClienteRequest(
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val tipo: String
)
