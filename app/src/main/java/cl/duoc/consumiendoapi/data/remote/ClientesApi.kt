package cl.duoc.consumiendoapi.data.remote

import cl.duoc.consumiendoapi.data.model.ClienteRequest
import cl.duoc.consumiendoapi.domain.model.Cliente
import retrofit2.Response
import retrofit2.http.*

interface ClientesApi {

    @GET("usuarios")
    suspend fun obtenerClientes(): List<Cliente>

    @GET("usuarios/{id}")
    suspend fun obtenerCliente(
        @Path("id") id: Int
    ): Cliente

    @POST("usuarios")
    suspend fun crearCliente(
        @Body cliente: ClienteRequest
    ): Response<Void>

    @PUT("usuarios/{id}")
    suspend fun actualizarCliente(
        @Path("id") id: Int,
        @Body cliente: ClienteRequest
    ): Response<Void>

    @DELETE("usuarios/{id}")
    suspend fun eliminarCliente(
        @Path("id") id: Int
    ): Response<Void>
}
