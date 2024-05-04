package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class RegistroController implements Initializable {

    @FXML
    private ImageView imagenLogo;

    @FXML
    private TextField campoPaisReg;

    @FXML
    private TextField campoEmailReg;

    @FXML
    private TextField campoUserReg;

    @FXML
    private PasswordField campoContraseñaReg;

    @FXML
    private Label error;
    
    @FXML
    private Label marcaErrorPais;

    @FXML
    private Label marcaErrorEmail;

    @FXML
    private Label marcaErrorUser;

    @FXML
    private Label marcaErrorPasswd;

    private String pais;

    private String email;

    private String user;

    private String passwd;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Cargar la imagen desde el archivo
        Image imagen = new Image(getClass().getResourceAsStream("/com/example/imgs/logo.jpg"));
        
        // Establecer la imagen en el ImageView
        imagenLogo.setImage(imagen);
    }
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("/com/example/vistas/login");
    }

    @FXML
    private void switchToMenuPrincipal() throws IOException {
        App.setRoot("/com/example/vistas/menusPrincipales/menuPrincipal");
    }

    @FXML
    private void guardarDatosRegistro() throws IOException {
        pais = campoPaisReg.getText();
        email = campoEmailReg.getText();
        user = campoUserReg.getText();
        passwd = campoContraseñaReg.getText();
        if (pais.isEmpty()) {
            error.setText("Debes rellenar el campo País");
            error.setVisible(true);
            marcaErrorPais.setVisible(true);
            marcaErrorEmail.setVisible(false);
            marcaErrorUser.setVisible(false);
            marcaErrorPasswd.setVisible(false);
        }
        else if (email.isEmpty()) {
            error.setText("Debes rellenar el campo Email");
            error.setVisible(true);
            marcaErrorPais.setVisible(false);
            marcaErrorEmail.setVisible(true);
            marcaErrorUser.setVisible(false);
            marcaErrorPasswd.setVisible(false);
        }
        else if (user.isEmpty()) {
            error.setText("Debes rellenar el campo Nombre de usuario");
            error.setVisible(true);
            marcaErrorPais.setVisible(false);
            marcaErrorEmail.setVisible(false);
            marcaErrorUser.setVisible(true);
            marcaErrorPasswd.setVisible(false);
        }
        else if (passwd.isEmpty()) {
            error.setText("Debes rellenar el campo Contraseña");
            error.setVisible(true);
            marcaErrorPais.setVisible(false);
            marcaErrorEmail.setVisible(false);
            marcaErrorUser.setVisible(false);
            marcaErrorPasswd.setVisible(true);
        }
        else if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            error.setText("Introduce un email válido");
            error.setVisible(true);
            marcaErrorPais.setVisible(false);
            marcaErrorEmail.setVisible(true);
            marcaErrorUser.setVisible(false);
            marcaErrorPasswd.setVisible(false);
        }
        else if (passwd.length() < 8) {
            error.setText("La contraseña debe ser mayor a 7 carácteres");
            error.setVisible(true);
            marcaErrorPais.setVisible(false);
            marcaErrorEmail.setVisible(false);
            marcaErrorUser.setVisible(false);
            marcaErrorPasswd.setVisible(true);
        }
        else if (!passwd.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+")) {
            error.setText("La contraseña debe contener al menos una mayúscula, una minúscula y un dígito");
            error.setVisible(true);
            marcaErrorPais.setVisible(false);
            marcaErrorEmail.setVisible(false);
            marcaErrorUser.setVisible(false);
            marcaErrorPasswd.setVisible(true);
        }
        else{
            if(agnadirUsuario()){
                switchToMenuPrincipal();
            }
            else {
                error.setText("Ya existe un usuario con este nombre");
                error.setVisible(true);
                marcaErrorUser.setVisible(true);
                marcaErrorPasswd.setVisible(false);
                marcaErrorPais.setVisible(false);
                marcaErrorEmail.setVisible(false);
            }
        }
    }

    /**
     * Añade el usuario registrado a la Base de Datos mediante un JSON
     */
    @SuppressWarnings("unchecked")
    private boolean agnadirUsuario() {
        try {
            URL url = new URL(App.ip + "/usuarios/newUsuario");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true); 
            conn.setRequestProperty("Content-Type", "application/json");
    
            List<Usuario> amigos = new ArrayList<>();
            List<Partida> partidas = new ArrayList<>();
            Usuario usuario = new Usuario(user, email, 100, pais, amigos, partidas);
            App.usuario = usuario;

            JSONObject jsonUsuario = new JSONObject();

            jsonUsuario.put("nombre", user);
            jsonUsuario.put("pais", pais);
            jsonUsuario.put("email", email);
            jsonUsuario.put("fichas", 100);
            jsonUsuario.put("amigos", amigos);
            jsonUsuario.put("partidas", partidas);
            
            try (OutputStream os = conn.getOutputStream()) {
                String jsonString = jsonUsuario.toJSONString();
                byte[] input = jsonString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
                
            // Leer la respuesta del servidor
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Respuesta del servidor: " + response.toString());
            }

            conn.disconnect();
            return true;
        } catch (MalformedURLException e) {
            // Manejar la excepción de URL mal formada
            e.printStackTrace();
        } catch (IOException e) {
            // Manejar la excepción de entrada/salida, que incluye la conexión rechazada
            e.printStackTrace();
        } catch (Exception e) {
            // Manejar otras excepciones no previstas
            e.printStackTrace();
        }
        return false;
    }
}