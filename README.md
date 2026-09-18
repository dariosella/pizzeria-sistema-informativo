# Thin client Pizzeria

Il progetto mantiene la struttura MVC/DAO fornita:

- `pizzeria.controller`: controller dell'applicazione e dei quattro ruoli;
- `pizzeria.model.dao`: un DAO per ciascuna stored procedure;
- `pizzeria.model.domain`: oggetti usati per trasferire i risultati;
- `pizzeria.view`: login e viste testuali dei quattro ruoli;
- `resources/db.properties`: account tecnici MySQL;
- `libs`: MySQL Connector/J 8.0.31.

## Avvio

1. Eseguire `pizzeriascript.sql` in MySQL Workbench.
2. Aprire questa cartella come progetto in IntelliJ IDEA.
3. Verificare che sia configurato un JDK 17 o successivo.
4. Eseguire la configurazione `Pizzeria` oppure la classe `pizzeria.Main`.

Utente applicativo iniziale:

```text
username: d.sella
password: dario123
ruolo: manager
```

Il client interroga il database esclusivamente attraverso le stored procedure.
Gli account tecnici configurati in `db.properties` necessitano quindi dei soli
privilegi `EXECUTE` assegnati dallo script SQL.
