# 📋 Sistema de Gestión de Incidencias

**Versión:** 1.0  
**Desarrollado por:** Proyecto3inTechAhmed  
**Tecnologías:** Java, JavaFX, Maven, MySQL

---

## 🚀 ¿Qué es este sistema?

El Sistema de Gestión de Incidencias es una aplicación de escritorio pensada para organizaciones que desean gestionar de manera eficiente los problemas técnicos que ocurren en su entorno.  
Con este sistema podrás:

- Reportar fallos técnicos de manera sencilla.
- Hacer seguimiento de incidencias.
- Asignar tareas a técnicos especializados.
- Administrar usuarios del sistema.

Es ideal para **usuarios sin experiencia técnica** gracias a su interfaz clara y guiada.

---

## 👤 Tipos de Usuario

### 🛠️ Técnico

- Visualiza las incidencias que se le han asignado.
- Cambia el estado de la incidencia a **En Progreso** o **Resuelta**.
- Especifica la solución implementada.

### 👨‍💼 Administrador

- Agrega, edita o elimina usuarios del sistema.
- Asigna incidencias a los técnicos disponibles.
- Establece prioridades (**Urgente**, **Normal**, **Baja**).
- Visualiza y filtra todas las incidencias registradas.

### 👨‍💻 Cliente

- Reporta nuevos problemas técnicos.
- Describe el fallo y puede adjuntar archivos.
- Consulta el estado actualizado de sus reportes.

---

## 🔐 Inicio de Sesión

1. Ingresa tu **correo electrónico** registrado.
2. Introduce tu **contraseña**.
3. Pulsa el botón **Iniciar Sesión**.

Si aparece un mensaje de error:

- Verifica que el correo y contraseña estén bien escritos.
- Si no tienes acceso, contacta al administrador.

🔒 Recomendaciones:
- No compartas tus credenciales.
- Cierra sesión al terminar tu uso.

---

## 🧭 Navegación según el tipo de usuario

### Cliente
- **Nueva Incidencia**: Describe el problema, opcionalmente adjunta archivos.
- **Mis Incidencias**: Consulta el estado y evolución de tus reportes.
- **Actualizar**: Refresca la información para ver novedades.
- **Cerrar Sesión**: Finaliza tu sesión de forma segura.

### Administrador
- **Gestionar Usuarios**: Crear, modificar o eliminar usuarios.
- **Asignar Incidencias**: Designa un técnico a cada incidencia.
- **Ver Incidencias**: Revisa todas las incidencias, filtra por estado.
- **Cerrar Sesión**: Cierra tu cuenta de forma segura.

### Técnico
- **Incidencias Asignadas**: Lista con detalles de incidencias a resolver.
- **Resolver Incidencias**: Cambia el estado y documenta la solución.
- **Actualizar**: Muestra tareas nuevas o cambios recientes.
- **Cerrar Sesión**: Finaliza la sesión correctamente.

---

## 🛠️ Requisitos del Sistema

- [Java 23](https://www.oracle.com/java/technologies/javase-downloads.html)
- [JavaFX 24.0.1](https://openjfx.io/)
- [Apache Maven](https://maven.apache.org/) (versión 3.8 o superior)
- MySQL Server (para base de datos)

---

## ▶️ Cómo ejecutar el proyecto

### Opción 1: Usando Maven

```bash
mvn clean javafx:run
