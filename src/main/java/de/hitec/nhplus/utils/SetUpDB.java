package de.hitec.nhplus.utils;

import de.hitec.nhplus.datastorage.*;
import de.hitec.nhplus.model.Caregiver;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;
import de.hitec.nhplus.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static de.hitec.nhplus.utils.DateConverter.convertStringToLocalDate;
import static de.hitec.nhplus.utils.DateConverter.convertStringToLocalTime;

/**
 * Call static class provides to static methods to set up and wipe the database. It uses the class ConnectionBuilder
 * and its path to build up the connection to the database. The class is executable. Executing the class will build
 * up a connection to the database and calls setUpDb() to wipe the database, build up a clean database and fill the
 * database with some test data.
 */
public class SetUpDB {

    /**
     * This method wipes the database by dropping the tables. Then the method calls DDL statements to build it up from
     * scratch and DML statements to fill the database with hard coded test data.
     */
    public static void setUpDb() {
        Connection connection = ConnectionBuilder.getConnection();
        SetUpDB.wipeDb(connection);
        SetUpDB.setUpTablePatient(connection);
        SetUpDB.setUpTableTreatment(connection);
        SetUpDB.setUpTableCaregiver(connection);
        SetUpDB.setUpTableUser(connection);
        SetUpDB.setUpPatients();
        SetUpDB.setUpCaregivers();
        SetUpDB.setUpTreatments();
        SetUpDB.setUpUsers();
    }

    /**
     * This method wipes the database by dropping the tables.
     * @param connection the connection to the database
     */
    public static void wipeDb(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE treatment");
            statement.execute("DROP TABLE patient");
            statement.execute("DROP TABLE caregiver");
            statement.execute("DROP TABLE user");
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * This method sets up the table patient in the database.
     * @param connection the connection to the database
     */
    private static void setUpTableUser(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS user (" +
                "   id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   email TEXT NOT NULL, " +
                "   password TEXT NOT NULL, " +
                "   status INTEGER NOT NULL" +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * This method sets up the table user in the database.
     */
    private static void setUpUsers() {
        try {
            UserDao dao = DaoFactory.getDaoFactory().createUserDAO();
            dao.create(new User("user3@gmail.com", hashPassword("333333"), 1));
            dao.create(new User("user2@gmail.com", hashPassword("222222"), 0));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * This method sets up the table patient in the database.
     * @param connection the connection to the database
     */
    private static void setUpTablePatient(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS patient (" +
                "   pid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   firstname TEXT NOT NULL, " +
                "   surname TEXT NOT NULL, " +
                "   dateOfBirth TEXT NOT NULL, " +
                "   carelevel TEXT NOT NULL, " +
                "   roomnumber TEXT NOT NULL, " +
                "   locked BOOLEAN NOT NULL, " +
                "   datecreated TEXT NOT NULL " +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * This method sets up the table treatment in the database.
     * @param connection the connection to the database
     */
    private static void setUpTableTreatment(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS treatment (" +
                "   tid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   pid INTEGER NOT NULL, " +
                "   cid INTEGER NOT NULL," +
                "   treatment_date TEXT NOT NULL, " +
                "   begin TEXT NOT NULL, " +
                "   end TEXT NOT NULL, " +
                "   description TEXT NOT NULL, " +
                "   remark TEXT NOT NULL," +
                "   locked BOOLEAN NOT NULL, " +
                "   datecreated TEXT NOT NULL, " +
                "   FOREIGN KEY (cid) REFERENCES caregiver (cid), " +
                "   FOREIGN KEY (pid) REFERENCES patient (pid) " +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }


    /**
     * This method sets up the example patients in the database.
     */
    private static void setUpPatients() {
        try {
            PatientDao dao = DaoFactory.getDaoFactory().createPatientDAO();
            dao.create(new Patient("Seppl", "Herberger", convertStringToLocalDate("1945-12-01"), "4", "202", false, DateConverter.convertLocalDateToString(LocalDate.now())));
            dao.create(new Patient("Martina", "Gerdsen", convertStringToLocalDate("1954-08-12"), "5", "010", false, DateConverter.convertLocalDateToString(LocalDate.now())));
            dao.create(new Patient("Gertrud", "Franzen", convertStringToLocalDate("1949-04-16"), "3", "002", false, DateConverter.convertLocalDateToString(LocalDate.now())));
            dao.create(new Patient("Ahmet", "Yilmaz", convertStringToLocalDate("1941-02-22"), "3", "013", false, DateConverter.convertLocalDateToString(LocalDate.now())));
            dao.create(new Patient("Hans", "Neumann", convertStringToLocalDate("1955-12-12"), "2", "001", false, DateConverter.convertLocalDateToString(LocalDate.now())));
            dao.create(new Patient("Elisabeth", "Müller", convertStringToLocalDate("1958-03-07"), "5", "110", false, DateConverter.convertLocalDateToString(LocalDate.now())));
            dao.create(new Patient("User", "Delete", convertStringToLocalDate("1999-08-22"), "5", "97", false, "2001-01-01"));
            dao.create(new Patient("User", "Not delete", convertStringToLocalDate("1999-08-22"), "5", "97", false, "2020-01-01"));//should be not deleted
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * This method sets up the example treatments in the database.
     */
    private static void setUpTreatments() {
        try {
            TreatmentDao dao = DaoFactory.getDaoFactory().createTreatmentDao();
            dao.create(new Treatment(1, 1, convertStringToLocalDate("2023-06-03"), convertStringToLocalTime("11:00"), convertStringToLocalTime("15:00"), "Gespräch", "Der Patient hat enorme Angstgefühle und glaubt, er sei überfallen worden. Ihm seien alle Wertsachen gestohlen worden.\nPatient beruhigt sich erst, als alle Wertsachen im Zimmer gefunden worden sind.", false, DateConverter.convertLocalDateToString(LocalDate.now())));
            dao.create(new Treatment(2, 1, convertStringToLocalDate("2023-06-05"), convertStringToLocalTime("11:00"), convertStringToLocalTime("12:30"), "Gespräch", "Patient irrt auf der Suche nach gestohlenen Wertsachen durch die Etage und bezichtigt andere Bewohner des Diebstahls.\nPatient wird in seinen Raum zurückbegleitet und erhält Beruhigungsmittel.", false, DateConverter.convertLocalDateToString(LocalDate.now())));
            dao.create(new Treatment(3, 2, convertStringToLocalDate("2023-06-04"), convertStringToLocalTime("07:30"), convertStringToLocalTime("08:00"), "Waschen", "Patient mit Waschlappen gewaschen und frisch angezogen. Patient gewendet.", false, DateConverter.convertLocalDateToString(LocalDate.now())));
            dao.create(new Treatment(4, 1, convertStringToLocalDate("2023-06-06"), convertStringToLocalTime("15:10"), convertStringToLocalTime("16:00"), "Spaziergang", "Spaziergang im Park, Patient döst  im Rollstuhl ein", false, DateConverter.convertLocalDateToString(LocalDate.now())));
            dao.create(new Treatment(4, 1, convertStringToLocalDate("2023-06-08"), convertStringToLocalTime("15:00"), convertStringToLocalTime("16:00"), "Spaziergang", "Parkspaziergang; Patient ist heute lebhafter und hat klare Momente; erzählt von seiner Tochter", false, DateConverter.convertLocalDateToString(LocalDate.now())));
            dao.create(new Treatment(3, 2, convertStringToLocalDate("2023-06-07"), convertStringToLocalTime("11:00"), convertStringToLocalTime("11:30"), "Waschen", "Waschen per Dusche auf einem Stuhl; Patientin gewendet;", false, DateConverter.convertLocalDateToString(LocalDate.now())));
            dao.create(new Treatment(2, 5, convertStringToLocalDate("2023-06-08"), convertStringToLocalTime("15:00"), convertStringToLocalTime("15:30"), "Physiotherapie", "Übungen zur Stabilisation und Mobilisierung der Rückenmuskulatur", false, DateConverter.convertLocalDateToString(LocalDate.now())));
            dao.create(new Treatment(2, 4, convertStringToLocalDate("2023-08-24"), convertStringToLocalTime("09:30"), convertStringToLocalTime("10:15"), "KG", "Lympfdrainage", false, DateConverter.convertLocalDateToString(LocalDate.now())));
            dao.create(new Treatment(1, 6, convertStringToLocalDate("2023-08-31"), convertStringToLocalTime("13:30"), convertStringToLocalTime("13:45"), "Toilettengang", "Hilfe beim Toilettengang; Patientin klagt über Schmerzen beim Stuhlgang. Gabe von Iberogast", false, DateConverter.convertLocalDateToString(LocalDate.now())));
            dao.create(new Treatment(2, 6, convertStringToLocalDate("2023-09-01"), convertStringToLocalTime("16:00"), convertStringToLocalTime("17:00"), "KG", "Massage der Extremitäten zur Verbesserung der Durchblutung", false, DateConverter.convertLocalDateToString(LocalDate.now())));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * This method sets up the table caregiver in the database.
     * @param connection the connection to the database
     */
private static void setUpTableCaregiver(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS caregiver (" +
                "   cid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   firstname TEXT NOT NULL, " +
                "   surname TEXT NOT NULL, " +
                "   phonenumber TEXT NOT NULL, " +
                "   locked BOOLEAN NOT NULL, " +
                "   datecreated TEXT NOT NULL" +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * This method sets up the example caregivers in the database.
     */
    private static void setUpCaregivers() {
        try {
            CaregiverDAO dao = DaoFactory.getDaoFactory().createCaregiverDAO();
            dao.create(new Caregiver(0, "Hans", "Müller", "0176-12345678", false, "2023-06-03"));
            dao.create(new Caregiver(1, "Karin", "Schmidt", "0176-12345679", false, "2023-06-03"));
            dao.create(new Caregiver(2, "Peter", "Schneider", "0176-12345680", true, "2023-06-03"));
            dao.create(new Caregiver(3, "Klaus", "Fischer", "0176-12345681", false, "2023-06-03"));
            dao.create(new Caregiver(4, "Sabine", "Weber", "0176-12345682", false, "2023-06-03"));
            dao.create(new Caregiver(5, "Andrea", "Meyer", "0176-12345683", false, "2023-06-03"));
            dao.create(new Caregiver(6, "Thomas", "Meyer", "0176-12345683", false, "2023-06-03"));
            dao.create(new Caregiver(7, "User", "Delete", "0176-12345683", false, "2001-01-01"));
            dao.create(new Caregiver(8, "User", "Not Delete", "0176-12345683", false, "2020-01-01"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    /**
     * This method hashes the given password. As a placeholder, this method simply returns the password as is.
     * @param number the password to hash.
     * @return the hashed password.
     */
    private static String hashPassword(String number) {
        return number;
    }

    /**
     * This method is the main method to execute the class.
     * @param args the arguments to execute the class. (There are none at the moment).
     */
    public static void main(String[] args) {
        SetUpDB.setUpDb();
    }
}
