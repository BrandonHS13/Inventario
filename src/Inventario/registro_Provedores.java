package Inventario;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class registro_Provedores extends Application {

    private Font fuenteTitulo;
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
        fuenteTitulo = Font.font(20);

        // Etiqueta del título
        Label lblTitulo = new Label("Registro de proveedores");
        lblTitulo.setFont(fuenteTitulo);

        // Inicializar la TableView y la ObservableList
        tablaProvedores = new TableView<>();
        listaProvedores = FXCollections.observableArrayList();
        cargarDatosProvedores(); // Método para agregar datos de ejemplo (puedes dejarlo vacío)

        // Definir las columnas de la tabla
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

        // Botones
        btnAgregar = crearBoton("Agregar", 100);
        btnEditar = crearBoton("Editar", 100);
        btnEliminar = crearBoton("Eliminar", 100);

        // Layout vertical para los botones
        VBox botonesLayout = new VBox(10);
        botonesLayout.getChildren().addAll(btnAgregar, btnEditar, btnEliminar);
        botonesLayout.setLayoutX(480); // Posición horizontal de los botones
        botonesLayout.setLayoutY(100); // Posición vertical inicial de los botones

        // Layout principal
        AnchorPane root = new AnchorPane();
        root.setPadding(new Insets(20));
        AnchorPane.setTopAnchor(lblTitulo, 20.0);
        AnchorPane.setLeftAnchor(lblTitulo, 20.0);
        AnchorPane.setTopAnchor(tablaProvedores, 60.0);
        AnchorPane.setLeftAnchor(tablaProvedores, 20.0);
        AnchorPane.setRightAnchor(tablaProvedores, 200.0); // Dejar espacio para los botones
        AnchorPane.setBottomAnchor(tablaProvedores, 20.0);

        root.getChildren().addAll(lblTitulo, tablaProvedores, botonesLayout);

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Registro de Proveedores");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button crearBoton(String texto, double ancho) {
        Button boton = new Button(texto);
        boton.setPrefWidth(ancho);
        Font fuenteBoton = Font.font(16);
        boton.setFont(fuenteBoton);
        boton.setTranslateX(250);
        boton.setTranslateY(50);
        return boton;
    }

    private void cargarDatosProvedores() {
        listaProvedores.addAll(
                new Proveedor("Proveedor A", "contacto@proveedora.com", "Insumos básicos"),
                new Proveedor("Proveedor B", "ventas@proveedorb.net", "Equipamiento"),
                new Proveedor("Proveedor C", "pedidos@proveedorc.org", "Materia prima")
                // Agrega aquí más proveedores si lo deseas
        );
    }

    public static void main(String[] args) {
        launch(registro_Provedores.class, args);
    }
}