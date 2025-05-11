package repository;

import domain.Comanda;
import domain.Tort;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class SQLComandaRepository extends MemoryRepository<Comanda> implements AutoCloseable {

    private Connection connection = null;
    private static final String DB_URL = "jdbc:sqlite:src/main/java/torturi.db";

    public SQLComandaRepository() {
        openConnection();
        createTablesIfNotExists();
        //     initComandaTable(); // Initialize the table with sample data
        loadData();
    }

    private void openConnection() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(DB_URL);
        try {
            if (connection == null || connection.isClosed()) {
                connection = dataSource.getConnection();
            }
        } catch (SQLException e) {
            System.out.println("Error creating connection: " + e.getMessage());
        }
    }

    private void createTablesIfNotExists() {
        String createComandaTableSQL = "CREATE TABLE IF NOT EXISTS comanda (" +
                "id INTEGER PRIMARY KEY, " +
                "data INTEGER NOT NULL);";

        String createComandaTortJoinTableSQL = "CREATE TABLE IF NOT EXISTS comanda_torturi (" +
                "comanda_id INTEGER NOT NULL, " +
                "tort_id INTEGER NOT NULL, " +
                "FOREIGN KEY (comanda_id) REFERENCES comanda(id), " +
                "FOREIGN KEY (tort_id) REFERENCES tort(id));";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createComandaTableSQL);
            statement.execute(createComandaTortJoinTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating tables.", e);
        }
    }

    private void initComandaTable() {
        // Sample tort instances (you can adjust this based on your existing SQLTortRepository data)
        List<Tort> tortList1 = List.of(new Tort(101, "Ciocolata"), new Tort(102, "Fructe"));
        List<Tort> tortList2 = List.of(new Tort(103, "Caramel"));
        List<Tort> tortList3 = List.of(new Tort(104, "Lamaie"), new Tort(105, "Capsune"));
        List<Tort> tortList4 = List.of(new Tort(101, "Ciocolata"), new Tort(103, "Caramel"));
        List<Tort> tortList5 = List.of(new Tort(105, "Capsune"));

        List<Comanda> comandaList = new ArrayList<>();
        comandaList.add(new Comanda(1, tortList1, new Date()));
        comandaList.add(new Comanda(2, tortList2, new Date()));
        comandaList.add(new Comanda(3, tortList3, new Date()));
        comandaList.add(new Comanda(4, tortList4, new Date()));
        comandaList.add(new Comanda(5, tortList5, new Date()));

        String insertComandaSQL = "INSERT INTO comanda (id, data) VALUES (?, ?);";
        String insertComandaTortJoinSQL = "INSERT INTO comanda_torturi (comanda_id, tort_id) VALUES (?, ?);";

        try (PreparedStatement insertComandaStmt = connection.prepareStatement(insertComandaSQL);
             PreparedStatement insertJoinStmt = connection.prepareStatement(insertComandaTortJoinSQL)) {

            for (Comanda comanda : comandaList) {
                // Insert into comanda table
                insertComandaStmt.setInt(1, comanda.getId());
                insertComandaStmt.setLong(2, comanda.getDate().getTime());
                insertComandaStmt.executeUpdate();

                // Insert into comanda_torturi table
                for (Tort tort : comanda.getTorturi()) {
                    insertJoinStmt.setInt(1, comanda.getId());
                    insertJoinStmt.setInt(2, tort.getId());
                    insertJoinStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error initializing comanda table.", e);
        }
    }

    private void loadData() {
        List<Comanda> comandaList = (List<Comanda>) getAll();
        System.out.println("Found " + comandaList.size() + " comenzi in the table.");
        entities.addAll(comandaList);
    }

    @Override
    public void add(Comanda comanda) throws RepositoryException {
        super.add(comanda);
        String insertComandaSQL = "INSERT INTO comanda (id, data) VALUES (?, ?);";
        String insertComandaTortJoinSQL = "INSERT INTO comanda_torturi (comanda_id, tort_id) VALUES (?, ?);";

        try (PreparedStatement insertComandaStmt = connection.prepareStatement(insertComandaSQL);
             PreparedStatement insertJoinStmt = connection.prepareStatement(insertComandaTortJoinSQL)) {

            insertComandaStmt.setInt(1, comanda.getId());
            insertComandaStmt.setLong(2, comanda.getDate().getTime());
            insertComandaStmt.executeUpdate();

            for (Tort tort : comanda.getTorturi()) {
                insertJoinStmt.setInt(1, comanda.getId());
                insertJoinStmt.setInt(2, tort.getId());
                insertJoinStmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RepositoryException("Error adding Comanda to database: " + e.getMessage());
        }
    }

    @Override
    public void delete(Comanda comanda) throws RepositoryException {
        super.delete(comanda);
        int id = comanda.getId();
        String deleteComandaSQL = "DELETE FROM comanda WHERE id = ?;";
        String deleteComandaTortJoinSQL = "DELETE FROM comanda_torturi WHERE comanda_id = ?;";

        try (PreparedStatement deleteComandaStmt = connection.prepareStatement(deleteComandaSQL);
             PreparedStatement deleteJoinStmt = connection.prepareStatement(deleteComandaTortJoinSQL)) {

            deleteJoinStmt.setInt(1, id);
            deleteJoinStmt.executeUpdate();

            deleteComandaStmt.setInt(1, id);
            deleteComandaStmt.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException("Error removing Comanda from database: " + e.getMessage());
        }
    }

    @Override
    public List<Comanda> getAll() {
        List<Comanda> resultList = new ArrayList<>();
        String selectComandaSQL = "SELECT * FROM comanda;";
        String selectTortJoinSQL = "SELECT t.id, t.type FROM comanda_torturi ct " +
                "JOIN tort t ON ct.tort_id = t.id WHERE ct.comanda_id = ?;";

        try (PreparedStatement selectComandaStmt = connection.prepareStatement(selectComandaSQL);
             PreparedStatement selectJoinStmt = connection.prepareStatement(selectTortJoinSQL)) {

            ResultSet comandaResults = selectComandaStmt.executeQuery();
            while (comandaResults.next()) {
                int comandaId = comandaResults.getInt("id");
                Date comandaDate = new Date(comandaResults.getLong("data"));

                selectJoinStmt.setInt(1, comandaId);
                ResultSet tortResults = selectJoinStmt.executeQuery();

                List<Tort> tortList = new ArrayList<>();
                while (tortResults.next()) {
                    int tortId = tortResults.getInt("id");
                    String tortType = tortResults.getString("type");
                    tortList.add(new Tort(tortId, tortType));
                }

                Comanda comanda = new Comanda(comandaId, tortList, comandaDate);
                resultList.add(comanda);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving Comanda data: " + e.getMessage());
        }

        return resultList;
    }

    @Override
    public void update(int id, Comanda comanda) throws RepositoryException {
        // Call the base class's update method for in-memory update
        super.update(id, comanda);

        // Update the comanda in the database
        String updateComandaSQL = "UPDATE comanda SET data = ? WHERE id = ?;";
        String deleteOldTortsSQL = "DELETE FROM comanda_torturi WHERE comanda_id = ?;";
        String insertNewTortsSQL = "INSERT INTO comanda_torturi (comanda_id, tort_id) VALUES (?, ?);";

        try (PreparedStatement updateComandaStmt = connection.prepareStatement(updateComandaSQL);
             PreparedStatement deleteOldTortsStmt = connection.prepareStatement(deleteOldTortsSQL);
             PreparedStatement insertNewTortsStmt = connection.prepareStatement(insertNewTortsSQL)) {

            // Update the comanda table
            updateComandaStmt.setLong(1, comanda.getDate().getTime());
            updateComandaStmt.setInt(2, id);
            int rowsUpdated = updateComandaStmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new RepositoryException("No Comanda found with ID " + id + " to update.");
            }

            // Remove old tort entries linked to this comanda
            deleteOldTortsStmt.setInt(1, id);
            deleteOldTortsStmt.executeUpdate();

            // Insert new tort entries linked to this comanda
            for (Tort tort : comanda.getTorturi()) {
                insertNewTortsStmt.setInt(1, id);
                insertNewTortsStmt.setInt(2, tort.getId());
                insertNewTortsStmt.executeUpdate();
            }

            System.out.println("Comanda with ID " + id + " was successfully updated in the database.");

        } catch (SQLException e) {
            throw new RepositoryException("Error updating Comanda in the database: " + e.getMessage());
        }
    }


    @Override
    public void close() throws Exception {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}