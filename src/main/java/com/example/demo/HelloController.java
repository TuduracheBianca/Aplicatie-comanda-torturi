//package com.example.demo;
//
//import domain.*;
//import javafx.fxml.FXMLLoader;
//import repository.*;
//import service.*;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//
//import java.awt.event.ActionEvent;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class HelloController {
//
//    @FXML
//    public TableView<Tort> tortTable;
//
//    @FXML
//    public ListView<String> listView;
//
//    @FXML
//    public TableColumn<Tort, Integer> idTortColumn;
//    @FXML
//    public TableColumn<Tort, String> tipTortColumn;
//
//    @FXML
//    public TableView<Comanda> comandaTable;
//    @FXML
//    public TableColumn<Comanda, Integer> idComandaColumn;
//    @FXML
//    public TableColumn<Comanda, String> torturiComandaColumn;
//    @FXML
//    public TableColumn<Comanda, String> dataComandaColumn;
//
//    @FXML
//    public TextField idTortField;
//    @FXML
//    public TextField numeTortField;
//
//    @FXML
//    public TextField idComandaField;
//    @FXML
//    public TextField torturiComandaField;
//    @FXML
//    public TextField dataComandaField;
//
//    @FXML
//    public DialogPane dialogPane;
//
//    private tortService tortService;
//    private comandaService comandaService;
//
//    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
//
//    // Metoda pentru setarea serviciilor
//    public void setServices(tortService tortService, comandaService comandaService) {
//        this.tortService = tortService;
//        this.comandaService = comandaService;
//        updateTables(); // Încarcă datele imediat după setarea serviciilor
//
//    }
//
//    @FXML
//    public void initialize() {
//        // Configurarea tabelelor la inițializare
//        configureTortTable();
//        configureComandaTable();
//    }
//
//    private void configureTortTable() {
//        idTortColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
//        tipTortColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTip_tort()));
//    }
//
//    private void updateTables() {
//        // Actualizează tabelul de torturi
//        tortTable.setItems(FXCollections.observableArrayList(tortService.display()));
////        tortTable.getItems().setAll(tortService.getInstance().display());
//
//
//        // Actualizează tabelul de comenzi
//        comandaTable.setItems(FXCollections.observableArrayList(comandaService.display()));
//    }
//
//    private void configureComandaTable() {
//        idComandaColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
//        torturiComandaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
//                String.join(", ", cellData.getValue().getTorturi().stream()
//                        .map(tort -> String.valueOf(tort.getId()))
//                        .collect(Collectors.toList()))
//        ));
//        dataComandaColumn.setCellValueFactory(cellData -> {
//            Date date = cellData.getValue().getDate();
//            return new SimpleStringProperty(sdf.format(date));
//        });
//    }
//
//    @FXML
//    public void adaugaTort() {
//        try {
////            if (idTortField.getText().isEmpty() || numeTortField.getText().isEmpty()) {
////                showError("Toate câmpurile trebuie completate!");
////                return;
////            }
////
////            int id = Integer.parseInt(idTortField.getText());
//            String nume = numeTortField.getText().trim();
////
////            if (tortService.findById(id) != null) {
////                showError("Există deja un tort cu acest ID.");
////                return;
////            }
//
//            tortService.add(nume);
//            showSuccess("Tortul a fost adaugat cu succes!");
////            tortTable.setItems(FXCollections.observableArrayList(tortService.display()));  // Actualizează tabelul
//            updateTables();
////        } catch (NumberFormatException e) {
////            showError("ID-ul trebuie să fie un număr întreg!");
//        } catch (Exception e) {
//            showError("Eroare la adăugarea tortului: " + e.getMessage());
//        } finally {
////            idTortField.clear();
//            numeTortField.clear();
//        }
//    }
//
//    @FXML
//    public void stergeTort() {
//        try {
//            Tort selectedTort = tortTable.getSelectionModel().getSelectedItem();
//            if (selectedTort == null) {
//                showError("Selectați un tort pentru ștergere!");
//                return;
//            }
//
//            tortService.delete(selectedTort.getId());
//            showSuccess("Tortul a fost sters cu succes!");
//            updateTables(); // Încarcă datele imediat după setarea serviciilor
//        } catch (Exception e) {
//            showError("Eroare la ștergerea tortului: " + e.getMessage());
//        } finally {
////            idTortField.clear();
////            numeTortField.clear();
//        }
//    }
//
//    @FXML
//    public void modificaTort() {
//        try {
////            if (idTortField.getText().isEmpty() || numeTortField.getText().isEmpty()) {
////                showError("Toate câmpurile trebuie completate!");
////                return;
////            }
////
////            int id = Integer.parseInt(idTortField.getText());
////            String nume = numeTortField.getText().trim();
//
//            Tort selectedTort = tortTable.getSelectionModel().getSelectedItem();
//            if (selectedTort == null) {
//                showError("Selectați un tort pentru ștergere!");
//                return;
//            }
//
//            // luam numele nou
//            String nume = numeTortField.getText().trim();
//
//            tortService.updateTort(selectedTort.getId(), nume);
//            showSuccess("Tortul a fost modificat cu succes!");
////            tortTable.setItems(FXCollections.observableArrayList(tortService.display()));  // Actualizează tabelul
//            updateTables();
//
//        } catch (NumberFormatException e) {
//            showError("ID-ul trebuie să fie un număr întreg!");
//        } catch (Exception e) {
//            showError("Eroare la modificarea tortului: " + e.getMessage());
//        } finally {
//            // nu mai e nevoie sa rulam clear => tabelul este updatat automat cand facem refresh la date
//
////            idTortField.clear();
////            numeTortField.clear();
//        }
//    }
//
//    @FXML
//    public void afisareTorturi() {
//        try {
//            listView.getItems().clear();
//            for (Tort cake : tortService.display()) {
//                listView.getItems().add(cake.toString());
//            }
//        } catch (Exception e) {
//            showError(e.getMessage());
//        }
//    }
//
//    @FXML
//    public void afisareComanda() {
//        try {
//            listView.getItems().clear();
//            for (Comanda comanda : comandaService.display()) {
//                listView.getItems().add(comanda.toString());
//            }
//        } catch (Exception e) {
//            showError(e.getMessage());
//        }
//    }
//
//    @FXML
//    public void adaugaComanda() {
//        try {
//            if (torturiComandaField.getText().isEmpty() || dataComandaField.getText().isEmpty()) {
//                showError("Toate câmpurile trebuie completate!");
//                return;
//            }
//
////            int id = Integer.parseInt(idComandaField.getText());
//            String[] tortIds = torturiComandaField.getText().split(",");
//            Date data = sdf.parse(dataComandaField.getText());
//
//            List<Tort> torturi = new ArrayList<>();
//            for (String tortId : tortIds) {
//                Tort tort = tortService.findById(Integer.parseInt(tortId.trim()));
//                if (tort == null) {
//                    showError("Tortul cu ID " + tortId + " nu există.");
//                    return;
//                }
//                torturi.add(tort);
//            }
//
////            Comanda comanda = new Comanda(torturi, data);
////            System.out.println("Verificare comanda cu ID: " + id);
////            if (comandaService.findById(id) != null) {
////                showError("Există deja o comandă cu acest ID.");
////                return;
////            }
//
//            comandaService.add(torturi, data);
//            showSuccess("Comanda a fost adaugata cu succes!");
////            comandaTable.setItems(FXCollections.observableArrayList(comandaService.display()));  // Actualizează tabelul
//            updateTables();
//
//        } catch (Exception e) {
//            showError("Eroare la adăugarea comenzii: " + e.getMessage());
//        } finally {
////            idComandaField.clear();
////            torturiComandaField.clear();
////            dataComandaField.clear();
//        }
//    }
//
//    @FXML
//    public void stergeComanda() {
//        try {
//            Comanda selectedComanda = comandaTable.getSelectionModel().getSelectedItem();
//            if (selectedComanda == null) {
//                showError("Selectați o comandă pentru ștergere!");
//                return;
//            }
//
//            comandaService.delete(selectedComanda.getId());
//            showSuccess("Comanda a fost sterasa cu succes!");
//            updateTables();
//
//        } catch (Exception e) {
//            showError("Eroare la ștergerea comenzii: " + e.getMessage());
//        } finally {
////            idComandaField.clear();
////            torturiComandaField.clear();
////            dataComandaField.clear();
//        }
//    }
//
//    @FXML
//    public void modificaComanda() {
//        try {
////            if (idComandaField.getText().isEmpty() || torturiComandaField.getText().isEmpty() || dataComandaField.getText().isEmpty()) {
////                showError("Toate câmpurile trebuie completate!");
////                return;
////            }
////
//////            int id = Integer.parseInt(idComandaField.getText());
////            String[] tortIds = torturiComandaField.getText().split(",");
////            Date data = sdf.parse(dataComandaField.getText());
////
////            List<Tort> torturi = new ArrayList<>();
////            for (String tortId : tortIds) {
////                Tort tort = tortService.findById(Integer.parseInt(tortId.trim()));
////                if (tort == null) {
////                    showError("Tortul cu ID " + tortId + " nu există.");
////                    return;
////                }
////                torturi.add(tort);
//
//            Comanda selectedComanda = comandaTable.getSelectionModel().getSelectedItem();
//            if (selectedComanda == null) {
//                showError("Selectati o comanda pentru stergere");
//                return;
//            }
//
//
//            Tort selectedTort = tortTable.getSelectionModel().getSelectedItem();
//            if (selectedTort == null) {
//                showError("Selectați un tort pentru ștergere!");
//                return;
//            }
//
//            // luam numele nou
//            String nume = numeTortField.getText().trim();
//
//            tortService.updateTort(selectedTort.getId(), nume);
//            showSuccess("Tortul a fost modificat cu succes!");
////            tortTable.setItems(FXCollections.observableArrayList(tortService.display()));  // Actualizează tabelul
//            updateTables();
//
////            comandaService.updateComanda(id, data, torturi);
//            showSuccess("Comanda modificata cu succes!");
////            comandaTable.setItems(FXCollections.observableArrayList(comandaService.display()));  // Actualizează tabelul
//            updateTables();
//
//        } catch (Exception e) {
//            showError("Eroare la modificarea comenzii: " + e.getMessage());
//        } finally {
////            idComandaField.clear();
////            torturiComandaField.clear();
////            dataComandaField.clear();
//        }
//    }
//    // Afișează rapoartele zilnice
//    public void arataRapoarteZilnice() {
//        List<Map.Entry<String, Long>> rapoarte = comandaService.rapoarteTorturiPeZi();
//        afiseazaRapoarte(rapoarte);
//    }
//
//    // Afișează rapoartele lunare
//    public void arataRapoarteLunare() {
//        List<Map.Entry<String, Long>> rapoarte = comandaService.rapoarteTorturiPeLuna();
//        afiseazaRapoarte(rapoarte);
//    }
//
//    // Afișează cele mai comandate torturi
//    public void arataTorturiCeleMaiComandate() {
//        List<Map.Entry<Tort, Long>> torturi = comandaService.torturiCeleMaiComandate();
//        ObservableList<String> observableList = FXCollections.observableArrayList();
//
//        for (Map.Entry<Tort, Long> entry : torturi) {
//            // Formatează string-ul pentru a afișa numele tortului și numărul de comenzi
//            observableList.add(entry.getKey().getTip_tort() + ": " + entry.getValue() + " comenzi");
//        }
//
//        // Setează lista în ListView
//        listView.setItems(observableList);    }
//
//    // Afișează rapoartele în ListView
//    private void afiseazaRapoarte(List<Map.Entry<String, Long>> rapoarte) {
//        ObservableList<String> observableList = FXCollections.observableArrayList();
//        for (Map.Entry<String, Long> entry : rapoarte) {
//            observableList.add(entry.getKey() + ": " + entry.getValue() + " torturi comandate");
//        }
//        listView.setItems(observableList);
//    }
//
//    // Afișează torturile cele mai comandate
//    private void afiseazaTorturiCeleMaiComandate(List<Map.Entry<Tort, Long>> torturi) {
//        ObservableList<String> observableList = FXCollections.observableArrayList();
//        for (Map.Entry<Tort, Long> entry : torturi) {
//            observableList.add(entry.getKey().getTip_tort() + ": " + entry.getValue() + " comenzi");
//        }
//        listView.setItems(observableList);
//    }
//
//
//    private void showSuccess(String message) {
//        dialogPane.setContentText(message);
//    }
//    public void showError(String message) {
//        // Creează un alert de tip eroare
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Eroare");
//        alert.setHeaderText("A apărut o problemă");
//        alert.setContentText(message);
//
//        // Afișează alert-ul
//        alert.showAndWait();
//    }
//}





package com.example.demo;

import domain.Comanda;
import domain.Tort;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.comandaService;
import service.tortService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HelloController {

    @FXML
    public TableView<Tort> tortTable;

    @FXML
    public ListView<String> listView;

    @FXML
    public TableColumn<Tort, Integer> idTortColumn;
    @FXML
    public TableColumn<Tort, String> tipTortColumn;

    @FXML
    public TableView<Comanda> comandaTable;
    @FXML
    public TableColumn<Comanda, Integer> idComandaColumn;
    @FXML
    public TableColumn<Comanda, String> torturiComandaColumn;
    @FXML
    public TableColumn<Comanda, String> dataComandaColumn;

    @FXML
    public TextField idTortField;
    @FXML
    public TextField numeTortField;

    @FXML
    public TextField idComandaField;
    @FXML
    public TextField torturiComandaField;
    @FXML
    public TextField dataComandaField;

    private tortService tortService;
    private comandaService comandaService;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    // Metoda pentru setarea serviciilor
    public void setServices(tortService tortService, comandaService comandaService) {
        this.tortService = tortService;
        this.comandaService = comandaService;
        updateTables(); // Încarcă datele imediat după setarea serviciilor
    }

    @FXML
    public void initialize() {
        // Configurarea tabelelor la inițializare
        configureTortTable();
        configureComandaTable();
    }

    private void updateTables() {
        // Actualizează tabelul de torturi
        tortTable.setItems(FXCollections.observableArrayList(tortService.display()));

        // Actualizează tabelul de comenzi
        comandaTable.setItems(FXCollections.observableArrayList(comandaService.display()));
    }

    private void configureTortTable() {
        idTortColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        tipTortColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTip_tort()));
    }

    private void configureComandaTable() {
        idComandaColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        torturiComandaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.join(", ", cellData.getValue().getTorturi().stream()
                        .map(tort -> String.valueOf(tort.getId()))
                        .collect(Collectors.toList()))
        ));
        dataComandaColumn.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().getDate();
            return new SimpleStringProperty(sdf.format(date));
        });
    }

    @FXML
    public void adaugaTort() {
        try {
//            if (idTortField.getText().isEmpty() || numeTortField.getText().isEmpty()) {
//                showError("Toate câmpurile trebuie completate!");
//                return;
//            }
//
//            int id = Integer.parseInt(idTortField.getText());
            String nume = numeTortField.getText().trim();
//

//            if (tortService.findById(id) != null) {
//                showError("Există deja un tort cu acest ID.");
//                return;
//            }

            tortService.add(nume);
            showSuccess("Tortul a fost adaugat cu succes!");
            tortTable.setItems(FXCollections.observableArrayList(tortService.display()));  // Actualizează tabelul
//        } catch (NumberFormatException e) {
//            showError("ID-ul trebuie să fie un număr întreg!");
        } catch (Exception e) {
            showError("Eroare la adăugarea tortului: " + e.getMessage());
        } finally {
//            idTortField.clear();
            numeTortField.clear();
        }
    }

    @FXML
    public void stergeTort() {
        try {
            Tort selectedTort = tortTable.getSelectionModel().getSelectedItem();
            if (selectedTort == null) {
                showError("Selectați un tort pentru ștergere!");
                return;
            }

            tortService.delete(selectedTort.getId());
            showSuccess("Tortul a fost sters cu succes!");
            updateTables();
        } catch (Exception e) {
            showError("Eroare la ștergerea tortului: " + e.getMessage());
        } finally {
//            idTortField.clear();
//            numeTortField.clear();
        }
    }

    @FXML
    public void modificaTort() {
        try {
//            if (idTortField.getText().isEmpty() || numeTortField.getText().isEmpty()) {
//                showError("Toate câmpurile trebuie completate!");
//                return;
//            }
//            int id = Integer.parseInt(idTortField.getText());
//            String nume = numeTortField.getText().trim();
//
//            System.out.println("3");
//
//            tortService.updateTort(id, nume);
//            showSuccess("Tortul a fost modificat cu succes!");

            // selecteaza tortul existent exact cum faci la stergere
            Tort selectedTort = tortTable.getSelectionModel().getSelectedItem();
            if (selectedTort == null) {
                showError("Selectați un tort pentru ștergere!");
                return;
            }

            // luam numele nou
            String nume = numeTortField.getText().trim();

            tortService.updateTort(selectedTort.getId(), nume);
            showSuccess("Tortul a fost modificat cu succes!");
            updateTables();
        } catch (NumberFormatException e) {
            showError("ID-ul trebuie să fie un număr întreg!");
        } catch (Exception e) {
            showError("Eroare la modificarea tortului: " + e.getMessage());
        } finally {
            // nu mai e nevoie sa rulam clear => tabelul este updatat automat cand facem refresh la date
//            idTortField.clear();
//            numeTortField.clear();
        }
    }

    @FXML
    public void afisareTorturi() {
        try {
            listView.getItems().clear();
            for (Tort cake : tortService.display()) {
                listView.getItems().add(cake.toString());
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void afisareComanda() {
        try {
            listView.getItems().clear();
            for (Comanda comanda : comandaService.display()) {
                listView.getItems().add(comanda.toString());
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void adaugaComanda() {
        try {
            String torturiComandaText = torturiComandaField.getText().trim();
            String dataComandaText = dataComandaField.getText().trim();

            if (torturiComandaText.isEmpty() || dataComandaText.isEmpty()) {
                showError("Toate câmpurile trebuie completate!");
                return;
            }

            Date data = sdf.parse(dataComandaText);
            String[] tortIds = torturiComandaText.split(",");
//            int id = Integer.parseInt(idComandaField.getText());

            List<Tort> torturi = new ArrayList<>();
            for (String tortId : tortIds) {
                Tort tort = tortService.findById(Integer.parseInt(tortId.trim()));
                if (tort == null) {
                    showError("Tortul cu ID " + tortId + " nu există.");
                    return;
                }
                torturi.add(tort);
            }

            comandaService.add(torturi, data);
            showSuccess("Comanda a fost adăugată cu succes!");
            updateTables();
        } catch (NumberFormatException e) {
            showError("ID-ul tortului trebuie să fie un număr întreg!");
        } catch (ParseException e) {
            showError("Format dată invalid!");
        } catch (Exception e) {
            showError("Eroare la adăugarea comenzii: " + e.getMessage());
        } finally {
            torturiComandaField.clear();
            dataComandaField.clear();
        }
    }

//    @FXML
//    public void adaugaComanda() {
//        try {
//            if (torturiComandaField.getText().isEmpty() || dataComandaField.getText().isEmpty()) {
//                showError("Toate câmpurile trebuie completate!");
//                return;
//            }
//
//            int id = Integer.parseInt(idComandaField.getText());
//            String[] tortIds = torturiComandaField.getText().split(",");
//            Date data = sdf.parse(dataComandaField.getText());
//
//            List<Tort> torturi = new ArrayList<>();
//            for (String tortId : tortIds) {
//                Tort tort = tortService.findById(Integer.parseInt(tortId.trim()));
//                if (tort == null) {
//                    showError("Tortul cu ID " + tortId + " nu există.");
//                    return;
//                }
//                torturi.add(tort);
//            }
//
////            Comanda comanda = new Comanda(torturi, data);
////            System.out.println("Verificare comanda cu ID: " + id);
////            if (comandaService.findById(id) != null) {
////                showError("Există deja o comandă cu acest ID.");
////                return;
////            }
//
//            comandaService.add(torturi, data);
//            showSuccess("Comanda a fost adaugata cu succes!");
//            updateTables();
//
//        } catch (Exception e) {
//            showError("Eroare la adăugarea comenzii: " + e.getMessage());
//        } finally {
////            idComandaField.clear();
//            torturiComandaField.clear();
//            dataComandaField.clear();
//        }
//    }

    @FXML
    public void stergeComanda() {
        try {
            Comanda selectedComanda = comandaTable.getSelectionModel().getSelectedItem();
            if (selectedComanda == null) {
                showError("Selectați o comandă pentru ștergere!");
                return;
            }
            
            comandaService.delete(selectedComanda.getId());
            showSuccess("Comanda a fost sterasa cu succes!");
            updateTables();
        } catch (Exception e) {
            showError("Eroare la ștergerea comenzii: " + e.getMessage());
        } finally {
//            idComandaField.clear();
//            torturiComandaField.clear();
//            dataComandaField.clear();
        }
    }

    @FXML
    public void modificaComanda() {
        try {
            Comanda selectedComanda = comandaTable.getSelectionModel().getSelectedItem();
            if (selectedComanda == null) {
                showError("Selectați o comandă pentru modificare!");
                return;
            }

            String dataComandaText = dataComandaField.getText().trim();
            String torturiComandaText = torturiComandaField.getText().trim();

            if (dataComandaText.isEmpty() || torturiComandaText.isEmpty()) {
                showError("Toate câmpurile trebuie completate!");
                return;
            }

            Date data = sdf.parse(dataComandaText);
            String[] tortIds = torturiComandaText.split(",");

            List<Tort> torturi = new ArrayList<>();
            for (String tortId : tortIds) {
                Tort tort = tortService.findById(Integer.parseInt(tortId.trim()));
                if (tort == null) {
                    showError("Tortul cu ID " + tortId + " nu există.");
                    return;
                }
                torturi.add(tort);
            }

            comandaService.updateComanda(selectedComanda.getId(), data, torturi);
            showSuccess("Comanda a fost modificată cu succes!");
            updateTables();

        } catch (NumberFormatException e) {
            showError("ID-ul tortului trebuie să fie un număr întreg!");
        } catch (ParseException e) {
            showError("Format dată invalid!");
        } catch (Exception e) {
            showError("Eroare la modificarea comenzii: " + e.getMessage());
        }
    }

//    @FXML
//    public void modificaComanda() {
//        try {
//            if (idComandaField.getText().isEmpty() || torturiComandaField.getText().isEmpty() || dataComandaField.getText().isEmpty()) {
//                showError("Toate câmpurile trebuie completate!");
//                return;
//            }
//
//            int id = Integer.parseInt(idComandaField.getText());
//            String[] tortIds = torturiComandaField.getText().split(",");
//            Date data = sdf.parse(dataComandaField.getText());
//
//            List<Tort> torturi = new ArrayList<>();
//            for (String tortId : tortIds) {
//                Tort tort = tortService.findById(Integer.parseInt(tortId.trim()));
//                if (tort == null) {
//                    showError("Tortul cu ID " + tortId + " nu există.");
//                    return;
//                }
//                torturi.add(tort);
//            }
//
//            comandaService.updateComanda(id, data, torturi);
//            showSuccess("Comanda modificata cu succes!");
//            updateTables();
//
//        } catch (Exception e) {
//            showError("Eroare la modificarea comenzii: " + e.getMessage());
//        } finally {
////            idComandaField.clear();
////            torturiComandaField.clear();
////            dataComandaField.clear();
//        }
//    }
    // Afișează rapoartele zilnice
    public void arataRapoarteZilnice() {
        List<Map.Entry<String, Long>> rapoarte = comandaService.rapoarteTorturiPeZi();
        afiseazaRapoarte(rapoarte);
    }

    // Afișează rapoartele lunare
    public void arataRapoarteLunare() {
        List<Map.Entry<String, Long>> rapoarte = comandaService.rapoarteTorturiPeLuna();
        afiseazaRapoarte(rapoarte);
    }

    // Afișează cele mai comandate torturi
    public void arataTorturiCeleMaiComandate() {
        List<Map.Entry<String, Long>> torturi = comandaService.torturiCeleMaiComandate();
        ObservableList<String> observableList = FXCollections.observableArrayList();

        for (Map.Entry<String, Long> entry : torturi) {
            // Formatează string-ul pentru a afișa tipul tortului și numărul total de comenzi
            observableList.add(entry.getKey() + ": " + entry.getValue() + " comenzi");
        }

        // Setează lista în ListView
        listView.setItems(observableList);
    }

//    public void arataTorturiCeleMaiComandate() {
//        List<Map.Entry<Tort, Long>> torturi = comandaService.torturiCeleMaiComandate();
//        ObservableList<String> observableList = FXCollections.observableArrayList();
//
//        for (Map.Entry<Tort, Long> entry : torturi) {
//            // Formatează string-ul pentru a afișa numele tortului și numărul de comenzi
//            observableList.add(entry.getKey().getTip_tort() + ": " + entry.getValue() + " comenzi");
//        }
//
//        // Setează lista în ListView
//        listView.setItems(observableList);    }

    // Afișează rapoartele în ListView
    private void afiseazaRapoarte(List<Map.Entry<String, Long>> rapoarte) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (Map.Entry<String, Long> entry : rapoarte) {
            observableList.add(entry.getKey() + ": " + entry.getValue() + " torturi comandate");
        }
        listView.setItems(observableList);
    }

    // Afișează torturile cele mai comandate
//    private void afiseazaTorturiCeleMaiComandate(List<Map.Entry<Tort, Long>> torturi) {
//        ObservableList<String> observableList = FXCollections.observableArrayList();
//        for (Map.Entry<Tort, Long> entry : torturi) {
//            observableList.add(entry.getKey().getTip_tort() + ": " + entry.getValue() + " comenzi");
//        }
//        listView.setItems(observableList);
//    }


    private void showSuccess(String message) {
        // folosim tot Alert ca la showError()

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showError(String message) {
        // Creează un alert de tip eroare
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText("A apărut o problemă");
        alert.setContentText(message);

        // Afișează alert-ul
        alert.showAndWait();
    }
}
