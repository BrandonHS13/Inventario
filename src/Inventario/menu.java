package Inventario;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class menu extends Application {

    private Font tamañoFuente;
    private Label lblBienvenido;
    private Label lblQDesea;
    private Button btnGestionIngredientes;
    private Button btnRegistroProvedores;
    private Button btnReportes;
    private Button btnConfig;
    private Image imgGestion;
    private Image imgRegistro;
    private Image imgReporte;
    private Image imgConfig;
    private ImageView imgViewGestion;
    private ImageView imgViewRegistro;
    private ImageView imgViewReporte;
    private ImageView imgViewConfig;
    private AnchorPane root;

    @Override
    public void start(Stage primaryStage) {
        inicializarComponentes(primaryStage); // Pasar el primaryStage al inicializador
        organizarLayout();
        configurarEscena(primaryStage);
    }

    private void inicializarComponentes(Stage menuStage) { // Recibir el Stage del menú
        tamañoFuente = Font.font(35);

        // Etiquetas
        lblBienvenido = crearEtiqueta("Bienvenido al sistema de inventario", 200, 25);
        lblQDesea = crearEtiqueta("¿Que desa hacer?", 350, 70);

        // Cargar imágenes
        imgGestion = cargarImagen("/Inventario/resource/Ingredientes.jpg");
        imgRegistro = cargarImagen("/Inventario/resource/Provedores.jpg");
        imgReporte = cargarImagen("/Inventario/resource/Reporte.jpg");
        imgConfig = cargarImagen("/Inventario/resource/Configuracion.jpg");

        // ImageViews
        imgViewGestion = crearImageView(imgGestion, 200, 200, 150, 150);
        imgViewRegistro = crearImageView(imgRegistro, 200, 150, 600, 150);
        imgViewReporte = crearImageView(imgReporte, 200, 150, 150, 400);
        imgViewConfig = crearImageView(imgConfig, 200, 150, 600, 400);

        // Botones
        btnGestionIngredientes = crearBoton(150, 320, "Gestion de ingredientes");
        btnRegistroProvedores = crearBoton(600, 320, "Registro de provedores");
        btnReportes = crearBoton(160, 570, "Reportes y analisis");
        btnConfig = crearBoton(620, 570, "Configuracion");

        // Configurar las acciones de los botones
        btnGestionIngredientes.setOnAction(event -> {
            Gestion_Ingredientes gestionIngredientes = new Gestion_Ingredientes();
            Stage gestionStage = new Stage();
            try {
                gestionIngredientes.start(gestionStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            menuStage.hide(); // Opcional: Ocultar el menú al abrir otra ventana
        });

        btnRegistroProvedores.setOnAction(event -> {
            registro_Provedores registroProvedores = new registro_Provedores();
            Stage registroStage = new Stage();
            try {
                registroProvedores.start(registroStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            menuStage.hide(); // Opcional: Ocultar el menú
        });

        btnReportes.setOnAction(event -> {
            Reportes reportes = new Reportes();
            Stage reportesStage = new Stage();
            try {
                reportes.start(reportesStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            menuStage.hide(); // Opcional: Ocultar el menú
        });

        btnConfig.setOnAction(event -> {
            configuracion config = new configuracion();
            Stage configStage = new Stage();
            try {
                config.start(configStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            menuStage.hide(); // Opcional: Ocultar el menú
        });

        //Barra de text
        root = new AnchorPane();
    }

    private Button crearBoton(double PosicionX, double PosicionY, String Text) {
        Button btn = new Button(Text);
        Font otraFuenteBoton = Font.font(17);
        btn.setTranslateX(PosicionX);
        btn.setTranslateY(PosicionY);
        btn.setFont(otraFuenteBoton);
        return btn;
    }

    private Label crearEtiqueta(String texto, double PosicionX, double PosicionY) {
        Label etiqueta = new Label(texto);
        etiqueta.setTranslateX(PosicionX);
        etiqueta.setTranslateY(PosicionY);
        etiqueta.setFont(tamañoFuente);
        return etiqueta;
    }

    private Image cargarImagen(String ruta) {
        return new Image(getClass().getResourceAsStream(ruta));
    }

    private ImageView crearImageView(Image imagen, double Ancho, double Alto, double PosicionX, double PosicionY) {
        ImageView imageView = new ImageView(imagen);
        imageView.setFitWidth(Ancho);
        imageView.setFitHeight(Alto);
        imageView.setTranslateX(PosicionX);
        imageView.setTranslateY(PosicionY);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private void organizarLayout() {
        root.getChildren().addAll(lblBienvenido, lblQDesea, imgViewGestion, imgViewRegistro,
                imgViewReporte, imgViewConfig, btnGestionIngredientes, btnRegistroProvedores,
                btnReportes, btnConfig);
    }

    private void configurarEscena(Stage primaryStage) {
        Scene scene = new Scene(root, 950, 680);
        primaryStage.setTitle("Inventario - Menú");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(InicioSesion.class,args);
    }
}