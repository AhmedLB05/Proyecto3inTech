package view;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;

public class MainApp extends Application {

    private static Stage primaryStage;
    private static final Controlador controlador = Controlador.getInstance(); // Singleton

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        showLogin();
        primaryStage.show();
    }

    public static void showLogin() {
        try {
            Parent root = FXMLLoader.load(MainApp.class.getResource("/view/login.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Sistema de Gestión de Incidencias - Login");
            primaryStage.show();
        } catch (Exception e) {
            handleFatalError("Error al cargar la pantalla de login", e);
        }
    }

    public static void showAdminMenu(Usuario usuario) {
        loadSceneWithController("/view/admin_menu.fxml", usuario, "Menú de Administrador");
    }

    public static void showTecnicoMenu(Tecnico tecnico) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/tecnico_menu.fxml"));
            Parent root = loader.load();
            TecnicoController controller = loader.getController();
            controller.setTecnico(tecnico);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Menú de Técnico");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showClienteMenu(Usuario usuario) {
        loadSceneWithController("/view/cliente_menu.fxml", usuario, "Menú de Cliente");
    }


    public static void showGestionUsuarios(Admin admin) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/gestion_usuarios.fxml"));
            Parent root = loader.load();

            GestionUsuariosController controller = loader.getController();
            controller.setAdmin(admin);

            setNewScene(root, "Gestión de Usuarios");
        } catch (Exception e) {
            handleError("Error al cargar gestión de usuarios", e);
        }
    }

    public static void showFormularioUsuario(Admin admin, Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/formulario_usuario.fxml"));
            Parent root = loader.load();

            FormularioUsuarioController controller = loader.getController();
            controller.setDatos(admin, usuario);

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
        } catch (Exception e) {
            handleError("Error al abrir formulario de usuario", e);
        }
    }

    private static void loadSceneWithController(String fxmlPath, Usuario usuario, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();

            if (controller instanceof AdminController) {
                ((AdminController) controller).setAdmin((Admin) usuario);
            } else if (controller instanceof TecnicoController) {
                ((TecnicoController) controller).setTecnico((Tecnico) usuario);
            } else if (controller instanceof ClienteController) {
                ((ClienteController) controller).setCliente((Cliente) usuario);
            } else {
                throw new RuntimeException("Controlador no soportado: " + controller.getClass().getName());
            }

            setNewScene(root, title);
        } catch (Exception e) {
            handleError("Error al cargar la pantalla: " + title, e);
        }
    }

    public static void showFormularioIncidencia(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/formulario_incidencia.fxml"));
            Parent root = loader.load();

            FormularioIncidenciaController controller = loader.getController();
            controller.setCliente(cliente);

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
        } catch (Exception e) {
            handleError("Error al abrir formulario de incidencia", e);
        }
    }

    public static void showAsignarIncidencias(Admin admin) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/asignar_incidencias.fxml"));
            Parent root = loader.load();

            AsignarIncidenciasController controller = loader.getController();
            controller.setAdmin(admin);

            setNewScene(root, "Asignar Incidencias");
        } catch (Exception e) {
            handleError("Error al cargar la asignación de incidencias", e);
        }
    }

    public static void showVerIncidencias(Admin admin) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/ver_incidencias.fxml"));
            Parent root = loader.load();

            VerIncidenciasController controller = loader.getController();
            controller.setAdmin(admin);

            setNewScene(root, "Todas las Incidencias");
        } catch (Exception e) {
            handleError("Error al cargar el listado de incidencias", e);
        }
    }

    public static void showResolverIncidencia(Tecnico tecnico, Incidencia incidencia) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/resolver_incidencia.fxml"));
            Parent root = loader.load();

            ResolverIncidenciaController controller = loader.getController();
            controller.setDatos(tecnico, incidencia);

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
        } catch (Exception e) {
            handleError("Error al abrir resolución de incidencia", e);
        }
    }

    private static void setNewScene(Parent root, String title) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(MainApp.class.getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.centerOnScreen();
    }

    private static void handleError(String message, Exception e) {
        System.err.println(message + ": " + e.getMessage());
        e.printStackTrace();
    }

    private static void handleFatalError(String message, Exception e) {
        handleError(message, e);
        System.exit(1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
