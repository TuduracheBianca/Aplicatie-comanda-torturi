package ui;

import domain.Comanda;
import domain.Tort;
import repository.RepositoryException;
import service.comandaService;
import service.tortService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Consola {
    private final comandaService comandaService;
    private final tortService tortService;

    public Consola(service.comandaService comandaService, service.tortService tortService) {
        this.comandaService = comandaService;
        this.tortService = tortService;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("Enter an option:");
            System.out.println("1.Insert a cake");
            System.out.println("2.Create order: ");
            System.out.println("3.Display cakes ");
            System.out.println("4.Display orders ");
            System.out.println("5.Delete Order ");
            System.out.println("6.Delete Cake ");
            System.out.println("7.Update cake to a new type ");
            System.out.println("8.Update order to a new date and cakes ");
            System.out.println("0. End of program ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> addTort(scanner);
                case 2 -> addComanda(scanner);
                case 3 -> displayTorturi();
                case 4 -> displayComenzi();
                case 5 -> deleteComanda(scanner);
                case 6 -> deleteTort(scanner);
                case 7 -> updateTort(scanner);
                case 8 -> updateComanda(scanner);
                case 0 -> flag = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }


    public void updateComanda(Scanner scanner) {
        // Citim ID-ul comenzii de la utilizator
        System.out.print("Introduceți ID-ul comenzii de actualizat: ");
        int idComanda = scanner.nextInt();

        // Verificăm dacă comanda cu acest ID există
        if (comandaService.findById(idComanda) == null) {
            throw new IllegalArgumentException("Comanda cu ID-ul " + idComanda + " nu există.");
        }

        // Citim data nouă a comenzii
        System.out.print("Introduceți data nouă a comenzii (format dd/MM/yyyy): ");
        String dataNouaStr = scanner.next();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dataNoua = null;
        try {
            dataNoua = dateFormat.parse(dataNouaStr);
        } catch (ParseException e) {
            System.out.println("Data introdusă nu este validă. Vă rugăm să încercați din nou.");
            return;
        }

        // Citim lista de torturi de la utilizator
        System.out.print("Introduceți numărul de torturi: ");
        int numarTorturi = scanner.nextInt();
        List<Tort> listaTorturiNou = new ArrayList<>();

        for (int i = 0; i < numarTorturi; i++) {
            boolean valid = false;
            while (!valid) {
                System.out.print("Introduceți ID-ul tortului " + (i + 1) + ": ");
                int idTort = scanner.nextInt();
                Tort tort =  tortService.findById(idTort);

                if (tort == null) {
                    System.out.println("ID-ul tortului " + idTort + " nu există. Vă rugăm să introduceți un ID valid.");
                } else {
                    listaTorturiNou.add(tort);
                    valid = true;
                }
            }
        }

        // Creăm noua comandă și o actualizăm în repository
//        Comanda comandaNoua = new Comanda(idComanda, listaTorturiNou, dataNoua);
        comandaService.updateComanda(idComanda, dataNoua, listaTorturiNou);
    }


//    private void updateComanda(Scanner scanner) {
//        System.out.println("Introduceti id-ul comenzii de modificat:");
//        int id;
//        while (true) {try{
//            id = Integer.parseInt(scanner.nextLine());
//            break;
//        }catch (NumberFormatException e){
//            System.out.println("Id-ul trebuie sa fie un numar intreg!");
//        }}
//        try {
//            System.out.println("Introduceti noua data a comenzii de modificat (dd-MM-yyyy):");
//            String dataString = scanner.nextLine();
//
//            Date dataNoua = new Date();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//
//            try {
//                dataNoua = dateFormat.parse(dataString);
//            } catch (ParseException e) {
//                System.out.println("Invalid date format! Please enter date in dd-MM-yyyy format.");
//            }
//
//            System.out.println("Introduceti numarul de torturi din comanda:");
//            int nr = Integer.parseInt(scanner.nextLine());
//            List<Tort> listaTorturiNou = new ArrayList<>();
//
//            for(int i = 1; i<=nr; i++) {
//                System.out.println("Introduceti id-ul tortului " + i + ":");
//                int idTort = Integer.parseInt(scanner.nextLine());
//                try {
//                    Tort tortNou = tortService.findById(idTort);
//                    listaTorturiNou.add(tortNou);
//                } catch (RepositoryException e) {
//                    System.out.println("Eroare: " + e.getMessage());
//                }
//            }
//
//
//
//            comandaService.updateComanda(id, dataNoua, listaTorturiNou);
//        } catch (RepositoryException  e) {
//            System.out.println("Eroare: "+e.getMessage());
//        }
//    }

    private void updateTort(Scanner scanner) {
        System.out.println("Introduceti id-ul tortului de modificat:");
        int id;
        while (true) {try{
            id = Integer.parseInt(scanner.nextLine());
            break;
        }catch (NumberFormatException e){
            System.out.println("Id-ul trebuie sa fie un numar intreg! ");
        }}
        try {
            System.out.println("Introduceti noul tip al tortului de modificat:");
            String tipNou = scanner.nextLine();
            tortService.updateTort(id, tipNou);
        } catch (RepositoryException  e) {
            System.out.println("Eroare: "+e.getMessage());
        }
    }

//    private void updateComanda(Scanner scanner) {
//
//    }
//
//    private void updateTort(Scanner scanner) {
//    }


    protected void addTort(Scanner scanner) {
        System.out.println("Enter the type of cake:");
        String type = scanner.nextLine();
        if (type.isBlank()) {
            System.out.println("Cake type cannot be empty.");
        } else {
            tortService.add(type);
            System.out.println("Cake added successfully.");
        }
    }

    protected void addComanda(Scanner scanner) {
        List<Tort> allTorturi = tortService.display();

        if (allTorturi.isEmpty()) {
            System.out.println("No cakes available. Please add cakes first.");
            return;
        }

        System.out.println("Available cakes:");
        for (int i = 0; i < allTorturi.size(); i++) {
            System.out.println((i + 1) + ". " + allTorturi.get(i));
        }

        System.out.println("Enter the numbers of the cakes you want to add to the order (separated by spaces), or '0' to cancel:");
        String input = scanner.nextLine();

        if (input.equals("0")) {
            System.out.println("Order creation cancelled.");
            return;
        }

        String[] indices = input.split(" ");
        List<Tort> selectedTorturi = new ArrayList<>();

        try {
            for (String index : indices) {
                int tortIndex = Integer.parseInt(index) - 1;
                if (tortIndex >= 0 && tortIndex < allTorturi.size()) {
                    selectedTorturi.add(allTorturi.get(tortIndex));
                } else {
                    System.out.println("Invalid cake number: " + (tortIndex + 1));
                }
            }

            if (selectedTorturi.isEmpty()) {
                System.out.println("No valid cakes selected. Order creation failed.");
            } else {
                comandaService.add(selectedTorturi, new Date());
                System.out.println("Order added successfully.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        }
    }

    protected void deleteTort(Scanner scanner) {
        System.out.println("Enter id from the cake you want to delete:");
        int id = scanner.nextInt();
        scanner.nextLine();
        try{
            tortService.delete(id);
            comandaService.update_after_delete(id);
            System.out.println("Cake deleted successfully");
        }
        catch (NoSuchElementException e)
        {
            System.out.println("Cake not found");
        }
    }

    protected void deleteComanda(Scanner scanner) {
        System.out.println("Enter id from the order you want to delete:");
        int id = scanner.nextInt();
        scanner.nextLine();
        try{
            comandaService.delete(id);
            System.out.println("Order deleted successfully");
        }catch (NoSuchElementException e){
            System.out.println("Order not found");
        }
    }

    protected void displayComenzi() {
        List<Comanda> comenzi = comandaService.display();
//        for(Comanda comanda : comenzi){
//            System.out.println(comanda);
        if (comenzi.isEmpty()) {
            System.out.println("No orders found");
        }
        else {
            comenzi.forEach(System.out::println);
        }
    }

    protected void displayTorturi() {
        List<Tort> tortList = tortService.display();
        if (tortList.isEmpty()) {
            System.out.println("No cakes found");
        }else {
            tortList.forEach(System.out::println);
        }
    }

//    public void addComanda(Scanner scanner) {
//        System.out.println("Enter type of cake ('stop' to return):");
//        List<Tort> torturi = new ArrayList<>();
//
//        while (true) {
//            String type = scanner.nextLine();
//
//            if (type.equalsIgnoreCase("stop")) {
//                if (torturi.isEmpty()) {
//                    System.out.println("You must add at least one cake before stopping the command.");
//                } else {
//                    comandaService.add(torturi, new Date());
//                    System.out.println("Comanda added");
//                    break;
//                }
//            } else if (type.isBlank()) {
//                System.out.println("Cake type cannot be empty. Please enter a valid type.");
//            } else {
//                tortService.add(type); // Add the cake to the repository
//                List<Tort> allTorturi = tortService.display(); // Fetch all existing cakes
//                Tort lastAddedTort = allTorturi.get(allTorturi.size() - 1); // Get the last added cake
//                torturi.add(lastAddedTort); // Add only the last cake to the current order
//                System.out.println("Cake added");
//            }
//        }
//    }
}
