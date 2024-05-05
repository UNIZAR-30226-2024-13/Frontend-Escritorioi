package com.example.menusPrincipales;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.App;
import com.example.Partida;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class MenuCrearController implements Initializable{

    @FXML
    private VBox opcionesVBox;
    
    @FXML
    private Label labelFichas;

    private boolean opcionesVisible = false;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        labelFichas.setText(App.usuario.getDinero() + " Fichas");
    }

    @FXML
    private void switchToAmigos() throws IOException {
        App.setRoot("/com/example/vistas/menusPrincipales/menuAmigos");
    }

    @FXML
    private void switchToMenuCrearPartida() throws IOException {
        App.setRoot("/com/example/vistas/menusPrincipales/menuCrearPartida");
    }
    
    @FXML
    private void switchToMenuUnirse() throws IOException {
        App.setRoot("/com/example/vistas/menusPrincipales/menuUnirsePartida");
    }

    @FXML
    private void switchToMenuPartidas() throws IOException {
        App.setRoot("/com/example/vistas/menusPrincipales/menuPartidasPausadas");
    }

    @FXML
    private void switchToPerfil() throws IOException {
        App.setRoot("/com/example/vistas/perfil/perfil");
    }

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("/com/example/vistas/menusPrincipales/menuPrincipal");
    }
    
    @FXML
    private void mostrarOcultarOpciones(ActionEvent event) {
        opcionesVisible = !opcionesVisible;
        opcionesVBox.setManaged(opcionesVisible);
        opcionesVBox.setVisible(opcionesVisible);
    }

    @FXML
    private void crearCinquillo() throws IOException {
        if(agnadirPartida("/partidas/addCinquillo")){
            App.setRoot("/com/example/vistas/juegos/crearCinquillo");
        }
    }

    @FXML
    private void crearMentiroso() throws IOException {
        if(agnadirPartida("/partidas/addMentiroso")){
            App.setRoot("/com/example/vistas/juegos/crearMentiroso");
        }
    }

    @FXML
    private void crearPoker() throws IOException {
        if(agnadirPartida("/partidas/addPoker")){
            App.setRoot("/com/example/vistas/juegos/crearPoker");
        }
    }

    @FXML
    private void crearBlackJack() throws IOException {
        if(agnadirPartida("/partidas/addBlackJack")){
            App.setRoot("/com/example/vistas/juegos/crearBlackJack");
        }
    }

    @FXML
    private void crearUNO() throws IOException {
        if(agnadirPartida("/partidas/addUNO")){
            App.setRoot("/com/example/vistas/juegos/crearUNO");
        }
    }

    /**
     * Añade la partida creada a la Base de Datos mediante un JSON
     */
    private boolean agnadirPartida(String apiJuego) {
        String llamada = App.ip + apiJuego;
        try {
            HttpResponse<JsonNode> apiResponse = Unirest.post(llamada).asJson();
            Gson gson = new Gson();
            Partida partida = gson.fromJson(apiResponse.getBody().toString(), Partida.class);
            App.partidaPasswd = partida.getId();
            return true;
        } catch (UnirestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}
