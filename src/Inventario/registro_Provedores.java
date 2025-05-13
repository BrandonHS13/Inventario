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

    public static class Proveedor {
        private final SimpleStringProperty nombre;
        private final SimpleStringProperty contacto;
        private final SimpleStringProperty producto;

        public Proveedor(String nombre, String contacto, String producto) {
            this.nombre = new SimpleStringProperty(nombre);
            this.contacto = new SimpleStringProperty(contacto);
            this.producto = new SimpleStringProperty(producto);
        }

        public String getNombre() {
            return nombre.get();
        }

        public SimpleStringProperty nombreProperty() {
            return nombre;
        }

        public String getContacto() {
            return contacto.get();
        }

        public SimpleStringProperty contactoProperty() {
            return contacto;
        }

        public String getProducto() {
            return producto.get();
        }

        public SimpleStringProperty productoProperty() {
            return producto;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Etiqueta del título
        Label lblTitulo = new Label("Registro de Proveedores");
        lblTitulo.setFont(Font.font("Arial", 24));
        lblTitulo.setAlignment(Pos.CENTER);

        // Campo de texto para buscar proveedores
        TextField txtFiltro = new TextField();
        txtFiltro.setPromptText("Buscar proveedor...");
        txtFiltro.textProperty().addListener((observable, oldValue, newValue) ->
                tablaProvedores.setItems(FXCollections.observableArrayList(
                        listaProvedores.filtered(proveedor ->
                                proveedor.getNombre().toLowerCase().contains(newValue.toLowerCase())
                        )
                ))
        );

        // Inicializar la tabla y la lista de proveedores
        tablaProvedores = new TableView<>();
        listaProvedores = FXCollections.observableArrayList();
        cargarDatosProvedores();
        crearColumnasTabla();

        // Botones
        btnAgregar = crearBoton("Agregar");
        btnEditar = crearBoton("Editar");
        btnEliminar = crearBoton("Eliminar");

        // Acciones de los botones
        btnAgregar.setOnAction(event -> agregarProveedor());
        btnEditar.setOnAction(event -> editarProveedor());
        btnEliminar.setOnAction(event -> eliminarProveedor());

        // Contenedor para los botones
        HBox botonesLayout = new HBox(10, btnAgregar, btnEditar, btnEliminar);
        botonesLayout.setAlignment(Pos.CENTER);

        // Layout principal
        VBox layoutPrincipal = new VBox(15, lblTitulo, txtFiltro, tablaProvedores, botonesLayout);
        layoutPrincipal.setPadding(new Insets(20));
        layoutPrincipal.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(layoutPrincipal, 900, 600);
        primaryStage.setTitle("Registro de Proveedores");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void crearColumnasTabla() {
        // Columna Nombre
        TableColumn<Proveedor, String> nombreCol = new TableColumn<>("Nombre");
        nombreCol.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        nombreCol.setPrefWidth(200);

        // Columna Contacto
        TableColumn<Proveedor, String> contactoCol = new TableColumn<>("Contacto");
        contactoCol.setCellValueFactory(cellData -> cellData.getValue().contactoProperty());
        contactoCol.setPrefWidth(150);

        // Columna Producto
        TableColumn<Proveedor, String> productoCol = new TableColumn<>("Producto que suministra");
        productoCol.setCellValueFactory(cellData -> cellData.getValue().productoProperty());
        productoCol.setPrefWidth(250);

        tablaProvedores.getColumns().addAll(nombreCol, contactoCol, productoCol);
        tablaProvedores.setItems(listaProvedores);
    }

    private void cargarDatosProvedores() {
        listaProvedores.addAll(
                new Proveedor("Proveedor A", "contacto@proveedora.com", "Insumos básicos"),
                new Proveedor("Proveedor B", "ventas@proveedorb.net", "Equipamiento"),
                new Proveedor("Proveedor C", "pedidos@proveedorc.org", "Materia prima")
        );
    }

    private Button crearBoton(String texto) {
        Button boton = new Button(texto);
        boton.setFont(Font.font(16));
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