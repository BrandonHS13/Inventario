package Inventario;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Reportes extends Application {

    private Label lblReportes;
    private Label lblFecha_Ini;
    private Label lblFecha_Fin;
    private TextField txtFecha_Ini;
    private TextField txtFecha_Fin;
    private Button btnGenerar;
    private Button btnReturn;

    @Override
    public void start(Stage primaryStage) {
        inicializarComponentes();
        VBox layoutPrincipal = organizarLayout();
        configurarEscena(primaryStage, layoutPrincipal);
    }

    private void inicializarComponentes() {
        // Etiquetas
        lblReportes = new Label("Reportes y análisis");
        lblReportes.setFont(Font.font("Arial", 30));
        lblReportes.setAlignment(Pos.CENTER);

        lblFecha_Ini = new Label("Fecha inicio (AAAA-MM-DD):");
        lblFecha_Ini.setFont(Font.font(16));
        lblFecha_Fin = new Label("Fecha final (AAAA-MM-DD):");
        lblFecha_Fin.setFont(Font.font(16));

        // Campos de texto
        txtFecha_Ini = new TextField();
        txtFecha_Ini.setPromptText("Ejemplo: 2025-01-01");
        txtFecha_Fin = new TextField();
        txtFecha_Fin.setPromptText("Ejemplo: 2025-12-31");

        // Botones
        btnGenerar = new Button("Generar reporte en PDF");
        btnGenerar.setFont(Font.font(16));
        btnGenerar.setOnAction(event -> generarReporte());

        btnReturn = new Button("<--");
        btnReturn.setFont(Font.font(16));
        btnReturn.setOnAction(event -> regresarAlMenu());
    }

    private VBox organizarLayout() {
        // Barra superior con botón de retorno
        HBox topBar = new HBox(btnReturn);
        topBar.setAlignment(Pos.TOP_LEFT);
        

        // Sección de fechas
        HBox fechasLayout = new HBox(20, 
            new VBox(10, lblFecha_Ini, txtFecha_Ini), 
            new VBox(10, lblFecha_Fin, txtFecha_Fin)
        );
        fechasLayout.setAlignment(Pos.CENTER);

        // Layout principal
        VBox layoutPrincipal = new VBox(20, topBar, lblReportes, fechasLayout, btnGenerar);
        layoutPrincipal.setPadding(new Insets(20));
        layoutPrincipal.setAlignment(Pos.TOP_CENTER);

        return layoutPrincipal;
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
        Scene scene = new Scene(layoutPrincipal, 950, 600);
        primaryStage.setTitle("Inventario - Reportes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void generarReporte() {
        try {
            LocalDate fechaInicio = validarFecha(txtFecha_Ini.getText());
            LocalDate fechaFin = validarFecha(txtFecha_Fin.getText());

            if (fechaInicio.isAfter(fechaFin)) {
                mostrarAlerta("Error", "La fecha de inicio no puede ser posterior a la fecha final.", Alert.AlertType.ERROR);
                return;
            }

            mostrarAlerta("Éxito", "El reporte se ha generado correctamente.", Alert.AlertType.INFORMATION);

        } catch (DateTimeParseException e) {
            mostrarAlerta("Error", "Formato de fecha inválido. Use YYYY-MM-DD.", Alert.AlertType.ERROR);
        }
    }

    private LocalDate validarFecha(String fecha) throws DateTimeParseException {
        return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}