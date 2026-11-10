package cl.duoc.consumiendoapi.ui.form

object ClienteValidator {
    fun validar(
        nombre: String,
        apellido: String,
        edad: Int?,
        tipo: String
    ): String? {
        val trimmedNombre = nombre.trim()
        val trimmedApellido = apellido.trim()

        return when {
            trimmedNombre.isBlank() -> "Ingresa un nombre."
            trimmedApellido.isBlank() -> "Ingresa un apellido."
            edad == null || edad !in 1..120 ->
                "La edad debe estar entre 1 y 120."
            tipo !in listOf("regular", "vip") ->
                "Selecciona un tipo válido."
            else -> null
        }
    }
}
