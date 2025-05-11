import javafx.beans.property.SimpleStringProperty;

public class Ingrediente {
        private final SimpleStringProperty nombre;
        private final SimpleStringProperty cantidad;
        private final SimpleStringProperty unidad;

        public Ingrediente(String nombre, String cantidad, String unidad) {
            this.nombre = new SimpleStringProperty(nombre);
            this.cantidad = new SimpleStringProperty(cantidad);
            this.unidad = new SimpleStringProperty(unidad);
        }

        // Getters para las propiedades
        public String getNombre() { return nombre.get(); }
        public SimpleStringProperty nombreProperty() { return nombre; }

        public String getCantidad() { return cantidad.get(); }
        public SimpleStringProperty cantidadProperty() { return cantidad; }

        public String getUnidad() { return unidad.get(); }
        public SimpleStringProperty unidadProperty() { return unidad; }
    }