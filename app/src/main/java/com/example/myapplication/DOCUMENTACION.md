# Documentación de Pantallas - MiPlato

Este documento describe la estructura y funcionalidad de las pantallas de la aplicación.

## 1. MainActivity (Controlador de Navegación)
- **Función**: Actúa como el orquestador principal de la aplicación.
- **Lógica**: Utiliza un estado (`currentScreen`) para alternar entre los diferentes composables de la aplicación sin necesidad de librerías externas complejas, ideal para prototipos rápidos.

## 2. LoginScreen (Acceso)
- **Función**: Permite al usuario entrar a su cuenta.
- **Elementos Clave**: 
    - Logotipo centrado.
    - Campos de entrada con iconos decorativos.
    - Acceso directo a la pantalla de Registro.

## 3. RegisterScreen (Registro de Usuario)
- **Función**: Captura los datos iniciales y objetivos del nuevo usuario.
- **Innovación**: Incluye un selector de objetivos interactivo que cambia de color al ser seleccionado.
- **Validación**: Estructura visual preparada para mostrar requisitos de contraseña.

## 4. HomeScreen (Dashboard Principal)
- **Función**: Muestra un resumen diario de la nutrición del usuario.
- **Elementos Clave**:
    - Saludo personalizado.
    - Tarjeta de progreso de calorías (visualización de objetivo vs consumo).
    - Sección de recomendaciones inteligentes con iconos.
    - Botón de acción principal (CTA) para escanear comida.

## 5. CameraScreen (Escaneo de Comida)
- **Función**: Interfaz de cámara para la captura de alimentos.
- **Elementos Clave**:
    - Visor de cámara estilizado.
    - Botón de obturador ergonómico.
    - Acceso rápido para volver al dashboard principal.


## 6. HistoryScreen (Historial de Consumo)
- **Función**: Permite visualizar el registro histórico de nutrición del usuario de forma diaria.
- **Elementos Clave**:
  - **Gráfico de Tendencia**: Imagen superior que muestra la evolución de calorías.
  - **Lista Expandible**: Implementada con `LazyColumn` para optimizar el rendimiento. Al hacer clic en un día, se expande para mostrar el desglose.
  - **Visualización de Macros**: Incluye un "Pie Chart" simplificado dibujado con `Canvas` para representar la distribución de proteínas, carbohidratos y grasas.
  - **Navegación**: Integra una `NavigationBar` personalizada que resalta la sección de Historial.

## Implementación de Firebase
Se ha configurado la base técnica para los servicios de Google en la aplicación:
- **Firebase BOM**: Gestión centralizada de versiones para evitar conflictos entre dependencias.
- **Firebase Authentication**: Preparado para gestionar el registro e inicio de sesión de usuarios de forma segura.
- **Cloud Firestore**: Configurado para el almacenamiento de perfiles de usuario y registros de comidas en tiempo real.
- **Google Services**: Integración del plugin de Gradle y el archivo `google-services.json` para la comunicación con el proyecto en la consola de Firebase.
