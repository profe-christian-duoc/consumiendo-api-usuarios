# Refactorización de Arquitectura y Desacoplamiento de Responsabilidades

Refactorizar el proyecto aplicando Clean Architecture y principios SOLID, separando estrictamente las responsabilidades entre la Capa de Dominio (Use Cases), Capa de Datos (Repository), Capa de Presentación (ViewModel) y Capa de Vista (Stateful y Stateless Composables desacoplados con soporte para Preview).

## User Review Required

> [!IMPORTANT]
> No se agregarán dependencias externas ni se modificarán archivos de configuración Gradle, cumpliendo estrictamente con la restricción de cero librerías adicionales.

## Open Questions

- Ninguna. La estructura propuesta utiliza características nativas de Kotlin y Jetpack Compose.

## Proposed Changes

### [Domain Layer]
- [NEW] [ObtenerClientesUseCase.kt](file:///C:/Users/chris/Documents/DSY1105/EA2/ConsumiendoApi/app/src/main/java/cl/duoc/consumiendoapi/domain/ObtenerClientesUseCase.kt): Caso de uso para encapsular la lógica de negocio de obtención y validación de clientes.

### [Presentation Layer - ViewModel]
- [MODIFY] [ClientesViewModel.kt](file:///C:/Users/chris/Documents/DSY1105/EA2/ConsumiendoApi/app/src/main/java/cl/duoc/consumiendoapi/ui/ClientesViewModel.kt): Refactorizado para recibir el `ObtenerClientesUseCase` (o repositorio) vía constructor y enfocarse únicamente en el manejo del estado (`ClientesUiState`).

### [UI Layer - Components & Screens]
- [NEW] [ClienteCard.kt](file:///C:/Users/chris/Documents/DSY1105/EA2/ConsumiendoApi/app/src/main/java/cl/duoc/consumiendoapi/ui/components/ClienteCard.kt): Composable Stateless para la tarjeta moderna de cliente, permitiendo `@Preview`.
- [MODIFY] [ClientesScreen.kt](file:///C:/Users/chris/Documents/DSY1105/EA2/ConsumiendoApi/app/src/main/java/cl/duoc/consumiendoapi/ui/ClientesScreen.kt): Dividido en `ClientesScreen` (Stateful) y `ClientesContent` (Stateless) para facilitar pruebas y previsualizaciones.

## Verification Plan

### Automated Tests
- Ejecutar `./gradlew test` y `./gradlew assembleDebug` para verificar que compila correctamente.

### Manual Verification
- Compilar y desplegar en emulador / dispositivo para verificar el funcionamiento de la app con la nueva arquitectura.
