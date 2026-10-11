# Sistema de Registro de Estudiantes

Aplicación Android desarrollada con Kotlin y Jetpack Compose para capturar datos de un estudiante, consultar su detalle y conservar localmente el último registro.

## Datos del registro
- Matrícula y nombre completo mediante campos de texto obligatorios.
- Carrera mediante un menú desplegable con cinco opciones.
- Turno matutino o vespertino mediante botones de selección.
- Estatus activo o inactivo mediante un interruptor.

## Carreras disponibles
1. Ingeniería en Mecatrónica.
2. Ingeniería en Sistemas Computacionales.
3. Ingeniería Industrial.
4. Licenciatura en Administración.
5. Licenciatura en Contaduría.

## Funciones
### Formulario de registro
Valida que matrícula, nombre y carrera estén completos antes de guardar. El botón **Guardar y ver detalle** almacena el registro y abre la pantalla de confirmación con los datos capturados.

**Limpiar formulario** restablece los campos para una nueva captura: matrícula, nombre y carrera vacíos, turno matutino y estatus activo. El último registro guardado permanece disponible hasta que se guarde uno nuevo.

### Pantalla de detalle
Presenta matrícula, nombre, carrera, turno y estatus del registro recibido mediante argumentos de navegación.

- **Registrar otro estudiante:** regresa al formulario con los campos restablecidos.
- **Editar registro:** vuelve al formulario conservando los datos capturados.
- **Compartir registro:** abre el selector de aplicaciones de Android para compartir los datos como texto. El usuario elige el destino y completa el envío.

### Apariencia
El interruptor **Tema oscuro** alterna entre una paleta clara y una oscura, ambas con acentos rosados. La elección se aplica a las dos pantallas y se conserva al cerrar la aplicación.

### Persistencia local
SharedPreferences almacena los cinco datos del último registro y la preferencia de tema. Al abrir nuevamente la aplicación, se recuperan los datos guardados. Cada nuevo registro reemplaza al anterior; la aplicación conserva un único registro, sin historial de estudiantes.

El estado temporal del formulario se conserva durante cambios de configuración del dispositivo mediante rememberSaveable. Los formularios sin guardar no reemplazan el registro persistido.

## Organización
- `MainActivity.kt`: inicio de la aplicación y estado del tema.
- `navigation/AppNavigation.kt`: navegación y transmisión de argumentos.
- `model/Estudiante.kt`: estructura de los datos y catálogo de carreras.
- `data/PreferencesManager.kt`: almacenamiento y recuperación local.
- `ui/screens/RegistroScreen.kt`: formulario, validación y selección del tema.
- `ui/screens/DetalleScreen.kt`: consulta, edición, nueva captura y uso compartido.
- `ui/theme/Theme.kt`: paletas de color clara y oscura.

## Tecnologías
Kotlin, Jetpack Compose, Material 3, Navigation Compose, SharedPreferences e Intent ACTION_SEND. Android mínimo: API 24.
