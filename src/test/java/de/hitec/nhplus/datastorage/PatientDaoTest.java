package de.hitec.nhplus.datastorage;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PatientDaoTest {

    private static Connection connection;

    /**
    * Initialisiert Verbindung zur Datenbank.
    */
    @BeforeEach
    public void setUp() {
        connection = ConnectionBuilder.getConnection();
    }

    /**
     * Schließt die Verbindung zur Datenbank, nachdem alle Tests ausgeführt wurden.
     */
    @AfterAll
    public static void closeConnection() {
        ConnectionBuilder.closeConnection();
    }

    /**
     * Überprüft, ob eine bestimmte Spalte in der Tabelle "Patient" vorhanden ist.
     * Wenn die Spalte existiert, wird der Test bestanden.
     * Wenn die Spalte nicht existiert, wird der Test nicht bestanden.
     */
    @Test
    public void testIfColumnExistsInPatientTable() {
        String columnName = "assets";
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, "patient", columnName);
            boolean columnExists = resultSet.next();
            resultSet.close();

            if (columnExists) {
                assertTrue(true, "Die Spalte existiert in der 'patient' Tabelle.");
            } else {
                assertTrue(false,"Die Spalte existiert nicht in der 'patient' Tabelle");
            }
        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }
}
