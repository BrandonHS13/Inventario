package Inventario;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class configuracion extends Application {

    private Font TamañoFuente; // Corregido a Font
    private Button btnUnidadesMed; // Acortado como en el diagrama
    private Button btnAlertas; // Acortado como en el diagrama
    private Button btnPersonalizacion; // Coincide con el diagrama

    @Override
    public void start(Stage primaryStage) {
        inicializarComponentes(primaryStage);
        organizarLayout();
        configurarEscena(primaryStage);
    }

    private void inicializarComponentes(Stage primaryStage) {
        TamañoFuente = Font.font("Arial", 24); // Inicializar la fuente

        Label lblTitulo = new Label("Configuracion");
        lblTitulo.setFont(TamañoFuente);

        btnUnidadesMed = crearBoton("Configurar unidades de medida", 250);
        btnAlertas = crearBoton("Configurar alertas de stock bajo", 250);
        btnPersonalizacion = crearBoton("Personalizacion del perfil", 250);

        primaryStage.setTitle("Configuracion"); // Establecer el título aquí también
    }

    private Button crearBoton(String texto, double ancho) {
        Button boton = new Button(texto);
        boton.setPrefWidth(ancho);
        return boton;
    }

    private void organizarLayout() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));
        layout.getChildren().addAll(new Label("Configuracion") {{ setFont(TamañoFuente); }}, btnUnidadesMed, btnAlertas, btnPersonalizacion);
        this.root = layout; // Asignar el layout a una variable root si la necesitas en configurarEscena
    }

    private VBox root; // Variable para el layout raíz

    private void configurarEscena(Stage primaryStage) {
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*public static void main(String[] args) {
        launch(args);
    }*/
}