package pizzeria.model.domain;

public enum Role {
    // Ogni ruolo è associato al numero restituito dalla procedura login.
    // associa ogni ruolo a un numero restituito dal database
    MANAGER(1),
    CAMERIERE(2),
    PIZZAIOLO(3),
    BARMAN(4);

    private final int id;

    private Role(int id) {
        // Memorizza il numero del ruolo.
        this.id = id;
    }

    public static Role fromInt(int id) {
        /*
        values() restituisce tutti i ruoli;
        il ciclo li esamina uno per uno;
        confronta l’identificatore;
        restituisce il ruolo corrispondente;
        se nessuno corrisponde, restituisce null.
         */
        for (Role type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    // Serve per passare il valore corretto alla colonna ruolo della tabella User.
    public String getDatabaseValue() {
        // MANAGER -> manager
        return name().toLowerCase();
    }
}

