package cl.duoc.consumiendoapi.data

import retrofit2.Response
import retrofit2.http.*

interface ClientesApi {

    @GET("usuarios")
    suspend fun obtenerClientes(): List<Cliente>

}