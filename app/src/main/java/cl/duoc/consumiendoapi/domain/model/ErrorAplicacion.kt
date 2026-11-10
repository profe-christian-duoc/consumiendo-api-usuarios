package cl.duoc.consumiendoapi.domain.model

class ErrorAplicacion(
    val codigoHttp: Int?,
    override val message: String
) : Exception(message)