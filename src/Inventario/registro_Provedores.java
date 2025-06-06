package Inventario;

import javafx.application.Application;
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

public class registro_Provedores extends Application {

    private TableView<Proveedor> tablaProvedores;
    private ObservableList<Proveedor> listaProvedores;
    private Button btnAgregar;
    private Button btnEditar;
    private Button btnEliminar;
    private Button btnReturn;

    public static class Proveedor {
        private final SimpleStringProperty nombre;
        private final SimpleStringProperty contacto;
        private final SimpleStringProperty producto;

        public Proveedor(String nombre, String contacto, String producto) {
            this.nombre = new SimpleStringProperty(nombre);
            this.contacto = new SimpleStringProperty(contacto);
            this.producto = new SimpleStringProperty(producto);
        }

        public String getNombre() { return nombre.get(); }
        public SimpleStringProperty nombreProperty() { return nombre; }
        public String getContacto() { return contacto.get(); }
        public SimpleStringProperty contactoProperty() { return contacto; }
        public String getProducto() { return producto.get(); }
        public SimpleStringProperty productoProperty() { return producto; }
    }

    @Override
    public void start(Stage primaryStage) {
        // Etiqueta del título
        Label lblTitulo = new Label("Registro de Proveedores");
        lblTitulo.getStyleClass().add("titulo-menu");
        lblTitulo.setAlignment(Pos.CENTER);

        // Campo de texto para buscar proveedores
        TextField txtFiltro = new TextField();
        txtFiltro.setPromptText("Buscar proveedor...");
        txtFiltro.getStyleClass().add("campo-busqueda");
        txtFiltro.textProperty().addListener((observable, oldValue, newValue) ->
                tablaProvedores.setItems(FXCollections.observableArrayList(
                        listaProvedores.filtered(proveedor ->
                                proveedor.getNombre().toLowerCase().contains(newValue.toLowerCase())
                        )
                ))
        );

        // Inicializar la tabla y la lista de proveedores
        tablaProvedores = new TableView<>();
        tablaProvedores.getStyleClass().add("table-view");
        tablaProvedores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaProvedores.setMaxHeight(Double.MAX_VALUE);

        listaProvedores = FXCollections.observableArrayList();
        cargarDatosProvedores();
        crearColumnasTabla();

        // Botones con estilos
        btnAgregar = crearBoton("Agregar", "btn-agregar");
        btnEditar = crearBoton("Editar", "btn-editar");
        btnEliminar = crearBoton("Eliminar", "btn-eliminar");
        btnReturn = crearBoton("<--", "btn-retornar");

        // Acciones de los botones
        btnAgregar.setOnAction(event -> agregarProveedor());
        btnEditar.setOnAction(event -> editarProveedor());
        btnEliminar.setOnAction(event -> eliminarProveedor());
        btnReturn.setOnAction(event -> regresarAlMenu());

        // Contenedor para los botones
        HBox botonesLayout = new HBox(16, btnReturn, btnAgregar, btnEditar, btnEliminar);
        botonesLayout.setAlignment(Pos.CENTER);
        botonesLayout.setPadding(new Insets(20, 0, 0, 0));

        // Layout principal con ID único para fondo
        VBox layoutPrincipal = new VBox(20, lblTitulo, txtFiltro, tablaProvedores, botonesLayout);
        layoutPrincipal.setPadding(new Insets(38));
        layoutPrincipal.setAlignment(Pos.TOP_CENTER);
        layoutPrincipal.setId("panel-menu-proveedores"); // ID único para el fondo

        VBox.setVgrow(tablaProvedores, Priority.ALWAYS);

        Scene scene = new Scene(layoutPrincipal, 950, 680);
        scene.getStylesheets().add(getClass().getResource("/Inventario/resource/styles.css").toExternalForm());
        primaryStage.setTitle("Registro de Proveedores");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void crearColumnasTabla() {
        TableColumn<Proveedor, String> nombreCol = new TableColumn<>("Nombre");
        nombreCol.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        nombreCol.setPrefWidth(200);

        TableColumn<Proveedor, String> contactoCol = new TableColumn<>("Contacto");
        contactoCol.setCellValueFactory(cellData -> cellData.getValue().contactoProperty());
        contactoCol.setPrefWidth(150);

        TableColumn<Proveedor, String> productoCol = new TableColumn<>("Producto que suministra");
        productoCol.setCellValueFactory(cellData -> cellData.getValue().productoProperty());
        productoCol.setPrefWidth(250);

        tablaProvedores.getColumns().addAll(nombreCol, contactoCol, productoCol);
        tablaProvedores.setItems(listaProvedores);
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

    private void cargarDatosProvedores() {
        listaProvedores.addAll(
                new Proveedor("Proveedor A", "contacto@proveedora.com", "Insumos básicos"),
                new Proveedor("Proveedor B", "ventas@proveedorb.net", "Equipamiento"),
                new Proveedor("Proveedor C", "pedidos@proveedorc.org", "Materia prima")
        );
    }

    private Button crearBoton(String texto, String id) {
        Button boton = new Button(texto);
        boton.setFont(Font.font(16));
        boton.setId(id);
        return boton;
    }

    private void agregarProveedor() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Agregar Proveedor");
        dialog.setHeaderText(null);
        dialog.setContentText("Nombre del Proveedor:");

        dialog.showAndWait().ifPresent(nombre -> {
            listaProvedores.add(new Proveedor(nombre, "contacto@ejemplo.com", "Producto genérico"));
        });
    }

    private void editarProveedor() {
        Proveedor seleccionado = tablaProvedores.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            TextInputDialog dialog = new TextInputDialog(seleccionado.getNombre());
            dialog.setTitle("Editar Proveedor");
            dialog.setHeaderText(null);
            dialog.setContentText("Editar nombre del Proveedor:");

            dialog.showAndWait().ifPresent(nuevoNombre -> seleccionado.nombreProperty().set(nuevoNombre));
        } else {
            mostrarAlerta("Por favor, selecciona un proveedor para editar.");
        }
    }

    private void eliminarProveedor() {
        Proveedor seleccionado = tablaProvedores.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Eliminación");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Estás seguro de que deseas eliminar este proveedor?");
            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    listaProvedores.remove(seleccionado);
                }
            });
        } else {
            mostrarAlerta("Por favor, selecciona un proveedor para eliminar.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}