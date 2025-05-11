package com.example.demo;

import domain.Comanda;
import domain.Tort;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.*;
import service.comandaService;
import service.tortService;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Încarcă FXML-ul
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        AnchorPane root = fxmlLoader.load();

        // Încarcă setările din fișierul properties
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("D:\\Facultate\\demo\\src\\main\\java\\setting.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Eroare la încărcarea fișierului de configurare: " + e.getMessage());
            return;
        }

        // Citește setările din properties
        String repositoryType1 = properties.getProperty("Repository1");
        String repositoryType2 = properties.getProperty("Repository2");
        String tortFile = properties.getProperty("TortFile");
        String comandaFile = properties.getProperty("ComandaFile");

        // Initializează repozitoarele și serviciile
        Repository<Comanda>  comandaRepository = null;
        Repository<Tort>  tortRepository = null;

        // Inițializare repository pentru torturi
        switch (repositoryType1.toLowerCase()) {
            case "binary":
                tortRepository = new BinaryFileRepo<Tort>(tortFile);
                break;
            case "text":
                tortRepository = new TortTextFileRepository(tortFile);
                break;
            case "inmemory":
                tortRepository = new MemoryRepository<Tort>();
                break;
            case "sql":
                tortRepository = new SQLTortRepository();
                break;
            default:
                System.out.println("Tipul de repository pentru torturi este invalid.");
                return;
        }

        // Inițializare repository pentru comenzi
        switch (repositoryType2.toLowerCase()) {
            case "binary":
                comandaRepository = new BinaryFileRepo<Comanda>(comandaFile);
                break;
            case "text":
                comandaRepository = new ComandaTextFileRepository(comandaFile);
                break;
            case "inmemory":
                comandaRepository = new MemoryRepository<Comanda>();
                break;
            case "sql":
                comandaRepository = new SQLComandaRepository();
                break;
            default:
                System.out.println("Tipul de repository pentru comenzi este invalid.");
                return;
        }

        // Crează serviciile pe baza repository-urilor
        tortService tortService = new tortService(tortRepository, comandaRepository);
        comandaService comandaService = new comandaService(comandaRepository, tortRepository);

        // Încarcă controller-ul FXML și setează serviciile
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        AnchorPane rootNode = loader.load();
        HelloController controller = loader.getController();
        controller.setServices(tortService, comandaService);
        controller.initialize();

        // Crează scena și setează stage-ul
        Scene scene = new Scene(rootNode, 800, 600);
        stage.setTitle("Aplicatie Torturi si Comenzi");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
