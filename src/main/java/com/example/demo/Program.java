package com.example.demo;

import domain.Comanda;
import domain.Tort;
import repository.*;
import service.comandaService;
import service.tortService;
import ui.Consola;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Program {
    public static void main(String[] args) throws IOException, RepositoryException {
//        Repository<Comanda> comandaRepository = new AbRepository<>();
//        List<Tort> torturi1 = List.of(new Tort(101, "Ciocolată"), new Tort(102, "Vanilie"));
//        List<Tort> torturi2 = List.of(new Tort(103, "Fructe de pădure"), new Tort(104, "Mango"));
//        List<Tort> torturi3 = List.of(new Tort(105, "Caramel"));
//        List<Tort> torturi4 = List.of(new Tort(101, "Ciocolată"), new Tort(104, "Mango"));
//        List<Tort> torturi5 = List.of(new Tort(102, "Vanilie"), new Tort(105, "Caramel"));
//
//        comandaRepository.add(new Comanda(101, torturi1, new Date()));
//        comandaRepository.add(new Comanda(102, torturi2, new Date()));
//        comandaRepository.add(new Comanda(103, torturi3, new Date()));
//        comandaRepository.add(new Comanda(104, torturi4, new Date()));
//        comandaRepository.add(new Comanda(105, torturi5, new Date()));


//        Repository<Tort> tortRepository = new AbRepository<>();
//
//        tortRepository.add(new Tort(101, "Ciocolată"));
//        tortRepository.add(new Tort(102, "Vanilie"));
//        tortRepository.add(new Tort(103, "Fructe de pădure"));
//        tortRepository.add(new Tort(104, "Mango"));
//        tortRepository.add(new Tort(105, "Caramel"));


        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("D:\\Facultate\\demo\\src\\main\\java\\setting.properties")){
            properties.load(input);
        }catch (IOException e){
            System.out.println(e.getMessage());
            return;
        }

        String repositoryType1 = properties.getProperty("Repository1");
        String repositoryType2 = properties.getProperty("Repository2");
        String tortFile = properties.getProperty("TortFile");
        String comandaFile = properties.getProperty("ComandaFile");

        Repository<Tort> tortRepository = null;
        Repository<Comanda> comandaRepository = null;

        switch (repositoryType1.toLowerCase())
        {
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
                System.out.println("Invalid Repository");
                return;
        }
        tortService.initialize(tortRepository, comandaRepository);

        switch (repositoryType2.toLowerCase())
        {
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
                System.out.println("Invalid Repository");
                return;
        }
//        SQLTortRepository tortRepository = new SQLTortRepository();
//        SQLComandaRepository comandaRepository = new SQLComandaRepository();

        tortService tortService = new tortService(tortRepository, comandaRepository);
        comandaService comandaService = new comandaService(comandaRepository, tortRepository);
        Consola consola = new Consola(comandaService, tortService);
        consola.start();


    }
//    private static void createFileIfNotExists(String filePath) {
//        File file = new File(filePath);
//        if (!file.exists()) {
//            try {
//                if (file.createNewFile()) {
//                    System.out.println("File created: " + filePath);
//                } else {
//                    System.out.println("Failed to create file: " + filePath);
//                }
//            } catch (IOException e) {
//                System.out.println("Error creating file: " + filePath);
//                e.printStackTrace();
//            }
//        }
//    }
}