package repository;

import domain.Tort;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLTortRepository extends MemoryRepository<Tort> implements AutoCloseable {

    private Connection connection= null;
    private String DB_URL = "jdbc:sqlite:src/main/java/torturi.db";

    // Constructor
    public SQLTortRepository() {
        openConnection();
        createTableIfNotExists();
        //initTortTable();
        loadData();
    }

    // Open connection to the SQLite database
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

    // Create the table if it doesn't already exist
    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS tort (" +
                "id INTEGER PRIMARY KEY, " +
                "type TEXT NOT NULL);";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating table.", e);
        }
    }

    // Load data from the database into the repository's entities list
    private void loadData() {
        List<Tort> torturiList = (List<Tort>) getAll();
        System.out.println("Found " + torturiList.size() + " torturi in the table.");
        entities.addAll(torturiList);
    }

    // Initialize the table with sample data if required (uncomment if you want this feature)
    private void initTortTable() {
        List<Tort> torturiList = new ArrayList<>();
        torturiList.add(new Tort(101, "Ciocolata"));
        torturiList.add(new Tort(102, "Fructe"));
        torturiList.add(new Tort(103, "Caramel"));
        torturiList.add(new Tort(104, "Lamaie"));
        torturiList.add(new Tort(105, "Capsune"));

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO tort VALUES (?, ?);")) {
            for (Tort tort : torturiList) {
                statement.setInt(1, tort.getId());
                statement.setString(2, tort.getTip_tort());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting torturi", e);
        }
    }

    // Add a new Tort to the database
    @Override
    public void add(Tort tort) throws RepositoryException {
        super.add(tort);
        String insertSQL = "INSERT INTO tort (id, type) VALUES (?, ?);";
        try (PreparedStatement addStatement = connection.prepareStatement(insertSQL)) {
            addStatement.setInt(1, tort.getId());
            addStatement.setString(2, tort.getTip_tort());
            int executionResult = addStatement.executeUpdate();
            System.out.println("Execution result from add(): " + executionResult);
        } catch (SQLException e) {
            throw new RepositoryException("Error adding Tort to database: " + e.getMessage());
        }
    }

    // Remove a Tort by its ID
    @Override
    public void delete(Tort tort) throws RepositoryException {
        super.delete(tort);
        int id = tort.getId();
        String deleteSQL = "DELETE FROM tort WHERE id = ?;";
        try (PreparedStatement removeStatement = connection.prepareStatement(deleteSQL)) {
            removeStatement.setInt(1, id);
            removeStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Error removing Tort from database: " + e.getMessage());
        }
    }

    // Get all Tort objects from the database
    @Override
    public List<Tort> getAll() {
        List<Tort> resultList = new ArrayList<>();
        String selectSQL = "SELECT * FROM tort;";
        try (PreparedStatement getAllStatement = connection.prepareStatement(selectSQL)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                Tort tort = new Tort(resultSet.getInt("id"), resultSet.getString("type"));
                resultList.add(tort);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving Tort data: " + e.getMessage());
        }
        return resultList;
    }

    // Close the database connection
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    // Update a Tort in the database and in-memory
    @Override
    public void update(int id, Tort item) throws RepositoryException {
        super.update(id, item); // Use base repository to update in-memory list
        String updateSQL = "UPDATE tort SET type = ? WHERE id = ?;";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
            updateStatement.setString(1, item.getTip_tort());
            updateStatement.setInt(2, id);
            int rowsUpdated = updateStatement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new RepositoryException("No Tort found with ID " + id + " to update.");
            }

            System.out.println("Tort with ID " + id + " was successfully updated.");
        } catch (SQLException e) {
            throw new RepositoryException("Error updating Tort in the database: " + e.getMessage());
        }
    }

    // Override close to be used with try-with-resources
    @Override
    public void close() throws Exception {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error if necessary
        }
    }
}