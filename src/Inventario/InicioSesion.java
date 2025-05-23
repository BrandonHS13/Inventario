package Inventario;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class InicioSesion extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Imagen decorativa superior (logo o restaurante)
        ImageView imgLogo = new ImageView(new Image(getClass().getResourceAsStream("/Inventario/resource/imagenRestaurante.jpg")));
        imgLogo.setFitWidth(100);
        imgLogo.setFitHeight(100);
        imgLogo.setPreserveRatio(true);

        // Título
        Label lblTitulo = new Label("Inicio de sesión");
        lblTitulo.setId("titulo-inicio");

        // Subtítulo
        Label lblSub = new Label("Bienvenido, por favor ingresa tu contraseña para continuar");
        lblSub.getStyleClass().add("subtitulo-inicio");

        // Campo de contraseña
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");
        txtPassword.getStyleClass().add("campo-password");

        // Botón de siguiente
        Button btnSiguiente = new Button("Entrar");
        btnSiguiente.getStyleClass().add("btn-siguiente");

        // Evento de botón
        btnSiguiente.setOnAction(event -> {
            // Aquí puedes validar la contraseña si lo deseas
            Stage menuStage = new Stage();
            menu menuVentana = new menu();
            try {
                menuVentana.start(menuStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ((Stage) btnSiguiente.getScene().getWindow()).close();
        });

        // Contenedor de login
        VBox loginBox = new VBox(24, imgLogo, lblTitulo, lblSub, txtPassword, btnSiguiente);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(40, 32, 40, 32));
        loginBox.setId("login-box");
        loginBox.setMaxWidth(420);

        // Fondo decorativo
        StackPane root = new StackPane(loginBox);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/Inventario/resource/styles.css").toExternalForm());
        primaryStage.setTitle("Inventario | Inicio de sesión");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}