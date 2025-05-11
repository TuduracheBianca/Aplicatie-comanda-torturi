package repository;//package repository;
//
//import domain.Comanda;

import domain.Entity;

import java.io.*;

//
public abstract class TextFileRepository<T extends Entity> extends MemoryRepository<T> {
    private String filePath;

    public TextFileRepository(String filePath) {
        this.filePath = filePath;
        readEntitiesFromFile();
    }

    /// Citeste din fisier
    public void readEntitiesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                T entity = createEntityFromCsv(line);
                entities.add(entity);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. A new one will be created.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /// Salvează în fișier
    public void writeEntitiesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (T entity : entities) {
                writer.write(toCvs(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }
    //
//    private String entityToText(T entity) {
//        if (entity instanceof Tort) {
//            return Tort.toText((Tort) entity);
//        } else if (entity instanceof Comanda) {
//            return Comanda.toText((Comanda) entity);
//        }
//        throw new IllegalArgumentException("Unknown entity type");
//    }
//
//    /// Încarcă din fișier
//    public void readEntitiesFromFile() {
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                entities.add(parseLineToEntity(line));
//            }
//        } catch (IOException e) {
//            System.err.println("Error reading from file: " + e.getMessage());
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    private T parseLineToEntity(String line) {
//        if (line.contains(",")) {
//            String[] parts = line.split(",");
//            if (parts.length == 3) {
//                return (T) Tort.fromText(line);
//            } else {
//                tortService tortService = service.tortService.getInstance();
//                return (T) Comanda.fromText(line, );
//            }
//        }
//        throw new IllegalArgumentException("Invalid line format");
//    }
//
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

    protected abstract String toCvs(T entity);
    public abstract T createEntityFromCsv(String csvLine);
}
//
////package repository;
////
////import domain.Comanda;
////import domain.Entity;
////import domain.Tort;
////
////import java.io.*;
////
////public class TextFileRepository<T extends Entity> extends MemoryRepository<T> {
////    private String file_path;
//////    private Parse<T> parses;
//////    protected abstract T parseEntity(String line);
//////    protected abstract String formatEntity(T entity);
////
////    public TextFileRepository(String filePath)  {
////        file_path = filePath;
////        readEntitiesFromFile();
////    }
////
////
////   ///salveaza in fisier
////   public void writeEntitiesToFile() {
////       try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_path))) {
////           for (T entity : entities) {
////               writer.write(entityToText(entity));
////               writer.newLine();
////           }
////       } catch (IOException e) {
////           System.out.println("Error saving to file: " + e.getMessage());
////       }
////   }
////
////    private String entityToText(T entity) {
////        if (entity instanceof Tort) {
////            return Tort.toText((Tort) entity);
////        } else if (entity instanceof Comanda) {
////            return Comanda.toText((Comanda) entity);
////        }
////        throw new IllegalArgumentException("Unknown entity type");
////    }
////
////    ///incarca din fisier
////    public void readEntitiesFromFile() {
////        try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) {
////            String line;
////            while ((line = reader.readLine()) != null) {
////                entities.add(parseLineToEntity(line));
////            }
////        } catch (IOException e) {
////            System.out.println("Error reading from file: " + e.getMessage());
////        }
////    }
////
////    private T parseLineToEntity(String line) {
////        if (line.contains(",")) {
////            String[] parts = line.split(",");
////            if (parts.length == 3) {
////                return (T) Tort.fromText(line);
////            } else {
////                return (T) Comanda.fromText(line);
////            }
////        }
////        throw new IllegalArgumentException("Invalid line format");
////    }
////
////    @Override
////    public void update(int id, T entity)
////    {
////        super.update(id, entity);
////        writeEntitiesToFile();
////    }
////
////    @Override
////    public void add(T entity)
////    {
////        super.add(entity);
////        writeEntitiesToFile();
////    }
////
////    @Override
////    public T findById(int id)
////    {
////        return super.findById(id);
////    }
////
////    @Override
////    public void delete(T entity)
////    {
////        super.delete(entity);
////        writeEntitiesToFile();
////    }
////
//////    protected abstract String formatEntity(Tort tort);
////
////
///////    //add
//////    public static void addObject(Tort tort) throws IOException {
//////        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
//////            writer.write(tort.toString());
//////            writer.newLine();
//////        }
//////    }
//////
///////    //delete
//////    public static void deleteObject(Tort tort) throws IOException {
//////        List<Tort> torts = readObject();
//////        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
//////            String line;
//////            while ((line = reader.readLine()) != null) {
//////                if (line.equals(tort.toString())) {
//////                    torts.remove(tort);
//////                    break;
//////                }
//////            }
//////        }
//////        writeObject(torts);
//////    }
////}