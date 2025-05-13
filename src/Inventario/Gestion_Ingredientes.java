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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.time.LocalDate;

public class Gestion_Ingredientes extends Application {

    private Label lblGestion;
    private Label lblFecha;
    private Button btnAgregar;
    private Button btnEditar;
    private Button btnEliminar;
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
        lblGestion = crearEtiqueta("Gestión de Ingredientes", 30);
        lblFecha = crearEtiqueta("Fecha", 20);

        // Botones
        btnAgregar = crearBoton("Agregar");
        btnEditar = crearBoton("Editar");
        btnEliminar = crearBoton("Eliminar");

        btnEliminar.setOnAction(event -> mostrarConfirmacion("¿Estás seguro de que deseas eliminar este ingrediente?"));

        // Tabla de Ingredientes
        tablaIngredientes = new TableView<>();
        listaIngredientes = FXCollections.observableArrayList();
        cargarDatosIngredientes(); // Llenar la lista con datos iniciales
        crearColumnasIngredientes(); // Definir las columnas de la tabla
        tablaIngredientes.setItems(listaIngredientes);
    }

    private VBox organizarLayout() {
        // Campo de texto para buscar ingredientes
        TextField filtro = new TextField();
        filtro.setPromptText("Buscar ingrediente...");
        filtro.textProperty().addListener((observable, oldValue, newValue) ->
                tablaIngredientes.setItems(FXCollections.observableArrayList(
                        listaIngredientes.filtered(ingrediente ->
                                ingrediente.getNombre().toLowerCase().contains(newValue.toLowerCase())
                        )
                ))
        );

        // Botonera
        HBox botonera = new HBox(10, btnAgregar, btnEditar, btnEliminar);
        botonera.setAlignment(Pos.CENTER);

        // Layout principal
        VBox layoutPrincipal = new VBox(15, lblGestion, filtro, tablaIngredientes, botonera);
        layoutPrincipal.setPadding(new Insets(15));
        layoutPrincipal.setAlignment(Pos.TOP_CENTER);

        return layoutPrincipal;
    }

    private void configurarEscena(Stage primaryStage, VBox layoutPrincipal) {
        Scene scene = new Scene(layoutPrincipal, 950, 680);
        primaryStage.setTitle("Inventario");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label crearEtiqueta(String texto, double tamañoFuente) {
        Label etiqueta = new Label(texto);
        etiqueta.setFont(Font.font(tamañoFuente));
        return etiqueta;
    }

    private Button crearBoton(String texto) {
        Button btn = new Button(texto);
        btn.setFont(Font.font(17));
        return btn;
    }

    private void crearColumnasIngredientes() {
        // Columna Nombre
        TableColumn<Ingrediente, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        columnaNombre.setPrefWidth(150);

        // Columna Cantidad
        TableColumn<Ingrediente, String> columnaCantidad = new TableColumn<>("Cantidad");
        columnaCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
        columnaCantidad.setPrefWidth(80);

        // Columna Unidad
        TableColumn<Ingrediente, String> columnaUnidad = new TableColumn<>("Unidad");
        columnaUnidad.setCellValueFactory(cellData -> cellData.getValue().unidadProperty());
        columnaUnidad.setPrefWidth(100);

        // Columna Fecha de Caducidad
        TableColumn<Ingrediente, LocalDate> columnaFechaCaducidad = new TableColumn<>("Fecha de Caducidad");
        columnaFechaCaducidad.setCellValueFactory(cellData -> cellData.getValue().fechaCaducidadProperty());
        columnaFechaCaducidad.setPrefWidth(150);

        // Columna Categoría
        TableColumn<Ingrediente, String> columnaCategoria = new TableColumn<>("Categoría");
        columnaCategoria.setCellValueFactory(cellData -> cellData.getValue().categoriaProperty());
        columnaCategoria.setPrefWidth(120);

        // Columna Costo
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