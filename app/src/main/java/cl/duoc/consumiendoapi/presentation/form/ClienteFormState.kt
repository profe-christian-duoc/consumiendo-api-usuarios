package cl.duoc.consumiendoapi.presentation.form

data class ClienteFormState(
    val nombre: String = "",
    val apellido: String = "",
    val edad: String = "",
    val tipo: String = "regular",
    val cargando: Boolean = false,
    val guardando: Boolean = false,
    val guardado: Boolean = false,
    val error: String? = null
)
