# 📘 Guía del Usuario Básico  
**Sistema de Gestión de Incidencias**  
**Versión 1.0 — 23/05/2025**

---

## 🔧 Instalación del sistema

Antes de comenzar a usar el sistema, debes realizar la instalación siguiendo estos pasos:

1. 📥 **Descarga el proyecto** desde el enlace proporcionado por el equipo de soporte o desde la plataforma correspondiente.
2. 📂 **Extrae el contenido** del archivo ZIP en una carpeta de tu preferencia.
3. 📁 Accede a la siguiente ruta dentro de la carpeta extraída:  
   `out/artifacts/Proyecto3inTechAhmed_jar`
4. ▶️ Ejecuta el archivo **Ejecutable.bat** haciendo doble clic.

> 📝 *Este archivo inicia la aplicación correctamente con JavaFX. Si ves un error de "JavaFX runtime missing", asegúrate de estar usando el `.bat` proporcionado.*

---

## 1. 👋 ¿Qué es este sistema?

El Sistema de Gestión de Incidencias es una aplicación que permite:

- Reportar problemas técnicos fácilmente.  
- Hacer seguimiento del estado de los problemas.  
- Asignar tareas a técnicos para resolverlos.  
- Gestionar usuarios (clientes, técnicos, administradores).  

---

## 1. 👋 ¿Qué es este sistema?

El Sistema de Gestión de Incidencias es una aplicación que permite:

- Reportar problemas técnicos fácilmente.  
- Hacer seguimiento del estado de los problemas.  
- Asignar tareas a técnicos para resolverlos.  
- Gestionar usuarios (clientes, técnicos, administradores).  

### Tipos de usuarios:

- **Clientes**: Reportan incidencias (problemas).  
- **Técnicos**: Atienden y resuelven las incidencias.  
- **Administradores**: Gestionan usuarios e incidencias.  

🔰 *No necesitas conocimientos de informática para utilizarlo. Solo sigue esta guía paso a paso.*

---

## 2. 🔐 ¿Cómo iniciar sesión?

Cuando abras el programa, verás un formulario con dos campos:

![Captura de pantalla](https://github.com/user-attachments/assets/e7e12192-2f88-41f2-8f69-555f2bc34716)

- **Correo electrónico**: Escribe el correo que usaste al registrarte.  
  *Ejemplo:* `ana.perez@empresa.com`  
- **Contraseña**: Introduce tu contraseña. Se mostrará como puntos por seguridad.  

### ¿Qué hacer luego?

Haz clic en el botón **"Iniciar sesión"**.

### Posibles errores:

- ❌ **Correo o contraseña incorrectos**: Verifica si están bien escritos.

![Error de credenciales](https://github.com/user-attachments/assets/a2e9a9d2-f236-4796-9afe-0290263cfa23)

- ❌ **Email y contraseña son obligatorios**: Debes introducir ambos campos, no puede quedar ninguno vacío.

![Campos obligatorios](https://github.com/user-attachments/assets/88f81855-7b4b-41b4-a1ae-176860f2e367)

### Consejos:

- Guarda tu contraseña en un lugar seguro.  
- No dejes tu sesión abierta en ordenadores compartidos.  
- Si olvidaste tu contraseña, contacta al soporte técnico.

---

## 3. 🛠 Área del Administrador

- Email: `admin@admin.com`  
- Contraseña: `admin`  

![Área administrador](https://github.com/user-attachments/assets/a0b63294-57f6-4121-8d32-8216e5c9e45a)

### 👤 Gestionar usuarios

![Gestión de usuarios](https://github.com/user-attachments/assets/45c23ec9-abd9-483e-955b-4192ca3561a4)

- **Dar de alta nuevos usuarios**: Añade nuevos técnicos o clientes.  
- **Modificar usuarios existentes**: Cambia nombre, correo o tipo de cuenta.  
- **Dar de baja usuarios**: Elimina cuentas.

Selecciona un usuario de la lista y haz clic en el botón correspondiente.

### 📝 Asignar incidencias

![Asignar incidencias](https://github.com/user-attachments/assets/39240ce9-3994-42e8-be51-4362c32a48dc)

- Revisa problemas reportados por clientes.  
- Asigna cada incidencia a un técnico disponible.

Introduce el ID de la incidencia, pulsa **Buscar incidencia** para verificar que existe. Luego, introduce el email del técnico y pulsa **Buscar técnico**. Finalmente, haz clic en **Asignar Incidencia**.

### 👁 Ver todas las incidencias

![Ver incidencias](https://github.com/user-attachments/assets/bf7161ee-b258-44b3-ade9-c1e1195f319b)

Visualiza todas las incidencias registradas y filtra por estado:

- 🟡 Pendiente  
- 🔵 En Progreso  
- ✅ Resuelta  

### 🔓 Cerrar sesión

Haz clic en **Cerrar sesión** al finalizar tu jornada.

---

## 4. 👤 Área del Cliente

![Área cliente](https://github.com/user-attachments/assets/2b9a8412-2270-4344-8e48-3a8bc718bd1e)

### 🆕 Reportar nueva incidencia

![Reportar incidencia](https://github.com/user-attachments/assets/0dd0c3e9-425e-425e-ae54-cef4cfdd83ba)

- **Descripción del problema**: Explica claramente lo que ocurre.  
  *Ejemplo:* “Al iniciar sesión, el programa se queda en blanco y se cierra.”  
- Haz clic en **Crear** para registrar tu incidencia.

### 📄 Ver mis incidencias

Consulta tus reportes anteriores en una tabla con:

- ID del reporte  
- Descripción  
- Estado actual: Pendiente / En Progreso / Resuelto  

Pulsa **Actualizar** para ver los cambios recientes.

### 🔓 Cerrar sesión

Cierra sesión siempre al finalizar, especialmente si usas un equipo compartido.

---

## 5. 🧑‍🔧 Área del Técnico

![Área técnico](https://github.com/user-attachments/assets/7af2f091-43e6-4fe5-bca0-0e4ba3c5808c)

### 📋 Ver incidencias asignadas

Revisa los detalles de tus tareas:

- ID de la incidencia  
- Cliente  
- Descripción  
- Fecha de creación  
- Fecha de solución  
- Estado  
- Solución aplicada  

### ✔ Resolver incidencia

![Resolver incidencia](https://github.com/user-attachments/assets/55b1b4f0-941f-4164-8a78-a67301d435c5)

- Cambia el estado a **En Progreso** al iniciar.  
- Cambia a **Resuelta** al finalizar.  
- Especifica la solución aplicada.  
  *Ejemplo:* “Se ha restablecido la conexión a la base de datos para el inicio de sesión de usuarios.”

### 🔄 Actualizar

Haz clic en **Actualizar** para ver nuevas tareas o cambios.

### 🔓 Cerrar sesión

Recuerda cerrar sesión para proteger tu cuenta.

---

## 6. 💡 Consejos útiles

- Usa siempre el botón **Actualizar** en cada vista para ver información actualizada.

### ¿Tabla vacía?

Posibles causas:

- No hay datos cargados.  
- No tienes conexión a Internet.  
- Tu sesión ha caducado (reinicia la aplicación).

### ❗ Problemas comunes

| Problema                   | Solución sugerida                                               |
|----------------------------|-----------------------------------------------------------------|
| "JavaFX runtime missing"   | Usa el archivo `.bat` incluido con la aplicación.              |
| El sistema no carga        | Verifica tu conexión o consulta al soporte técnico.            |
| No puedo iniciar sesión    | Verifica tu correo y contraseña. Contacta al administrador.    |

---

## 7. 🆘 Contacto de soporte (ficticio)

Si necesitas ayuda, contacta al equipo técnico:

- 📧 **Correo:** [proyecto3intech@gmail.com](mailto:proyecto3intech@gmail.com)  
- ☎️ **Teléfono:** +34 900 123 456  
  *Horario: Lunes a Viernes, de 8:00 a 18:00*

---

## 🙌 ¡Gracias por usar nuestro sistema!

Esperamos que esta guía te haya sido útil. Nuestro objetivo es que tu experiencia sea clara, rápida y sencilla.

🌐 Visita nuestro sitio para más información o futuras actualizaciones:  
[www.proyecto3intech.com](http://www.proyecto3intech.com)

— *Equipo de Desarrollo del Sistema de Incidencias 3inTech*
