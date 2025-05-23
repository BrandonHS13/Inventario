package Inventario;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class menu extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Imagen decorativa superior derecha, bajada con TranslateY
        ImageView imgLogo = new ImageView(new Image(getClass().getResourceAsStream("/Inventario/resource/imagenRestaurante.jpg")));
        imgLogo.setFitWidth(80);
        imgLogo.setFitHeight(80);
        imgLogo.setPreserveRatio(true);
        imgLogo.setOpacity(0.92); // Un poco translúcida para que no distraiga
        imgLogo.setTranslateY(40); // Baja la imagen 40px desde arriba

        // Alinea la imagen en la esquina superior derecha
        StackPane.setAlignment(imgLogo, Pos.TOP_RIGHT);
        StackPane.setMargin(imgLogo, new Insets(0, 30, 0, 0)); // 30px desde la derecha

        // Título y subtítulo del menú
        Label lblBienvenido = new Label("Bienvenido al sistema de inventario");
        lblBienvenido.getStyleClass().add("titulo-menu");

        Label lblQDesea = new Label("¿Qué desea hacer?");
        lblQDesea.getStyleClass().add("subtitulo-menu");

        // Panel para las opciones
        GridPane grid = new GridPane();
        grid.setHgap(32);
        grid.setVgap(32);
        grid.setAlignment(Pos.CENTER);

        // Opciones del menú (botón con imagen y texto)
        Button btnGestionIngredientes = crearBotonMenu("/Inventario/resource/Ingredientes.jpg", "Gestión de Ingredientes");
        Button btnRegistroProvedores = crearBotonMenu("/Inventario/resource/Provedores.jpg", "Registro de Proveedores");
        Button btnReportes = crearBotonMenu("/Inventario/resource/Reporte.jpg", "Reportes y Análisis");
        Button btnConfig = crearBotonMenu("/Inventario/resource/Configuracion.jpg", "Configuración");

        // Acciones
        btnGestionIngredientes.setOnAction(event -> {
            Gestion_Ingredientes gestionIngredientes = new Gestion_Ingredientes();
            Stage gestionStage = new Stage();
            try { gestionIngredientes.start(gestionStage); } catch (Exception e) { e.printStackTrace(); }
            primaryStage.hide();
        });

        btnRegistroProvedores.setOnAction(event -> {
            registro_Provedores registroProvedores = new registro_Provedores();
            Stage registroStage = new Stage();
            try { registroProvedores.start(registroStage); } catch (Exception e) { e.printStackTrace(); }
            primaryStage.hide();
        });

        btnReportes.setOnAction(event -> {
            Reportes reportes = new Reportes();
            Stage reportesStage = new Stage();
            try { reportes.start(reportesStage); } catch (Exception e) { e.printStackTrace(); }
            primaryStage.hide();
        });

        btnConfig.setOnAction(event -> {
            configuracion config = new configuracion();
            Stage configStage = new Stage();
            try { config.start(configStage); } catch (Exception e) { e.printStackTrace(); }
            primaryStage.hide();
        });

        // Añadir botones al grid
        grid.add(btnGestionIngredientes, 0, 0);
        grid.add(btnRegistroProvedores, 1, 0);
        grid.add(btnReportes, 0, 1);
        grid.add(btnConfig, 1, 1);

        // Panel principal del menú, centrado y elegante
        VBox panelMenu = new VBox(5, lblBienvenido, lblQDesea, grid);
        panelMenu.setAlignment(Pos.CENTER);
        panelMenu.setPadding(new Insets(42, 32, 42, 32));
        panelMenu.setId("panel-menu");
        panelMenu.setMaxWidth(700);

        // StackPane como raíz: panel blanco centrado + imagen flotante en esquina superior derecha
        StackPane root = new StackPane(panelMenu, imgLogo);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/Inventario/resource/styles.css").toExternalForm());

        primaryStage.setTitle("Inventario - Menú");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Crea un botón de menú con imagen grande y texto debajo, ambos centrados.
     */
    private Button crearBotonMenu(String imgPath, String textoBoton) {
        Image img = new Image(getClass().getResourceAsStream(imgPath));
        ImageView icon = new ImageView(img);
        icon.setFitWidth(90);
        icon.setFitHeight(90);
        icon.setPreserveRatio(true);

        Button btn = new Button(textoBoton, icon);
        btn.getStyleClass().add("menu-btn");
        btn.setContentDisplay(ContentDisplay.TOP); // Imagen arriba, texto abajo
        btn.setWrapText(true);
        btn.setPrefWidth(220);
        btn.setMinHeight(180);
        btn.setAlignment(Pos.CENTER);
        return btn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}