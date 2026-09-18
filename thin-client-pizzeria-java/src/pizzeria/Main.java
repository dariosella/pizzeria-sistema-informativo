package pizzeria;

import pizzeria.controller.ApplicationController;

/*
Java cerca il metodo main;
viene creato un ApplicationController;
viene invocato il suo metodo start();
il controllo dell’applicazione passa interamente ad ApplicationController.
 */
public class Main {
    // Avvia il controller principale, punto di ingresso dell'applicazione
    public static void main(String[] args) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.start();
    }
}