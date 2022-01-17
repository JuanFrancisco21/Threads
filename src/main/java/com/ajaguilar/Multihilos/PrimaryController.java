package com.ajaguilar.Multihilos;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.multihilo;

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
    
    private multihilo pc;
    Thread Cliente;
    Thread Cliente2;
    Thread Hamburgesa;

    public void initialize() throws InterruptedException {
    	System.out.print("");
    	pc = new multihilo(Client,HAMBU);
	}

    @FXML
    public void Inicio()  throws InterruptedException {
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
        
        
		        Thread saludo = new Thread(new Runnable() {
		            @Override
		            public void run()
		            {
		            	try {
							Thread.currentThread().sleep(2000);
							System.out.println("Inicializando el programa");
							Thread.currentThread().sleep(1000);
							System.out.println("Probando el join de los hilos.");
							Thread.currentThread().sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		        });
 
		
        
        saludo.start();
        saludo.join();
        
		//Show stop button when saludo thread die.
        stop.setVisible(true);

        // Start both threads
        Cliente.start();
        Cliente2.start();
        Hamburgesa.start();
        
 
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
