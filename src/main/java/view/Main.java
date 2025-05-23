/*

package view;

import controller.Controlador;
import models.*;
import utils.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static final Scanner S = new Scanner(System.in);

    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        Usuario usuarioLogeado;
        do {
            do {
                System.out.println();
                System.out.println(" - BIENVENIDO A NUESTRO SISTEMA DE INCIDENCIAS, INICIE SESIÓN CON SUS CREDENCIALES");
                controlador = new Controlador();
                usuarioLogeado = iniciarSesion(controlador);
                if (usuarioLogeado == null) System.out.println("\n* ERROR AL INICIAR SESIÓN\n");
            } while (usuarioLogeado == null);
            menuUsuarios(controlador, usuarioLogeado);
        } while (true);
    }

    //Metodo que pinta y tiene la logica para mostrar un menu para cada tipo de usuario
    private static void menuUsuarios(Controlador controlador, Usuario usuarioLogeado) {
        if (usuarioLogeado != null) {
            switch (usuarioLogeado.getTipoUsuario()) {
                case "cliente":
                    Cliente clienteLog = (Cliente) usuarioLogeado;
                    menuCliente(controlador, clienteLog);
                    break;
                case "tecnico":
                    Tecnico tecnicoLog = (Tecnico) usuarioLogeado;
                    menuTecnico(controlador, tecnicoLog);
                    break;
                case "admin":
                    Admin adminLog = (Admin) usuarioLogeado;
                    menuAdmin(controlador, adminLog);
                    break;
            }
        }
    }

    //Metodo que pinta y tiene la logica del menu cliente
    private static void menuAdmin(Controlador controlador, Admin adminLog) {
        int op = 0;
        do {
            if (adminLog.getUltimoAcceso() != null)
                System.out.println("\n* USTED HA ACCEDIDO POR ULTIMO VEZ: " + adminLog.getUltimoAccesoFormateado());
            System.out.println("\n---------- BIENVENIDO " + adminLog.getNombre() + " " + adminLog.getApellidos() + " AL MENÚ DE GESTIÓN----------\n");
            do {
                try {
                    System.out.print("""
                            1. - Alta/baja/modificación de usuarios (clientes/técnicos).
                            2. - Asignación manual de incidencias a técnicos.
                            3. - Visualización de todos los tickets.
                            4. - Salir
                            Opcion:\s""");
                    op = Integer.parseInt(S.nextLine());
                } catch (NumberFormatException e) {
                    System.out.print("");
                }
                if (op < 1 || op > 4) System.out.println("\n * ERROR AL INTRODUCIR LA OPCIÓN\n");
            } while (op < 1 || op > 4);
            switch (op) {
                case 1:
                    gestionaUsuarios(controlador, adminLog);
                    break;
                case 2:
                    asignaIncidencias(controlador, adminLog);
                    break;
                case 3:
                    visualizaIncidencias(controlador, adminLog);
                    break;
                case 4:
                    adminLog.setUltimoAcceso(LocalDateTime.now());
                    controlador.actualizaUsuario(adminLog);
                    Utils.mensajeSaliendo();
                    break;
            }
        } while (op != 4);
    }

    //Metodo que tiene la logica para gestionar los usuarios
    private static void gestionaUsuarios(Controlador controlador, Admin adminLog) {
        int op = 0;
        do {
            try {
                System.out.print("""
                        1. - Alta
                        2. - Baja
                        3. - Modificar usuario
                        4. - Salir
                        Opcion:\s""");
                op = Integer.parseInt(S.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("");
            }
            if (op < 1 || op > 4) System.out.println("\n * ERROR AL INTRODUCIR LA OPCIÓN\n");
        } while (op < 1 || op > 4);
        switch (op) {
            case 1:
                altaUsuarios(controlador);
                break;
            case 2:
                bajaUsuarios(controlador);
                break;
            case 3:
                modificaUsuarios(controlador);
                break;
            case 4:
                Utils.mensajeSaliendo();
                break;
        }
    }

    private static void bajaUsuarios(Controlador controlador) {
        Usuario usuarioBuscado = null;
        String idUsuarioBuscado, respuesta = "";
        do {
            System.out.print(" - Introduzca el ID del usuario al que desea modificar (-1 para salir): ");
            idUsuarioBuscado = S.nextLine();
            if (idUsuarioBuscado.equalsIgnoreCase("-1")) return;
            usuarioBuscado = controlador.buscaUsuarioById(idUsuarioBuscado);
            if (usuarioBuscado != null) {
                System.out.println(" - Ha seleccionado a " + usuarioBuscado.getNombre() + " " + usuarioBuscado.getApellidos());
                do {
                    System.out.print("¿Es este el usuario que buscas (S/N)?: ");
                    respuesta = S.nextLine();
                } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));
            } else System.out.println("\n* ERROR NO HAY NINGUN USUARIO CON ESE ID\n");
        } while (!respuesta.equalsIgnoreCase("s"));
        controlador.bajaUsuario(idUsuarioBuscado);
    }

    //Metodo que tiene la logica para dar de alta usuarios
    private static void altaUsuarios(Controlador controlador) {
        int op = 0;
        do {
            try {
                System.out.print("""
                        Seleccione el tipo de usuario a dar de alta:
                        1. - Cliente
                        2. - Técnico
                        3. - Administrador
                        4. - Salir
                        Opción:\s""");
                op = Integer.parseInt(S.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("");
            }
            if (op < 1 || op > 4) System.out.println("\n * ERROR AL INTRODUCIR LA OPCIÓN\n");
        } while (op < 1 || op > 4);

        System.out.print(" - Introduzca nombre: ");
        String nombre = S.nextLine();
        System.out.print(" - Introduzca apellidos: ");
        String apellidos = S.nextLine();
        String email;
        Usuario usuarioEmail;
        do {
            System.out.print(" - Introduzca correo electrónico: ");
            email = S.nextLine();
            usuarioEmail = controlador.buscaUsuarioByEmail(email);
            if (usuarioEmail != null) System.out.println(" * ERROR EL CORREO ELECTRONICO YA EXISTE");
        } while (usuarioEmail != null);
        System.out.print(" - Introduzca contraseña: ");
        String pass = S.nextLine();
        System.out.print(" - Introduzca teléfono: ");
        String telefono = S.nextLine();

        switch (op) {
            case 1: //Cliente
                System.out.print(" - Introduzca su dirección: ");
                String direccion = S.nextLine();
                System.out.print(" - Introduzca nombre de empresa: ");
                String nombreEmpresa = S.nextLine();
                if (controlador.creaNuevoCliente(nombre, apellidos, email, pass, telefono, direccion, nombreEmpresa))
                    System.out.println(" - CLIENTE CREADO CON ÉXITO");
                else System.out.println(" * ERROR NO SE HA PODIDO CREAR EL NUEVO CLIENTE");
                break;
            case 2: //Tecnico
                System.out.print(" - Introduzca nivel de experiencia: ");
                String nivelExp = S.nextLine();
                if (controlador.creaNuevoTecnico(nombre, apellidos, email, pass, telefono, nivelExp))
                    System.out.println(" - TÉCNICO CREADO CON ÉXITO");
                else System.out.println(" * ERROR NO SE HA PODIDO CREAR EL NUEVO TÉCNICO");
                break;
            case 3: //Admin
                if (controlador.creaNuevoAdmin(nombre, apellidos, email, pass, telefono))
                    System.out.println(" - ADMINISTRADOR CREADO CON ÉXITO");
                else System.out.println(" * ERROR NO SE HA PODIDO CREAR EL NUEVO ADMINISTRADOR");
                break;
            case 4:
                Utils.mensajeSaliendo();
                break;
        }
        Utils.pulsaParaContinuar();
    }

    //Metodo que tiene la logica para modificar los usuarios
    private static void modificaUsuarios(Controlador controlador) {
        Usuario usuarioBuscado = null;
        String idUsuarioBuscado, respuesta = "";
        do {
            System.out.print(" - Introduzca el ID del usuario al que desea modificar (-1 para salir): ");
            idUsuarioBuscado = S.nextLine();
            if (idUsuarioBuscado.equalsIgnoreCase("-1")) return;
            usuarioBuscado = controlador.buscaUsuarioById(idUsuarioBuscado);
            if (usuarioBuscado != null) {
                System.out.println(" - Ha seleccionado a " + usuarioBuscado.getNombre() + " " + usuarioBuscado.getApellidos());
                do {
                    System.out.print("¿Es este el usuario que buscas (S/N)?: ");
                    respuesta = S.nextLine();
                } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));
            } else System.out.println("\n* ERROR NO HAY NINGUN USUARIO CON ESE ID\n");
        } while (!respuesta.equalsIgnoreCase("s"));
        switch (usuarioBuscado.getTipoUsuario()) {
            case "cliente":
                modificaCliente(controlador, usuarioBuscado);
                Utils.pulsaParaContinuar();
                break;
            case "tecnico":
                modificaTecnico(controlador, usuarioBuscado);
                Utils.pulsaParaContinuar();
                break;
            case "admin":
                modificaAdmin(controlador, usuarioBuscado);
                Utils.pulsaParaContinuar();
                break;
        }
    }

    //Metodo que modifica los datos de un admin
    private static void modificaAdmin(Controlador controlador, Usuario usuarioBuscado) {
        Admin adminModificar = (Admin) controlador.buscaUsuarioById(usuarioBuscado.getId());
        int opAdmin = 0;
        do {
            do {
                try {
                    System.out.println("Seleccione que desea modificar: ");
                    System.out.print("""
                            1. - Nombre
                            2. - Apellidos
                            3. - Correo electrónico
                            4. - Contraseña
                            5. - Teléfono
                            6. - Salir
                            Opción:\s""");
                    opAdmin = Integer.parseInt(S.nextLine());
                } catch (NumberFormatException e) {
                    System.out.print("");
                }
                if (opAdmin < 1 || opAdmin > 6) System.out.println("\n * ERROR AL INTRODUCIR LA OPCIÓN\n");
            } while (opAdmin < 1 || opAdmin > 6);
            switch (opAdmin) {
                case 1:
                    System.out.print(" - Introduzca nombre: ");
                    String nombre = S.nextLine();
                    adminModificar.setNombre(nombre);
                    Utils.pulsaParaContinuar();
                    break;
                case 2:
                    System.out.print(" - Introduzca apellidos: ");
                    String apellidos = S.nextLine();
                    adminModificar.setApellidos(apellidos);
                    Utils.pulsaParaContinuar();
                    break;
                case 3:
                    System.out.print(" - Introduzca correo electrónico: ");
                    String email = S.nextLine();
                    adminModificar.setEmail(email);
                    Utils.pulsaParaContinuar();
                    break;
                case 4:
                    System.out.print(" - Introduzca contraseña: ");
                    String pass = S.nextLine();
                    adminModificar.setPass(pass);
                    Utils.pulsaParaContinuar();
                    break;
                case 5:
                    System.out.print(" - Introduzca telefono: ");
                    String telefono = S.nextLine();
                    adminModificar.setTelefono(telefono);
                    Utils.pulsaParaContinuar();
                    break;
                case 6:
                    controlador.actualizaUsuario(adminModificar);
                    Utils.mensajeSaliendo();
                    break;
            }
        } while (opAdmin != 6);
    }

    //Metodo que modifica los datos de un tecnico
    private static void modificaTecnico(Controlador controlador, Usuario usuarioBuscado) {
        Tecnico tecnicoModificar = (Tecnico) controlador.buscaUsuarioById(usuarioBuscado.getId());
        int opTecnico = 0;
        do {
            do {
                try {
                    System.out.println("Seleccione que desea modificar: ");
                    System.out.print("""
                            1. - Nombre
                            2. - Apellidos
                            3. - Correo electrónico
                            4. - Contraseña
                            5. - Teléfono
                            6. - Nivel de experiencia
                            7. - Salir
                            Opción:\s""");
                    opTecnico = Integer.parseInt(S.nextLine());
                } catch (NumberFormatException e) {
                    System.out.print("");
                }
                if (opTecnico < 1 || opTecnico > 7) System.out.println("\n * ERROR AL INTRODUCIR LA OPCIÓN\n");
            } while (opTecnico < 1 || opTecnico > 7);
            switch (opTecnico) {
                case 1:
                    System.out.print(" - Introduzca nombre: ");
                    String nombre = S.nextLine();
                    tecnicoModificar.setNombre(nombre);
                    Utils.pulsaParaContinuar();
                    break;
                case 2:
                    System.out.print(" - Introduzca apellidos: ");
                    String apellidos = S.nextLine();
                    tecnicoModificar.setApellidos(apellidos);
                    Utils.pulsaParaContinuar();
                    break;
                case 3:
                    System.out.print(" - Introduzca correo electrónico: ");
                    String email = S.nextLine();
                    tecnicoModificar.setEmail(email);
                    Utils.pulsaParaContinuar();
                    break;
                case 4:
                    System.out.print(" - Introduzca contraseña: ");
                    String pass = S.nextLine();
                    tecnicoModificar.setPass(pass);
                    Utils.pulsaParaContinuar();
                    break;
                case 5:
                    System.out.print(" - Introduzca telefono: ");
                    String telefono = S.nextLine();
                    tecnicoModificar.setTelefono(telefono);
                    Utils.pulsaParaContinuar();
                    break;
                case 6:
                    System.out.print(" - Introduzca nivel de experiencia: ");
                    String nivelExp = S.nextLine();
                    tecnicoModificar.setNivelExp(nivelExp);
                    Utils.pulsaParaContinuar();
                    break;
                case 7:
                    controlador.actualizaUsuario(tecnicoModificar);
                    break;
            }
        } while (opTecnico != 7);
    }

    //Metodo que modifica los datos de un cliente
    private static void modificaCliente(Controlador controlador, Usuario usuarioBuscado) {
        Cliente clienteModificar = (Cliente) controlador.buscaUsuarioById(usuarioBuscado.getId());
        int opCliente = 0;
        do {
            do {
                try {
                    System.out.println("Seleccione que desea modificar: ");
                    System.out.print("""
                            1. - Nombre
                            2. - Apellidos
                            3. - Correo electrónico
                            4. - Contraseña
                            5. - Teléfono
                            6. - Dirección
                            7. - Nombre de empresa
                            8. - Salir
                            Opción:\s""");
                    opCliente = Integer.parseInt(S.nextLine());
                } catch (NumberFormatException e) {
                    System.out.print("");
                }
                if (opCliente < 1 || opCliente > 8) System.out.println("\n * ERROR AL INTRODUCIR LA OPCIÓN\n");
            } while (opCliente < 1 || opCliente > 8);
            switch (opCliente) {
                case 1:
                    System.out.print(" - Introduzca nombre: ");
                    String nombre = S.nextLine();
                    clienteModificar.setNombre(nombre);
                    Utils.pulsaParaContinuar();
                    break;
                case 2:
                    System.out.print(" - Introduzca apellidos: ");
                    String apellidos = S.nextLine();
                    clienteModificar.setApellidos(apellidos);
                    Utils.pulsaParaContinuar();
                    break;
                case 3:
                    System.out.print(" - Introduzca correo electrónico: ");
                    String email = S.nextLine();
                    clienteModificar.setEmail(email);
                    Utils.pulsaParaContinuar();
                    break;
                case 4:
                    System.out.print(" - Introduzca contraseña: ");
                    String pass = S.nextLine();
                    clienteModificar.setPass(pass);
                    Utils.pulsaParaContinuar();
                    break;
                case 5:
                    System.out.print(" - Introduzca telefono: ");
                    String telefono = S.nextLine();
                    clienteModificar.setTelefono(telefono);
                    Utils.pulsaParaContinuar();
                    break;
                case 6:
                    System.out.print(" - Introduzca direccion: ");
                    String direccion = S.nextLine();
                    clienteModificar.setDireccion(direccion);
                    Utils.pulsaParaContinuar();
                    break;
                case 7:
                    System.out.print(" - Introduzca nombre de empresa: ");
                    String nombreEmpresa = S.nextLine();
                    clienteModificar.setNombreEmpresa(nombreEmpresa);
                    Utils.pulsaParaContinuar();
                    break;
                case 8:
                    controlador.actualizaUsuario(clienteModificar);
                    break;
            }
        } while (opCliente != 8);
    }

    //Metodo que asigna incidencias a tecnicos
    private static void asignaIncidencias(Controlador controlador, Admin adminLog) {
        String idIncidencia, emailTecnico, respuestaIncidencia = "", respuestaTecnico = "";
        Incidencia incidenciaBuscada = null;
        Tecnico tecnicoBuscado = null;
        do {
            System.out.print(" - Introduzca el ID de la incidencia a asignar (-1 para salir): ");
            idIncidencia = S.nextLine();
            if (idIncidencia.equalsIgnoreCase("-1")) return;
            incidenciaBuscada = controlador.buscaIncidenciaByIdAdmin(idIncidencia, adminLog);
            if (incidenciaBuscada != null) {
                System.out.println(incidenciaBuscada);
                do {
                    System.out.print("¿Es esta la incidencia que buscas (S/N)?: ");
                    respuestaIncidencia = S.nextLine();
                } while (!respuestaIncidencia.equalsIgnoreCase("s") && !respuestaIncidencia.equalsIgnoreCase("n"));
            } else System.out.println("\n* ERROR NO HAY NINGUNA INCIDENCIA CON ESE ID\n");
        } while (!respuestaIncidencia.equalsIgnoreCase("s"));
        do {
            System.out.print(" - Introduzca el correo electrónico del tecnico al que desea asignar (-1 para salir): ");
            emailTecnico = S.nextLine();
            if (emailTecnico.equalsIgnoreCase("-1")) return;
            tecnicoBuscado = controlador.buscaTecnicoByEmail(emailTecnico);
            if (tecnicoBuscado != null) {
                System.out.println(" - Ha seleccionado a " + tecnicoBuscado.getNombre() + " " + tecnicoBuscado.getApellidos() + " - " + tecnicoBuscado.getNivelExp());
                do {
                    System.out.print("¿Es este el tecnico que buscas (S/N)?: ");
                    respuestaTecnico = S.nextLine();
                } while (!respuestaTecnico.equalsIgnoreCase("s") && !respuestaTecnico.equalsIgnoreCase("n"));
            } else System.out.println("\n* ERROR NO HAY NINGUN TECNICO CON ESE EMAIL\n");
        } while (!respuestaTecnico.equalsIgnoreCase("s"));
        if (incidenciaBuscada != null && tecnicoBuscado != null) {
            if (controlador.asignaIncidencia(incidenciaBuscada, tecnicoBuscado))
                System.out.println(" - INCIDENCIA ASIGNADA EXITOSAMENTE");
            else System.out.println(" - ERROR NO SE HA PODIDO ASIGNAR LA INCIDENCIA");
        } else {
            System.out.println("\n* ERROR NO SE PUDO LLEVAR A ACABO LA ASIGNACIÓN\n");
        }
    }

    //Metodo para el admin para ver todas las incidencias
    private static void visualizaIncidencias(Controlador controlador, Admin adminLog) {
        ArrayList<Incidencia> incidencias = controlador.getIncidencias();
        if (incidencias != null) {
            if (!incidencias.isEmpty()) {
                int cont = 1;
                String op;
                for (Incidencia i : incidencias) {
                    System.out.println(i);
                    cont++;
                    if (cont == 5) {
                        System.out.print(" - Pulse ENTER para seguir viendo los demás tickets o introduzca cualquier carácter para salir");
                        op = S.nextLine();
                        if (!op.isEmpty()) return;
                        else cont = 1;
                    }
                }
            } else System.out.println("\n* ERROR NO HAY INCIDENCIAS REGISTRADAS EN EL SISTEMA\n");
        }
        Utils.pulsaParaContinuar();
    }

    //Metodo que pinta y tiene la logica del menu tecnico
    private static void menuTecnico(Controlador controlador, Tecnico tecnicoLog) {
        int op = 0;
        do {
            System.out.println("\n---------- BIENVENIDO " + tecnicoLog.getNombre() + " " + tecnicoLog.getApellidos() + " AL MENÚ DE GESTIÓN----------\n");
            do {
                try {
                    System.out.print("""
                            1. - Ver incidencias asignadas
                            2. - Marcar la incidencia como resuelta (añadir descripción de resolución)
                            3. - Cambiar estado
                            4. - Salir
                            Opcion:\s""");
                    op = Integer.parseInt(S.nextLine());
                } catch (NumberFormatException e) {
                    System.out.print("");
                }
                if (op < 1 || op > 4) System.out.println("\n * ERROR AL INTRODUCIR LA OPCIÓN\n");
            } while (op < 1 || op > 4);
            switch (op) {
                case 1:
                    pintaIncidenciasAsignadas(controlador, tecnicoLog);
                    break;
                case 2:
                    marcaIncidenciaResuelta(controlador, tecnicoLog);
                    break;
                case 3:
                    cambiaEstadoIncidencia(controlador, tecnicoLog);
                    break;
                case 4:
                    controlador.actualizaUsuario(tecnicoLog);
                    Utils.mensajeSaliendo();
                    break;
            }
        } while (op != 4);
    }

    //Metodo para cambiar el estado de una incidencia por un tecnico
    private static void cambiaEstadoIncidencia(Controlador controlador, Tecnico tecnicoLog) {
        int op = 0;
        System.out.print(" - Introduzca el ID de la incidencia a resolver (debe estar bajo tu dominio): ");
        String id = S.nextLine();
        Incidencia incidenciaBuscada = controlador.buscaIncidenciaByIdTecnico(tecnicoLog, id);
        if (incidenciaBuscada != null) {
            System.out.println(incidenciaBuscada);
            Utils.pulsaParaContinuar();
            do {
                try {
                    System.out.println("Seleccione el estado:");
                    System.out.print("""
                            1. - Pendiente
                            2. - En curso
                            3. - Resuelta
                            Opción:\s""");
                    op = Integer.parseInt(S.nextLine());
                } catch (NumberFormatException e) {
                    System.out.print("");
                }
                if (op < 1 || op > 3) System.out.println("\n * ERROR AL INTRODUCIR LA OPCIÓN\n");
            } while (op < 1 || op > 3);
            if (controlador.setEstado(op, incidenciaBuscada)) System.out.println(" - ESTADO CAMBIADO EXITOSAMENTE");
            else System.out.println(" * ERROR NO SE HA PODIDO CAMBIAR EL ESTADO");
            System.out.println();
            System.out.println(incidenciaBuscada);

        } else System.out.println("\n* ERROR NO SE HA ENCONTRADO LA INCIDENCIA\n");

        Utils.pulsaParaContinuar();
    }

    //Metodo para marcar una incidencia como resuelta por un tecnico
    private static void marcaIncidenciaResuelta(Controlador controlador, Tecnico tecnicoLog) {
        System.out.print(" - Introduzca el ID de la incidencia a resolver (debe estar bajo tu dominio): ");
        String id = S.nextLine();
        Incidencia incidenciaBuscada = controlador.buscaIncidenciaByIdTecnico(tecnicoLog, id);
        if (incidenciaBuscada != null) {
            System.out.println(incidenciaBuscada);
            Utils.pulsaParaContinuar();
            System.out.print(" - Introduzca la solución: ");
            String solucion = S.nextLine();
            if (controlador.solucionaIncidencia(solucion, incidenciaBuscada))
                System.out.println(" - INCIDENCIA RESUELTA");
            else System.out.println(" - ERROR NO SE HA PODIDO RESOLVER LA INCIDENCIA");
            String respuesta;
            do {
                System.out.print(" - ¿Desea cambiar el estado de la incidencia a resuelta? (S/N): ");
                respuesta = S.nextLine();
            } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));
            if (respuesta.equalsIgnoreCase("s")) {
                incidenciaBuscada.setEstado("Resuelta");
                if (controlador.actualizaIncidencia(incidenciaBuscada)) System.out.println(" - INCIDENCIA RESUELTA");
                else System.out.println(" * ERROR NO SE HA PODIDO RESOLVER LA INCIDENCIA");
            }
        } else System.out.println("\n* ERROR NO SE HA ENCONTRADO LA INCIDENCIA\n");
        Utils.pulsaParaContinuar();
    }

    //Metodo para pintar las incidencias
    private static void pintaIncidenciasAsignadas(Controlador controlador, Tecnico tecnicoLog) {
        ArrayList<Incidencia> incidenciasAsignadas = controlador.buscaIncidenciasAsignadas(tecnicoLog);
        if (!incidenciasAsignadas.isEmpty()) {
            for (Incidencia i : incidenciasAsignadas) {
                System.out.println(i);
            }
        } else System.out.println(" * ERROR NO HAY INCIDENCIAS ASIGNADAS");
        Utils.pulsaParaContinuar();
    }

    //Metodo que pinta y tiene la logica del menu cliente
    private static void menuCliente(Controlador controlador, Cliente clienteLog) {
        int op = 0;
        do {
            System.out.println("\n---------- BIENVENIDO " + clienteLog.getNombre() + " " + clienteLog.getApellidos() + " AL MENÚ DE GESTIÓN----------\n");
            do {
                try {
                    System.out.print("""
                            1. - Crear una nueva incidencia
                            2. - Consultar historial de incidencias propias
                            3. - Consultar el estado de una incidencia concreta
                            4. - Salir
                            Opcion:\s""");
                    op = Integer.parseInt(S.nextLine());
                } catch (NumberFormatException e) {
                    System.out.print("");
                }
                if (op < 1 || op > 4) System.out.println("\n * ERROR AL INTRODUCIR LA OPCIÓN\n");
            } while (op < 1 || op > 4);
            switch (op) {
                case 1:
                    creaIncidencia(controlador, clienteLog);
                    break;
                case 2:
                    consultaIncidenciasCliente(controlador, clienteLog);
                    break;
                case 3:
                    consultaIncidenciaConcreta(controlador, clienteLog);
                    Utils.pulsaParaContinuar();
                    break;
                case 4:
                    controlador.actualizaUsuario(clienteLog);
                    Utils.mensajeSaliendo();
                    break;
            }
        } while (op != 4);
    }

    //Metodo para crear una incidencia por el cliente
    private static void creaIncidencia(Controlador controlador, Cliente clienteLog) {
        String descripcion;
        System.out.print("Explique el problema que tiene: ");
        descripcion = S.nextLine();
        if (controlador.creaIncidenciaCliente(clienteLog, descripcion))
            System.out.println("INCIDENCIA CREADA EXITOSAMENTE");
        else System.out.println("\n* ERROR AL CREAR LA INCIDENCIA PRUEBE DE NUEVO\n");
        Utils.pulsaParaContinuar();
    }

    //Metodo que consulta una incidencia concreta de un cliente
    private static void consultaIncidenciaConcreta(Controlador controlador, Cliente clienteLog) {
        String id;
        System.out.print(" - Introduzca el ID de la incidencia que desea consultar: ");
        id = S.nextLine();
        Incidencia incidenciaConsulta = controlador.buscaIncidenciaByIdCliente(id, clienteLog);
        if (incidenciaConsulta != null) {
            System.out.println();
            System.out.println(incidenciaConsulta);
        } else System.out.println("\n* ERROR NO SE HA ENCONTRADO ESTA INCIDENCIA\n");
    }

    //Metodo que recoge todas las incidencias de un cliente y las pinta
    private static void consultaIncidenciasCliente(Controlador controlador, Cliente clienteLog) {
        ArrayList<Incidencia> incidenciasCliente = controlador.buscaIncidenciasCliente(clienteLog);
        if (incidenciasCliente.isEmpty()) {
            System.out.println("\n * ERROR NO HAY INCIDENCIAS ENVIADAS POR TI\n");
            Utils.pulsaParaContinuar();
            return;
        } else {
            for (Incidencia i : incidenciasCliente) {
                System.out.println();
                System.out.println(i);
            }
        }
        Utils.pulsaParaContinuar();
    }

    //Metodo para iniciar sesion pide los datos
    private static Usuario iniciarSesion(Controlador controlador) {
        Usuario usuarioLog;
        System.out.print("- Introduzca su correo electrónico: ");
        String email = S.nextLine();
        System.out.print("- Introduzca su contraseña: ");
        String pass = S.nextLine();
        usuarioLog = controlador.inicioSesion(email, pass);
        return usuarioLog;
    }
}
*/
