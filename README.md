# Sistema informativo di una pizzeria

Sistema informativo per la gestione di una pizzeria, sviluppato come progetto universitario per l'esame di Basi di Dati.  
L'applicazione implementa una soluzione a client leggero (thin client) che interroga il database esclusivamente tramite stored procedure, seguendo un'architettura MVC/DAO.
> Per ulteriori informazioni relative al processo di progettazione del database, dall'analisi dei requisiti fino alla progettazione fisica, leggere la relazione  in PDF allegata.

## Obiettivo

Gestire in modo centralizzato le attività principali di un locale di pizzeria, tra cui:

- apertura e chiusura comande;
- gestione tavoli e ordini;
- registrazione di ordini da asporto e consegna a domicilio;
- preparazione e completamento prodotti;
- calcolo entrate giornaliere e mensili;
- autenticazione degli utenti in base al ruolo.

## Architettura

Il thin client è organizzato secondo il pattern MVC/DAO:

- `pizzeria.controller`: controller dell'applicazione e delle funzionalità per i quattro ruoli;
- `pizzeria.model.dao`: accesso ai dati tramite DAO e stored procedure;
- `pizzeria.model.domain`: classi di dominio per la rappresentazione dei dati;
- `pizzeria.view`: interfaccia testuale e pagine di login/gestione;
- `resources/db.properties`: configurazione dei profili tecnici di accesso al database;
- `libs`: dipendenze esterne, inclusi i driver MySQL Connector/J.

## Requisiti

Prima di avviare l'applicazione assicurati di avere:

- Java 17 o superiore;
- MySQL Server;
- MySQL Workbench (consigliato per eseguire lo script SQL);
- IntelliJ IDEA (o un altro IDE Java equivalente).

## Struttura del progetto

```text
pizzeria-sistema-informativo/
├── src/
│   ├── pizzeria/
│   │   ├── controller/
│   │   ├── model/
│   │   │   ├── dao/
│   │   │   ├── domain/
│   │   └── view/
│   └── resources/
│       └── db.properties
├── libs/
│   └── mysql-connector-j-8.0.31.jar
├── pizzeriascript.sql
├── README.md
└── Relazione_DarioSella.pdf
```

## Setup del database

1. Aprire `pizzeriascript.sql` in MySQL Workbench.
2. Eseguire lo script SQL per creare:
   - lo schema `Pizzeria`;
   - le tabelle;
   - le stored procedure;
   - le view;
   - i trigger;
   - gli utenti applicativi e i relativi permessi.

> Il database viene inizializzato in modo completo mediante lo script fornito nel repository.

## Avvio dell'applicazione

1. Aprire la cartella del progetto in IntelliJ IDEA.
2. Verificare che sia configurato un JDK 17 o successivo.
3. Controllare i parametri di connessione in `resources/db.properties`.
4. Eseguire la configurazione `Pizzeria` oppure avviare la classe:
   - `pizzeria.Main`

## Utente iniziale

Per testare l'applicazione subito dopo l'avvio, utilizzare il seguente account:

```text
username: d.sella
password: dario123
ruolo: manager
```

## Ruoli supportati

Il sistema prevede i seguenti ruoli:

- `manager`
- `cameriere`
- `pizzaiolo`
- `barman`

Ogni ruolo ha accesso a funzionalità specifiche dell'applicazione.

## Sicurezza e accesso ai dati

Il client non esegue query SQL direttamente sul database:  
interroga il DB esclusivamente tramite stored procedure.

Questo approccio:
- riduce il rischio di query SQL non controllate;
- centralizza la logica di accesso ai dati;
- rende il sistema più conforme alle best practice di sicurezza applicativa.

Gli account tecnici presenti in `db.properties` richiedono quindi solo i privilegi di `EXECUTE` assegnati dallo script SQL.

## Funzionalità principali

- creazione e gestione di comande;
- gestione di tavoli occupati/liberi;
- registrazione ordini da asporto e consegna;
- controllo dello stato dei prodotti (`non pronta` / `pronta`);
- generazione scontrini e calcolo totale;
- visualizzazione entrate giornaliere e mensili;
- autenticazione con ruoli distinti.

## Documentazione

Nel repository è presente anche un documento di relazione:

- `Relazione_DarioSella.pdf`

## Note finali

Questo progetto è pensato come esempio applicativo di database relazionale con logica business gestita tramite stored procedure e accesso controllato dal livello applicativo.

Se vuoi approfondire il funzionamento del sistema o le scelte progettuali, consulta anche la relazione allegata.

## Licenza

Il repository non specifica una licenza esplicita.  
Prima di riutilizzare il progetto in ambito commerciale o pubblico, verificare se è richiesto un accordo di utilizzo.
