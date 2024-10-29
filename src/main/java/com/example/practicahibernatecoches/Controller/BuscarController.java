package com.example.practicahibernatecoches.Controller;

import com.example.practicahibernatecoches.dao.CocheDao;
import com.example.practicahibernatecoches.dao.CocheDaoImpl;
import com.example.practicahibernatecoches.model.Coche;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class BuscarController {

    @FXML
    private Button btnBuscar2;

    @FXML
    private TableColumn<Coche, String> colMarca;

    @FXML
    private TableColumn<Coche, String> colMatricula;

    @FXML
    private TableColumn<Coche, String> colModelo;

    @FXML
    private TableColumn<Coche, String> colTipo;

    @FXML
    private Button idSalir;

    @FXML
    private TableView<Coche> idTablaCoches;

    @FXML
    private TextField txtMatricula;

    private SessionFactory sessionFactory;

    private CocheDao cocheDao;

    public BuscarController() {
        // Constructor vacío
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @FXML
    private void initialize() {
        cocheDao = new CocheDaoImpl();

        colMarca.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarca()));
        colMatricula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatricula()));
        colModelo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelo()));
        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTipo())));
    }

    @FXML
    void onClickBuscar2(ActionEvent event) {
        String matricula = txtMatricula.getText();

        if (matricula.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor ingresa una matrícula.");
            alert.show();
            return;
        }

        try (Session session = sessionFactory.openSession()) {
            cocheDao = new CocheDaoImpl();
            Coche coche = cocheDao.buscarCoche(matricula, session);

            if (coche != null) {
                idTablaCoches.getItems().clear();
                idTablaCoches.getItems().add(coche);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No se encontró ningún coche con esa matrícula.");
                alert.show();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al buscar el coche: " + e.getMessage());
            alert.show();
        }
    }


    @FXML
    void onClickedMouse(MouseEvent event) {

    }

    @FXML
    void onFinClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Cierre");
        alert.setHeaderText("¿Estás seguro de que deseas cerrar la aplicación?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

}
