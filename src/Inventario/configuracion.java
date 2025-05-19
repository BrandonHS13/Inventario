package Inventario;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class configuracion extends Application {

    private Font tamañoFuente;
    private Button btnUnidadesMed;
    private Button btnAlertas;
    private Button btnPersonalizacion;
    private Button btnReturn;

    @Override
    public void start(Stage primaryStage) {
        inicializarComponentes();
        VBox layoutPrincipal = organizarLayout();
        configurarEscena(primaryStage, layoutPrincipal);
    }

    private void inicializarComponentes() {
        // Fuente para el título
        tamañoFuente = Font.font("Arial", 24);

        // Botones
        btnUnidadesMed = crearBoton("Configurar unidades de medida");
        btnAlertas = crearBoton("Configurar alertas de stock bajo");
        btnPersonalizacion = crearBoton("Personalización del perfil");
        btnReturn = new Button("<--");
        btnReturn.setPrefWidth(40); // Más pequeño
        btnReturn.setFont(Font.font(14)); // Más pequeño

        // Acciones de los botones
        btnUnidadesMed.setOnAction(event -> mostrarMensaje("Configurar Unidades de Medida", "Aquí podrás configurar las unidades de medida."));
        btnAlertas.setOnAction(event -> mostrarMensaje("Configurar Alertas", "Aquí podrás configurar las alertas de stock bajo."));
        btnPersonalizacion.setOnAction(event -> mostrarMensaje("Personalización", "Aquí podrás personalizar tu perfil."));
        btnReturn.setOnAction(event -> regresarAlMenu());
    }

    private Button crearBoton(String texto) {
        Button boton = new Button(texto);
        boton.setPrefWidth(300);
        boton.setFont(Font.font(16));
        return boton;
    }

    private VBox organizarLayout() {
        // Barra superior con botón Return pequeño y en la esquina
        HBox topBar = new HBox(btnReturn);
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setPadding(new Insets(0, 0, 0, 0)); // Sin padding para esquina
        // Etiqueta del título
        Label lblTitulo = new Label("Configuración");
        lblTitulo.setFont(tamañoFuente);

        // Layout principal
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(topBar, lblTitulo, btnUnidadesMed, btnAlertas, btnPersonalizacion);

        return layout;
    }

    private void regresarAlMenu() {
        Stage stage = (Stage) btnReturn.getScene().getWindow();
        stage.close();
        try {
            new menu().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void configurarEscena(Stage primaryStage, VBox layoutPrincipal) {
        Scene scene = new Scene(layoutPrincipal, 400, 400);
        primaryStage.setTitle("Configuración");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}