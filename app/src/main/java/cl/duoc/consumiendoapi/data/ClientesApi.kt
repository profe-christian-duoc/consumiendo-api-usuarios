package cl.duoc.consumiendoapi.data

import retrofit2.http.GET

interface ClientesApi {

    @GET("usuarios")
    suspend fun obtenerClientes(): List<Cliente>
}