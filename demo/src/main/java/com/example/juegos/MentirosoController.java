package com.example.juegos;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.App;
import com.example.Carta;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MentirosoController implements Initializable{

    @FXML
    private GridPane cartasMesa;

    @FXML
    private GridPane cartas;

    private List<Carta> listaCartas = new ArrayList<>();

    private List<Button> botonesSeleccionados = new ArrayList<>();

    private List<Carta> cartasSeleccionadas = new ArrayList<>();

   
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            /**
             * TODO: Conectarse al servidor, recibir las manos
             * Iterar sobre las manos y añadir a cada caja el contenido individual
             * Cerrar las conexiones y manejar errores
             */
            int n = 0;
            while (n < 10) { 
                //  Añadir las cartas a la lista, parseando lo recibido
                listaCartas.add(new Carta(n, 2));
                n++;
            }

            // Eliminar lo que hubiera antes y crear botones para cada carta
            n = 0;
            cartas.getChildren().clear();
            for (Carta carta : listaCartas) {
                Button boton = new Button(carta.toString());
                boton.getStyleClass().add("carta-button");
                boton.setOnAction(event -> {
                    boton.getStyleClass().remove("carta-button");
                    boton.getStyleClass().add("carta-button-clicked");
                    botonesSeleccionados.add(boton);
                    cartasSeleccionadas.add(carta);
                });
                cartas.add(boton, n, 0);
                n++;
            }
            cartas.setHgap(20);
            cartasMesa.setHgap(10);
        } catch (Exception e) {
        } finally{
        }
    }

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("/com/example/vistas/menusPrincipales/menuPrincipal");
    }

    @FXML
    private void pausarPartida() throws IOException {
        // TODO : Iniciar votacion o votar si / no
    }

    @FXML
    private void ponerCarta(){ 
        for (int i = 0; i < cartasSeleccionadas.size(); i++) {
            Label label = new Label(cartasSeleccionadas.get(i).toString());
            label.getStyleClass().add("carta-button");
            cartasMesa.add(label, i, 0);
            cartas.getChildren().remove(botonesSeleccionados.get(i));
        }       
    }
}