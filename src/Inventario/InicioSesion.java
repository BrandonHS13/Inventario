package Inventario;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class InicioSesion extends Application {

    private Font tamañoFuente;
    private Button btnSiguiente;
    private Label lblInicioSesion;
    private Label lblContraseña;
    private Image imgInicioSesion;
    private Image imgRestaurante;
    private ImageView imgViewInicioSesion;
    private ImageView imgViewRestaurante;
    private AnchorPane root;
    private TextField btxt;

    @Override
    public void start(Stage primaryStage) {
        inicializarComponentes();
        organizarLayout();
        configurarEscena(primaryStage);
    }

    private void inicializarComponentes() {
        tamañoFuente = Font.font(20);

        // Botón de siguiente
        btnSiguiente = crearBotonSiguiente(880, 395);

        // Etiquetas
        lblInicioSesion = crearEtiqueta("Inicio de sesión", 650, 70);
        lblContraseña = crearEtiqueta("Ingresa la contraseña", 630, 350);

        // Cargar imágenes
        imgInicioSesion = cargarImagen("/Inventario/resource/Inicio de secion.jpg");
        imgRestaurante = cargarImagen("/Inventario/resource/imagenRestaurante.jpg");

        // ImageViews
        imgViewInicioSesion = crearImageView(imgInicioSesion, 250, 200, 600, 110);
        imgViewRestaurante = crearImageView(imgRestaurante, 400, 350, 60, 70);

        //Barra de text
        btxt = barraTexto(250, 35, 600, 390);
        root = new AnchorPane();

        // Configurar la acción del botón "Siguiente"
        btnSiguiente.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage menuStage = new Stage();
                menu menuVentana = new menu();
                try {
                    menuVentana.start(menuStage);
                } catch (Exception e) {
                    e.printStackTrace(); // Manejar cualquier error al iniciar el menú
                }

                // Opcional: Cerrar la ventana de inicio de sesión
                Stage inicioSesionStage = (Stage) btnSiguiente.getScene().getWindow();
                inicioSesionStage.close();
            }
        });
    }

    private Button crearBotonSiguiente(double PosicionX, double PosicionY) {
        Button btn = new Button("-->");
        btn.setTranslateX(PosicionX);
        btn.setTranslateY(PosicionY);
        return btn;
    }

    private Label crearEtiqueta(String texto, double translateX, double translateY) {
        Label etiqueta = new Label(texto);
        etiqueta.setTranslateX(translateX);
        etiqueta.setTranslateY(translateY);
        etiqueta.setFont(tamañoFuente);
        return etiqueta;
    }

    private Image cargarImagen(String ruta) {
        return new Image(getClass().getResourceAsStream(ruta));
    }

    private TextField barraTexto(double ancho, double alto, double PosicionX, double PosicionY) {
        TextField barra = new TextField();
        barra.setPrefWidth(ancho);
        barra.setPrefHeight(alto);
        barra.setTranslateX(PosicionX);
        barra.setTranslateY(PosicionY);
        return barra;
    }

    private ImageView crearImageView(Image imagen, double fitWidth, double fitHeight, double translateX, double translateY) {
        ImageView imageView = new ImageView(imagen);
        imageView.setFitWidth(fitWidth);
        imageView.setFitHeight(fitHeight);
        imageView.setTranslateX(translateX);
        imageView.setTranslateY(translateY);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private void organizarLayout() {
        root.getChildren().addAll(btnSiguiente, imgViewInicioSesion, imgViewRestaurante, lblInicioSesion, lblContraseña, btxt);
    }

    private void configurarEscena(Stage primaryStage) {
        Scene scene = new Scene(root, 950, 600);
        primaryStage.setTitle("Inventario");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(InicioSesion.class, args); // Asegúrate de que la aplicación inicie desde Inicio_sesion
    }
}