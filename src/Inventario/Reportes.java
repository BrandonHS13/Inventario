package Inventario;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class Reportes extends Application {

    private Font tamañoFuente;    
    private Label lblReportes; private Label lblFecha_Ini;       private Label lblFecha_Fin;     
    private TextField txtFecha_Ini;     private TextField txtFecha_Fin;
    private Button btnGenerar;
    private AnchorPane root;

    @Override
    public void start(Stage primaryStage) {
        inicializarComponentes();
        organizarLayout();
        configurarEscena(primaryStage);
    }

    private void inicializarComponentes() {
        tamañoFuente = Font.font(35);

        // Etiquetas
        lblReportes = crearEtiqueta("Reportes y analisis", 300, 25);
        lblFecha_Ini = crearEtiqueta("Fecha inicio", 150, 270);
        lblFecha_Fin = crearEtiqueta("fecha final", 650, 270);
        
        // Botónes
        btnGenerar = crearBoton(370,450,"Generar reporte en pdf");
        
        
        //Barra de text
        txtFecha_Ini = barraTexto(200, 45, 150, 330);
        txtFecha_Fin = barraTexto(200, 45, 650, 330);
        root = new AnchorPane();
    }

    private Button crearBoton(double PosicionX, double PosicionY, String Text) {
        Button btn = new Button(Text);
        Font otraFuenteBoton = Font.font(20);
        btn.setTranslateX(PosicionX);
        btn.setTranslateY(PosicionY);
        btn.setFont(otraFuenteBoton);
        return btn;
    }

    private Label crearEtiqueta(String texto, double PosicionX, double PosicionY) {
        Label etiqueta = new Label(texto);
        etiqueta.setTranslateX(PosicionX);
        etiqueta.setTranslateY(PosicionY);
        etiqueta.setFont(tamañoFuente);
        return etiqueta;
    }
    
    private TextField barraTexto(double ancho, double alto, double PosicionX, double PosicionY) {
        TextField barra = new TextField();
        barra.setPrefWidth(ancho);
        barra.setPrefHeight(alto);
        barra.setTranslateX(PosicionX);
        barra.setTranslateY(PosicionY);
        return barra;
    }

    private void organizarLayout() {
        root.getChildren().addAll(lblReportes, lblFecha_Ini, lblFecha_Fin, btnGenerar, txtFecha_Ini, txtFecha_Fin);
    }

    private void configurarEscena(Stage primaryStage) {
        Scene scene = new Scene(root, 950, 680);
        primaryStage.setTitle("Inventario");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*public static void main(String[] args) {
        
        launch(Inicio_sesion.class,args);
    }*/
}