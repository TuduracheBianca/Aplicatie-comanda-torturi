


package repository;


import domain.Entity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileRepo<T extends Entity> extends MemoryRepository<T> {
    private final String filePath;

    public BinaryFileRepo(String filePath) {
        this.filePath = filePath;
//        createFileIfNotExists();
        readEntitiesFromFile();
    }

//    private void createFileIfNotExists() {
//        File file = new File(filePath);
//        if (!file.exists()) {
//            try {
//                if (file.createNewFile()) {
//                    logger.info("File created: " + filePath);
//                } else {
//                    logger.warning("Failed to create file: " + filePath);
//                }
//            } catch (IOException e) {
//                logger.log(Level.SEVERE, "Error creating file", e);
//            }
//        }
//    }

    private void readEntitiesFromFile() {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(filePath))) {
            List<T> lista = (List<T>) input.readObject();
            entities.clear();
            entities.addAll(lista);
        }catch (FileNotFoundException e){
            System.out.println("File not found. A new one will be created.");
        }catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
//            T entity;
//            while ((entity = (T) input.readObject()) != null) {
//                entities.add(entity);
//            }
//        } catch (EOFException e) {
//            // End of file reached
//        } catch (IOException | ClassNotFoundException e) {
//            logger.log(Level.SEVERE, "Error reading from file", e);
//        }
    }

    public void writeEntitiesToFile() {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath))) {
            output.writeObject(new ArrayList<>(entities));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
//            for (T entity : entities) {
//                output.writeObject(entity);
//            }
//        } catch (IOException e) {
//            logger.log(Level.SEVERE, "Error saving to file", e);
//        }
    }

    @Override
    public void update(int id, T entity) {
        super.update(id, entity);
        writeEntitiesToFile();
    }

    @Override
    public void add(T entity) {
        super.add(entity);
        writeEntitiesToFile();
    }

    @Override
    public T findById(int id) {
        return super.findById(id);
    }

    @Override
    public void delete(T entity) {
        super.delete(entity);
        writeEntitiesToFile();
    }
}
//import domain.Entity;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class BinaryFileRepo<T extends Entity> extends MemoryRepository<T> {
//    private String file_path;
//    protected List<T> entity = new ArrayList<>();
//
//    public BinaryFileRepo(String file_path) {
//        this.file_path = file_path;
//        readObjectsfromfile();
//    }
//    @Override
//    public void add(T entity)
//    {
//        super.add(entity);
//        saveFile();
//    }
//
//    @Override
//    public void  delete(T entity) {
//        super.delete(entity);
//        saveFile();
//    }
//
//    @Override
//    public T findById(int id) {
//        return super.findById(id);
//    }
//
//    @Override
//    public void update(int id, T entity) {
//        super.update(id,entity);
//        saveFile();
//    }
//
//
//
////
//    ///salveaza in fisier
//    public void saveFile(){
//        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file_path))) {
//            //write the size
//            oos.writeObject(entities.size());
//
//            //write orders
//            for (T entity : entities) {
//                oos.writeObject(entity);
//            }
//        }catch (IOException e){
//            System.out.println("eroare la salvare date" + e.getMessage());
//        }
//    }
//
//   ///incarca din fisier
//    public void readObjectsfromfile() {
//
//        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file_path))) {
//            entities = (List<T>) ois.readObject();
//        }catch (IOException | ClassNotFoundException e){
//            System.out.println("eroare la salvare date" + e.getMessage());
//        }
//    }
//
//
////
////  //append object
////    public static void appendObjects(List<Comanda> order) throws IOException {
////        List<Comanda> orders = new ArrayList<>();
////        try {
////            orders = readObjects();
////        }catch (EOFException | ClassNotFoundException e){
////        }
////        orders.addAll(order);
////        writeObjects(orders);
////    }
////
/////    //update
////    public static boolean updateObjects(Date data, Comanda neworder) throws IOException, ClassNotFoundException {
////        List<Comanda> orders = readObjects();
////        boolean found = false;
////
////        for(Comanda comanda : orders){
////            if(comanda.getId() == neworder.getId()){
////                orders.set(orders.indexOf(comanda), neworder);
////                found = true;
////                break;
////            }
////        }
////
////        if (found) {
////            writeObjects(orders);
////        }
////        return found;
////    }
//}