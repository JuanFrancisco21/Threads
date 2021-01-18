package com.ajaguilar.Multihilos;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.macdonals;
import model.cooperantes.Contador;
import model.cooperantes.Hilo;

public class PrimaryController {
	@FXML
	private ListView<String> Client;
	@FXML
	private ListView<String> HAMBU;
    @FXML
    private Button start;
    @FXML
    private Button inicio;
    @FXML
    private Button stop;
    
    private macdonals pc;
    Thread Cliente;
    Thread Cliente2;
    Thread Hamburgesa;

    //Valores de contador cooperante
    private static final int NUM_HILOS = 5; 
	private static final int CUENTA_TOTAL = 50; 
    
    public void initialize() throws InterruptedException {
    	System.out.print("");
    	pc = new macdonals(Client,HAMBU);
		Client.getItems().add("Contandos todos los trabajadores");

		Thread.currentThread().sleep(2000);
    	contadorTrabajadores();

	}

    /**
     * Function called by inicio button, start saludo thread, which use join.
     * after saludo thread die, start 2 producer threads and 1 consumer thread.
     * @throws InterruptedException
     */
    @FXML
    public void Inicio()  throws InterruptedException {
		Client.getItems().clear();
		
    	//Hide buttos for error control, before Thread saludo start.
        start.setVisible(false);
        inicio.setVisible(false);

 
    	// Object of a class that has both produce()
        // and consume() methods
 
        // Create producer thread
         Cliente = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.produce("H1 ");
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
      // Create producer thread
         Cliente2 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.produce("H2 ");
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
 
        // Create consumer thread
        Hamburgesa = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.consume();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        
		//Show stop button when saludo thread die.
        stop.setVisible(true);

        // Start both threads
        Cliente.start();
        Cliente2.start();
        Hamburgesa.start();
        
 
    }
    
    private void contadorTrabajadores(){
    	try {
	    	Contador c = new Contador();
			Thread[] hilos = new Thread[NUM_HILOS];
				
			for (int i = 0; i < NUM_HILOS; i++) {
				Thread th= new Thread(new Hilo(i, CUENTA_TOTAL/NUM_HILOS,c));
				th.start();
				hilos[i] = th;
	
	
				if (i==NUM_HILOS-1) {
					try {
						th.join();
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			Client.getItems().add("Trabajadores totales:"+c.getCuenta());
			System.out.printf("Trabajadores totales: %s\n",c.getCuenta());
	    	

    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
    
    
    /**
     * Restart Clientes Thread.
     */
    @FXML
    private void start(){
    	try {
    		//Cliente.resume();
        	pc.setActiveClient(true);
        	start.setVisible(false);
            stop.setVisible(true);
            pc.notifi();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    /**
     * Stop Clientes Thread
     * but hamburgesa continues.
     */
    @FXML
    private void stop(){
    	//Cliente.interrupt();
    	pc.setActiveClient(false);
    	
    	start.setVisible(true);
        stop.setVisible(false);
    }
    
    /**
     * Stop threads(Kill then)
     * @throws IOException
     */
    @FXML
    private void Killer() throws IOException {
    	Cliente.stop();
    	Hamburgesa.stop();
    	
    	start.setVisible(false);
        inicio.setVisible(true);
        stop.setVisible(false);
    }
    
    @FXML
    private void switchToSecondary() throws IOException {
    	App.setRoot("secondary");
    }
}
