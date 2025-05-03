# AgroCheck

## 📘 Descripción General

### ¿Qué hace la aplicación?
La aplicación es para la marcación de asistencias (Entrada, Salida, Salida Refrigerio, Regreso Refrigerio, Papeletas).

### ¿A quién está dirigida?
Está dirigida al área de recursos humanos, el cual necesita registrar las distintas asistencias de todas las sucursales del Perú que no cuentan con acceso a Internet.

### ¿Qué problema resuelve?
Resuelve la falta de un sistema eficiente para el registro y control de asistencias en ubicaciones sin conexión a Internet.

## 🛠 Tecnologías y Herramientas

### Frontend:
- **Jetpack Compose**
- **Jetpack Navigation Compose**: Para la navegación entre pantallas.
- **CameraX**: Para funcionalidades de cámara (foto/video).
- **Google Maps Compose**: Para mostrar mapas de forma nativa en Compose.
- **SplashScreen API**: Para mostrar pantalla de inicio.
- **Accompanist Permissions**: Para manejo simplificado de permisos en Compose.

### Backend:
- **Retrofit**: Cliente HTTP para consumir APIs REST.
- **Moshi**: Serialización JSON para convertir respuestas a objetos Kotlin.
- **OkHttp Interceptor**: Para registrar logs de las peticiones.

### Patrones de Diseño:
- **MVVM + Clean Architecture**
- **Hilt** para inyección de dependencias
- **Room** para persistencia local
- **DataStore Preferences**

## 📂 Estructura del Proyecto

El proyecto está organizado principalmente por capas, siguiendo principios de Clean Architecture y MVVM (Model-View-ViewModel). A su vez, la capa de presentación está dividida por features (pantallas o funcionalidades).

- **data/**: Contiene la lógica relacionada al acceso a datos.
  - **dao/**: Interfaces para interactuar con la base de datos local.
  - **entity/**: Entidades que representan las tablas de la base de datos.
  - **repository/**: Repositorios que encapsulan la lógica de acceso a datos.
  - **dataStore/**: Mecanismo para el almacenamiento persistente de datos simples.

- **db/**: Configuración de la base de datos (Room).
- **EntryPoint/**: Punto de acceso temporal para UsuarioDao mientras no se cuenta con una API.
- **AppModule**: Módulo de Hilt para inyección de dependencias.
- **presentation/**: Organizada por features (pantallas como login, home, marcación, historial, etc.).
  - Cada feature contiene su UI (Screen.kt) y ViewModel.

### Navegación y app principal:
- **AppNavigation.kt**: Define la navegación entre pantallas.
- **MainActivity.kt** y **MyApp.kt**: Punto de entrada de la app y configuración general.

## 📦 Instrucciones de Instalación

### 🛠 Requisitos Previos
- Android Studio Giraffe o superior
- JDK 11
- Dispositivo físico o emulador con Android 8.0 (API 26) o superior
  - (Se recomienda un dispositivo con cámara y ubicación habilitada para pruebas completas)
- Conexión a internet (solo para la primera instalación y descarga de dependencias)

### 📥 Clonación y Ejecución del Proyecto
1. Clona el repositorio:
   ```bash
   git clone https://github.com/aaronoterolavi/MarcacionesApp.git
2. Abre el proyecto en Android Studio

3. Espera a que Gradle sincronice todas las dependencias

4. Conecta un dispositivo físico o inicia un emulador

5. Ejecuta la app desde MainActivity o MyApp

### 📌 Notas Adicionales
- **El proyecto utiliza Jetpack Compose, Hilt, Room, DataStore, CameraX y Google Maps Compose.**

- **Se recomienda conceder permisos de cámara y ubicación para probar correctamente la funcionalidad de marcación.**
### 🔐 Credenciales de Prueba
Para probar la funcionalidad de inicio de sesión, puedes usar las siguientes credenciales:

- **Usuario: aotero**

- **Contraseña: 123**
### 🔧 Configuración Adicional
📱 Permisos Requeridos
- **INTERNET: Para futuras conexiones a una API remota.**

- **CAMERA: Para capturar fotos al momento de la marcación.**

- **ACCESS_FINE_LOCATION y ACCESS_COARSE_LOCATION: Para obtener la ubicación precisa del usuario.**

🔌 Uso de Hardware
- **Cámara: Declarada como no obligatoria (required="false"), lo que permite ejecutar la app en dispositivos sin cámara, aunque algunas funcionalidades quedarán limitadas.**
## ✅ Checklist de Funcionalidades

| **Funcionalidad**                        | **Estado**        | **Detalles**                                           |
|------------------------------------------|-------------------|--------------------------------------------------------|
| **Inicio de sesión**                     | ✅ Completo       | Actualmente con un usuario de prueba; pendiente integración con API real |
| **Registro de asistencia (marcación)**   | ✅ Completo       | Incluye captura de foto y estado (entrada/salida)      |
| **Captura de geolocalización**           | 🔧 Pendiente      | La funcionalidad aún no está integrada                |
| **Historial de marcaciones**             | ✅ Completo       | Muestra registros guardados localmente                 |
| **Perfil de usuario**                    | ✅ Completo       | Visualización de datos básicos del usuario             |
| **Almacenamiento offline**               | ✅ Completo       | Datos almacenados en Room Database                     |
| **Sincronización con backend**           | 🔄 En progreso    | APIs aún en desarrollo (login y envío de asistencias)  |

### 🔄 Estado del Proyecto
El proyecto está en desarrollo, con algunas funcionalidades completas (como el inicio de sesión, registro de marcación, y almacenamiento offline), pero aún se encuentran pendientes la integración de la geolocalización y la sincronización con el backend.

### 💡 To Do / Roadmap
- **Integración de la captura de geolocalización en las marcaciones**

- **Sincronización de datos con el backend para la gestión de usuarios y asistencias**

- **Mejoras en la UI/UX, especialmente en la visualización del historial de marcaciones**

- **Optimización del manejo de permisos y validación de dispositivos sin cámara**

### 👤 Autores y Créditos
- **Aaron Andre Otero Lavi**
- **Danny Huapaya Hinostroza**
- **Edson Arturo Quispe Ornay**


