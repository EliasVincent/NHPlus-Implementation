package de.hitec.nhplus.datastorage;

/**
 * The DaoFactory class is a Factory Pattern to create DAO objects.
 */
public class DaoFactory {

    private static DaoFactory instance;

    /**
     * Empty constructor.
     */
    private DaoFactory() {
    }

    /**
     * Returns the instance of the DaoFactory class.
     * If the instance is null, a new instance is created.
     * @return the instance of the DaoFactory class
     */
    public static DaoFactory getDaoFactory() {
        if (DaoFactory.instance == null) {
            DaoFactory.instance = new DaoFactory();
        }
        return DaoFactory.instance;
    }

    /**
     * Creates a new TreatmentDao object.
     * @return a new TreatmentDao object
     */
    public TreatmentDao createTreatmentDao() {
        return new TreatmentDao(ConnectionBuilder.getConnection());
    }

    /**
     * Creates a new PatientDao object.
     * @return a new PatientDao object
     */
    public PatientDao createPatientDAO() {
        return new PatientDao(ConnectionBuilder.getConnection());
    }

    /**
     * Creates a new CaregiverDAO object.
     * @return a new CaregiverDAO object
     */
    public CaregiverDAO createCaregiverDAO() {
        return new CaregiverDAO(ConnectionBuilder.getConnection());
    }

    /**
     * Creates a new UserDao object.
     * @return a new UserDao object
     */
    public UserDao createUserDAO() { return new UserDao(ConnectionBuilder.getConnection()); }
}
