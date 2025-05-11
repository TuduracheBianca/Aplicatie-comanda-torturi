package repository;
//

import domain.Comanda;

import java.io.IOException;
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;



public class ComandaTextFileRepository extends TextFileRepository<Comanda> {

    public ComandaTextFileRepository(String fileName) throws IOException {
        super(fileName);

    }

    @Override
    protected String toCvs(Comanda entity) {
        return entity.toCsv();
    }

    @Override
    public Comanda createEntityFromCsv(String csvLine) {
        return Comanda.fromCsv(csvLine);
    }
}

//    @Override
//    public Comanda createEntityFromCSV(String cvsLine){
//        return Comanda.fromCsv(cvsLine);
//    }
//
//    @Override
//    public String toCsv(Comanda comanda){
//        return comanda.toCvs();
//    }

//}
//    private static final Logger logger = Logger.getLogger(ComandaTextFileRepository.class.getName());
//    private String filePath;
//
//    public ComandaTextFileRepository(String filePath) {
//        this.filePath = filePath;
//        readEntitiesFromFile();
//    }
//
//    private String comandaToText(Comanda comanda) {
//        // Adaptează această parte la structura clasei tale Comanda
//        return comanda.getId() + "," + comanda.getDate() + "," + comanda.getTorturi(); // adaugă toate atributele necesare
//    }
//
//    public void writeEntitiesToFile() {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//            for (Comanda comanda : entities) {
//                writer.write(comandaToText(comanda));
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            logger.log(Level.SEVERE, "Error saving to file", e);
//        }
//    }
//
//    public void readEntitiesFromFile() {
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                entities.add(textToComanda(line));
//            }
//        } catch (IOException e) {
//            logger.log(Level.SEVERE, "Error reading from file", e);
//        }
//    }
//
//    private Comanda textToComanda(String text) {
//        String[] attributes = text.split(",");
//        int id = Integer.parseInt(attributes[0]);
//        String date = attributes[1];
//         = Integer.parseInt(attributes[2]);
//        return new Comanda(id, da, quantity);
//    }
//
//    @Override
//    public void update(int id, Comanda entity) {
//        super.update(id, entity);
//        writeEntitiesToFile();
//    }
//
//    @Override
//    public void add(Comanda entity) {
//        super.add(entity);
//        writeEntitiesToFile();
//    }
//
//    @Override
//    public Comanda findById(int id) {
//        return super.findById(id);
//    }
//
//    @Override
//    public void delete(Comanda entity) {
//        super.delete(entity);
//        writeEntitiesToFile();
//    }
//}