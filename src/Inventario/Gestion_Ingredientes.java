package Inventario;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import java.time.LocalDate;

public class Gestion_Ingredientes extends Application {

    private Font tamañoFuente;
    private Label lblGestion;
    private Label lblFecha;
    private Button btnListo;
    private Button btnAgregar;
    private Button btnEditar;
    private Button btnEliminar;
    private Button btnFecha;
    private AnchorPane root;

    // **DEFINICIÓN DE LA CLASE INTERNA Ingrediente**
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

        public void setFechaCaducidad(LocalDate fechaCaducidad) { this.fechaCaducidad.set(fechaCaducidad); }
        public void setCategoria(String categoria) { this.categoria.set(categoria); }
    }

    private TableView<Ingrediente> tablaIngredientes; // Declarar la TableView
    private ObservableList<Ingrediente> listaIngredientes; // Declarar la ObservableList


    @Override
    public void start(Stage primaryStage) {
        inicializarComponentes();
        organizarLayout();
        configurarEscena(primaryStage);
    }

    private void inicializarComponentes() {


        // Etiquetas
        lblGestion = crearEtiqueta("Gestion de ingredientes", 50, 25, 30);
        lblFecha = crearEtiqueta("Fecha", 50, 100, 23);

        // Botónes
        btnListo = crearBoton(150, 100, "Gestion de ingredientes");
        btnAgregar = crearBoton(650, 100, "Agregar");
        btnEditar = crearBoton(750, 100, "Editar");
        btnEliminar = crearBoton(850, 100, "Eliminar");
        btnFecha = crearBoton(620, 100, "Configuracion");

        // Inicializar TableView y ObservableList
        tablaIngredientes = new TableView<>();
        listaIngredientes = FXCollections.observableArrayList();
        cargarDatosIngredientes(); // Llama al método para llenar la lista con datos
        crearColumnasIngredientes(); // Llama al método para definir las columnas

        tablaIngredientes.setItems(listaIngredientes); // Asigna los datos a la tabla

        //Barra de text
        root = new AnchorPane();
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

        // Columna Unidad (ComboBox)
        TableColumn<Ingrediente, String> columnaUnidad = new TableColumn<>("Unidad");
        columnaUnidad.setCellValueFactory(cellData -> cellData.getValue().unidadProperty());
        columnaUnidad.setPrefWidth(100);
        columnaUnidad.setCellFactory(column -> {
            return new TableCell<Ingrediente, String>() {
                private final ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList("kg", "g", "litros", "ml", "unidades"));

                {
                    comboBox.setMaxWidth(Double.MAX_VALUE);
                    comboBox.setOnAction(event -> {
                        Ingrediente ingrediente = getTableView().getItems().get(getIndex());
                        if (ingrediente != null) {
                            ingrediente.unidadProperty().set(comboBox.getValue());
                        }
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        comboBox.setValue(item);
                        setGraphic(comboBox);
                    }
                }
            };
        });

        // Columna Fecha de caducidad (DatePicker)
        TableColumn<Ingrediente, LocalDate> columnaFechaCaducidad = new TableColumn<>("Fecha de caducidad");
        columnaFechaCaducidad.setCellValueFactory(cellData -> cellData.getValue().fechaCaducidadProperty());
        columnaFechaCaducidad.setPrefWidth(150);
        columnaFechaCaducidad.setCellFactory(column -> new DatePickerTableCell<>());

        // Columna Categoría (ComboBox)
        TableColumn<Ingrediente, String> columnaCategoria = new TableColumn<>("Categoría");
        columnaCategoria.setCellValueFactory(cellData -> cellData.getValue().categoriaProperty());
        columnaCategoria.setPrefWidth(120);
        columnaCategoria.setCellFactory(column -> {
            return new TableCell<Ingrediente, String>() {
                private final ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList("Fruta", "Verdura", "Lácteo", "Carne", "Otro"));

                {
                    comboBox.setMaxWidth(Double.MAX_VALUE);
                    comboBox.setOnAction(event -> {
                        Ingrediente ingrediente = getTableView().getItems().get(getIndex());
                        if (ingrediente != null) {
                            ingrediente.categoriaProperty().set(comboBox.getValue());
                        }
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        comboBox.setValue(item);
                        setGraphic(comboBox);
                    }
                }
            };
        });

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

    private Button crearBoton(double PosicionX, double PosicionY, String Text) {
        Button btn = new Button(Text);
        Font otraFuenteBoton = Font.font(17);
        btn.setTranslateX(PosicionX);
        btn.setTranslateY(PosicionY);
        btn.setFont(otraFuenteBoton);
        return btn;
    }

    private Label crearEtiqueta(String texto, double PosicionX, double PosicionY, double tamañoFuente) {
        Label etiqueta = new Label(texto);
        etiqueta.setTranslateX(PosicionX);
        etiqueta.setTranslateY(PosicionY);
        etiqueta.setFont(Font.font(tamañoFuente)); // Usamos el tamaño de fuente pasado como argumento
        return etiqueta;
    }

    private void organizarLayout() {
        root.getChildren().addAll(lblGestion, lblFecha, tablaIngredientes, btnAgregar, btnEditar, btnEliminar);
        // Puedes ajustar la posición y el tamaño de la tabla usando métodos de AnchorPane
        AnchorPane.setTopAnchor(tablaIngredientes, 150.0); // Ejemplo de posicionamiento
        AnchorPane.setLeftAnchor(tablaIngredientes, 50.0);
        AnchorPane.setRightAnchor(tablaIngredientes, 50.0);
        AnchorPane.setBottomAnchor(tablaIngredientes, 100.0); // Ajusta según necesites
    }

    private void configurarEscena(Stage primaryStage) {
        Scene scene = new Scene(root, 950, 680);
        primaryStage.setTitle("Inventario");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(Inicio_sesion.class, args);
    }

    // Clase interna para el DatePicker en la TableCell
    public static class DatePickerTableCell<S> extends TableCell<S, LocalDate> {

        private final DatePicker datePicker;

        public DatePickerTableCell() {
            this.datePicker = new DatePicker();
            this.datePicker.setOnAction(event -> {
                LocalDate selectedDate = datePicker.getValue();
                if (isEditing()) {
                    commitEdit(selectedDate);
                }
            });
            this.datePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    commitEdit(datePicker.getValue());
                }
            });
        }

        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                datePicker.setValue(item);
                setGraphic(datePicker);
            }
        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (!isEmpty()) {
                setGraphic(datePicker);
                setText(null);
                datePicker.requestFocus();
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem() == null ? "" : getItem().toString());
            setGraphic(null);
        }
    }
}