package pizzeria.exception;

/*
Serve a separare gli errori JDBC dal resto dell’applicazione.
È un’eccezione controllata utilizzata dal livello DAO.
Essendo una sottoclasse di Exception, il controller deve:
intercettarla con catch;
oppure dichiararla con throws.
*/
public class DAOException extends Exception {
    // È un identificatore usato dalla serializzazione Java
    private static final long serialVersionUID = 1L;

    // Il controller riceve una DAOException e mostra un messaggio comprensibile.
    public DAOException(String message, Throwable cause) {
        /*
        Conserva:
        un messaggio comprensibile;
        la SQLException originale.
         */
        super(message, cause);
    }

    public DAOException(String message) {
        // Permette di creare un errore usando solamente un messaggio.
        super(message);
    }
}
