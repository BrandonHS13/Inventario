package Inventario;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class Gestion_Ingredientes extends Application {

    private Label lblGestion;
    private Button btnAgregar;
    private Button btnEditar;
    private Button btnEliminar;
    private Button btnReturn;
    private TableView<Ingrediente> tablaIngredientes;
    private ObservableList<Ingrediente> listaIngredientes;

    @Override
    public void start(Stage primaryStage) {
        inicializarComponentes();
        VBox layoutPrincipal = organizarLayout();
        configurarEscena(primaryStage, layoutPrincipal);
    }

    private void inicializarComponentes() {
        // Etiquetas
        lblGestion = new Label("Gestión de Ingredientes");
        lblGestion.getStyleClass().add("titulo-menu");

        // Botones con ID únicos para CSS
        btnAgregar = new Button("Agregar");
        btnAgregar.setId("btn-agregar");

        btnEditar = new Button("Editar");
        btnEditar.setId("btn-editar");

        btnEliminar = new Button("Eliminar");
        btnEliminar.setId("btn-eliminar");

        btnReturn = new Button("<--");
        btnReturn.setId("btn-retornar");

        // Botón regresar
        btnReturn.setOnAction(event -> {
            Stage currentStage = (Stage) btnReturn.getScene().getWindow();
            currentStage.close();
            try {
                new menu().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnEliminar.setOnAction(event -> mostrarConfirmacion("¿Estás seguro de que deseas eliminar este ingrediente?"));

        // Tabla de Ingredientes
        tablaIngredientes = new TableView<>();
        tablaIngredientes.getStyleClass().add("table-view");
        tablaIngredientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Para que la tabla ocupe todo el espacio disponible
        tablaIngredientes.setMaxHeight(Double.MAX_VALUE);

        listaIngredientes = FXCollections.observableArrayList();
        cargarDatosIngredientes();
        crearColumnasIngredientes();
        tablaIngredientes.setItems(listaIngredientes);
    }

    private VBox organizarLayout() {
        // Campo de texto para buscar ingredientes
        TextField filtro = new TextField();
        filtro.setPromptText("Buscar ingrediente...");
        filtro.getStyleClass().add("campo-busqueda");
        filtro.textProperty().addListener((observable, oldValue, newValue) ->
                tablaIngredientes.setItems(FXCollections.observableArrayList(
                        listaIngredientes.filtered(ingrediente ->
                                ingrediente.getNombre().toLowerCase().contains(newValue.toLowerCase())
                        )
                ))
        );

        // Botonera
        HBox botonera = new HBox(16, btnReturn, btnAgregar, btnEditar, btnEliminar);
        botonera.setAlignment(Pos.CENTER);
        botonera.setPadding(new Insets(20, 0, 0, 0));

        // Layout principal
        VBox layoutPrincipal = new VBox(20, lblGestion, filtro, tablaIngredientes, botonera);
        layoutPrincipal.setPadding(new Insets(38));
        layoutPrincipal.setAlignment(Pos.TOP_CENTER);
        layoutPrincipal.setId("panel-Gestion");

        // Para que la tabla crezca y use el espacio disponible
        VBox.setVgrow(tablaIngredientes, Priority.ALWAYS);

        return layoutPrincipal;
    }

    private void configurarEscena(Stage primaryStage, VBox layoutPrincipal) {
        Scene scene = new Scene(layoutPrincipal, 950, 680);
        scene.getStylesheets().add(getClass().getResource("/Inventario/resource/styles.css").toExternalForm());
        primaryStage.setTitle("Inventario");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void crearColumnasIngredientes() {
        TableColumn<Ingrediente, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        columnaNombre.setPrefWidth(150);

        TableColumn<Ingrediente, String> columnaCantidad = new TableColumn<>("Cantidad");
        columnaCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
        columnaCantidad.setPrefWidth(80);

        TableColumn<Ingrediente, String> columnaUnidad = new TableColumn<>("Unidad");
        columnaUnidad.setCellValueFactory(cellData -> cellData.getValue().unidadProperty());
        columnaUnidad.setPrefWidth(100);

        TableColumn<Ingrediente, LocalDate> columnaFechaCaducidad = new TableColumn<>("Fecha de Caducidad");
        columnaFechaCaducidad.setCellValueFactory(cellData -> cellData.getValue().fechaCaducidadProperty());
        columnaFechaCaducidad.setPrefWidth(150);

        TableColumn<Ingrediente, String> columnaCategoria = new TableColumn<>("Categoría");
        columnaCategoria.setCellValueFactory(cellData -> cellData.getValue().categoriaProperty());
        columnaCategoria.setPrefWidth(120);

        TableColumn<Ingrediente, String> columnaCosto = new TableColumn<>("Costo");
        columnaCosto.setCellValueFactory(cellData -> cellData.getValue().costoProperty());
        columnaCosto.setPrefWidth(80);

        tablaIngredientes.getColumns().addAll(columnaNombre, columnaCantidad, columnaUnidad, columnaFechaCaducidad, columnaCategoria, columnaCosto);
    }

    private void cargarDatosIngredientes() {
        listaIngredientes.addAll(
                new Ingrediente("Harina", "2", "kg", LocalDate.of(2025, 12, 31), "Otro", 15.00),
                new Ingrediente("Azúcar", "1.5", "kg", LocalDate.of(2026, 3, 15), "Otro", 20.50),
                new Ingrediente("Leche", "5", "litros", LocalDate.of(2025, 5, 10), "Lácteo", 10.75),
                new Ingrediente("Huevos", "30", "unidades", LocalDate.of(2025, 5, 20), "Otro", 8.20)
        );
    }

    private void mostrarConfirmacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("Acción confirmada");
            }
        });
    }

    public static class Ingrediente {
        private final SimpleStringProperty nombre;
        private final SimpleStringProperty cantidad;
        private final SimpleStringProperty unidad;
        private final ObjectProperty<LocalDate> fechaCaducidad;
        private final SimpleStringProperty categoria;
        private final SimpleStringProperty costo;

        public Ingrediente(String nombre, String cantidad, String unidad, LocalDate fechaCaducidad, String categoria, double costo) {
            this.nombre = new SimpleStringProperty(nombre);
            this.cantidad = new SimpleStringProperty(cantidad);
            this.unidad = new SimpleStringProperty(unidad);
            this.fechaCaducidad = new SimpleObjectProperty<>(fechaCaducidad);
            this.categoria = new SimpleStringProperty(categoria);
            this.costo = new SimpleStringProperty(String.valueOf(costo));
        }

        public String getNombre() { return nombre.get(); }
        public SimpleStringProperty nombreProperty() { return nombre; }

        public String getCantidad() { return cantidad.get(); }
        public SimpleStringProperty cantidadProperty() { return cantidad; }

        public String getUnidad() { return unidad.get(); }
        public SimpleStringProperty unidadProperty() { return unidad; }

        public LocalDate getFechaCaducidad() { return fechaCaducidad.get(); }
        public ObjectProperty<LocalDate> fechaCaducidadProperty() { return fechaCaducidad; }

        public String getCategoria() { return categoria.get(); }
        public SimpleStringProperty categoriaProperty() { return categoria; }

        public String getCosto() { return costo.get(); }
        public SimpleStringProperty costoProperty() { return costo; }
    }

    public static void main(String[] args) {
        launch(InicioSesion.class,args);
    }
}