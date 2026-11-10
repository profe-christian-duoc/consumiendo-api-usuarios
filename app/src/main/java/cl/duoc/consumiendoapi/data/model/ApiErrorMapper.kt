package cl.duoc.consumiendoapi.data.repository

import cl.duoc.consumiendoapi.domain.model.ErrorAplicacion
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

fun traducirError(error: Exception): ErrorAplicacion {

    if (error is ErrorAplicacion) return error

    return when (error) {

        is HttpException -> {
            val codigo = error.code()

            val mensaje = when (codigo) {
                400 -> "Los datos enviados no son válidos."
                401 -> "Debes iniciar sesión."
                403 -> "No tienes permisos para esta operación."
                404 -> "El usuario solicitado no existe."
                409 -> "La operación genera un conflicto."
                422 -> "El servidor rechazó los datos."
                in 500..599 -> "Error interno del servidor."
                else -> "Error HTTP $codigo."
            }

            ErrorAplicacion(codigo, mensaje)
        }

        is SocketTimeoutException -> {
            ErrorAplicacion(
                null,
                "El servidor tardó demasiado en responder."
            )
        }

        is IOException -> {
            ErrorAplicacion(
                null,
                "No fue posible comunicarse con el servidor."
            )
        }

        else -> {
            ErrorAplicacion(
                null,
                "Ocurrió un error inesperado."
            )
        }
    }
}
