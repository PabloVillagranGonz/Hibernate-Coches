package com.example.practicahibernatecoches.Controller;

import com.example.practicahibernatecoches.dao.CocheDao;
import com.example.practicahibernatecoches.dao.CocheDaoImpl;
import com.example.practicahibernatecoches.model.Coche;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.cfg.Configuration;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class CochesController {

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnImportar;

    @FXML
    private ComboBox<String> fxTipo;

    @FXML
    private Button idSalir;

    @FXML
    private ListView<Coche> listCoche;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtModelo;

    private SessionFactory sessionFactory;

    private CocheDao cocheDao;

    public CochesController() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("La sesion ha sido fallida." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @FXML
    private void initialize() {
        cocheDao = new CocheDaoImpl();
        fxTipo.getItems().addAll("Sedán", "SUV", "Camioneta", "Deportivo");
    }

    private void cargarCoches() {
        try (Session session = sessionFactory.openSession()) {
            List<Coche> coches = cocheDao.getAllCoche(session);
            listCoche.getItems().clear();
            listCoche.getItems().addAll(coches);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtMarca.clear();
        txtModelo.clear();
        txtMatricula.clear();
        fxTipo.getSelectionModel().clearSelection(); // Limpiar la selección del ComboBox
    }

    @FXML
    void onClicBorrar(ActionEvent event) {
        // Limpiamos los campos de texto
        limpiarCampos();

        // Mostramos alerta de confirmacion
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Datos Borrados");
        alert.setHeaderText(null);
        alert.setContentText("Los campos han sido borrados.");
        alert.showAndWait();
    }

    @FXML
    void onClicEliminar(ActionEvent event) {
        Coche cocheSeleccionado = listCoche.getSelectionModel().getSelectedItem();

        if (cocheSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecciona un coche para eliminar.");
            alert.showAndWait();
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmación de Eliminación");
        confirmacion.setHeaderText("¿Estás seguro de que deseas eliminar este coche?");
        confirmacion.setContentText("Coche: " + cocheSeleccionado.getMarca() + " " + cocheSeleccionado.getModelo());

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try (Session session = sessionFactory.openSession()) {
                cocheDao.deleteCocheById(cocheSeleccionado.getId(), session);
                cargarCoches();
                limpiarCampos();
            } catch (Exception e) {
                System.out.println("Error al eliminar el coche: " + e.getMessage());
            }
        }
    }

    @FXML
    void onClicModificar(ActionEvent event) {
        Coche cocheSeleccionado = listCoche.getSelectionModel().getSelectedItem();

        if (cocheSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecciona un coche para modificar.");
            alert.showAndWait();
            return;
        }

        if (txtMatricula.getText().isEmpty() || txtMarca.getText().isEmpty() ||
                txtModelo.getText().isEmpty() || fxTipo.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, completa todos los campos.");
            alert.showAndWait();
            return;
        }

        // Actualizar los campos del coche seleccionado con los nuevos valores
        cocheSeleccionado.setMatricula(txtMatricula.getText());
        cocheSeleccionado.setMarca(txtMarca.getText());
        cocheSeleccionado.setModelo(txtModelo.getText());
        cocheSeleccionado.setTipo(fxTipo.getValue());

        try (Session session = sessionFactory.openSession()) {
            cocheDao.updateCoche(cocheSeleccionado, session);
            cargarCoches();
            limpiarCampos();
        } catch (Exception e) {
            System.out.println("Error al modificar el coche: " + e.getMessage());
        }
    }

    @FXML
    void onClicNuevo(ActionEvent event) {
        Coche cochenuevo = new Coche();
        cochenuevo.setMatricula(txtMatricula.getText());
        cochenuevo.setMarca(txtMarca.getText());
        cochenuevo.setModelo(txtModelo.getText());
        cochenuevo.setTipo(fxTipo.getValue());

        if (txtMatricula.getText().isEmpty() || txtMarca.getText().isEmpty() ||
                txtModelo.getText().isEmpty() || fxTipo.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete todos los campos.");
            alert.showAndWait();
        } else {
            try (Session session = sessionFactory.openSession()) {
                cocheDao.saveCoche(cochenuevo, session);
                cargarCoches();
                limpiarCampos();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
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

    @FXML
    void onImportar2(MouseEvent event) {
        try{
            Coche coche = listCoche.getSelectionModel().getSelectedItem();
            if (coche != null){
                txtMatricula.setText(coche.getMatricula());
                txtMarca.setText(coche.getMarca());
                txtModelo.setText(coche.getModelo());
                fxTipo.setValue(coche.getTipo());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void OnClickImportar(ActionEvent event) {
        cargarCoches();
    }

}
