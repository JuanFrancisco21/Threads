package model;

import java.util.LinkedList;

import javafx.application.Platform;
import javafx.scene.control.ListView;

//This class has a list, producer (adds items to list
// and consumer (removes items).
public class multihilo {
	private final int capacity = 2;
	int value=0;
	private Boolean activeClient;
	private ListView<String> Client;
	private ListView<String> HAMBU;
	
	// Create a list shared by producer and consumer
    // Size of list is 2.
    LinkedList<Integer> list = new LinkedList<>();

    public multihilo(ListView<String> client, ListView<String> hAMBU) {
    	activeClient=true;
    	Client = client;
    	HAMBU = hAMBU;
    }
    

    public Boolean getActiveClient() {
		return activeClient;
	}
	public void setActiveClient(Boolean activeClient) {
		this.activeClient = activeClient;
	}


	// Function called by producer thread
    public void produce(String name) throws InterruptedException{
        while (true) {
            synchronized (this)
            {
                // producer thread waits while list
                // is full
                while (list.size() == capacity || activeClient==false) {
                	//Thread.currentThread().stop();
                    wait();
                }
                System.out.println("Produce - " + value);
                addText(Client, name+"Cliente "+value+ " pide ");
                // HAMBU.getItems().add("Pedido "+value+ " servido");
                

                // to insert the jobs in the list
                list.add(value++);

                // notifies the consumer thread that
                // now it can start consuming
                notifyAll();

                // makes the working of program easier
                // to  understand
                Thread.sleep(1000);
            }
        }
    }
    
    
    
    public synchronized void  notifi() throws InterruptedException{
             // notifies the consumer thread that
             // now it can start consuming
             notifyAll();             
    }
    
    
    
    

    // Function called by consumer thread
    public void consume() throws InterruptedException{
        while (true) {
        	System.out.print("");
        	synchronized (this)
            {
                // consumer thread waits while list
                // is empty
                while (list.isEmpty()) {
                	//Thread.currentThread().stop();
                    wait();

                } 
                int time = (int) (Math.random() * 5) + 1;

                // to retrieve the first job in the list
                int val = list.removeFirst();

                System.out.println("Consume-" + val);
                addText(HAMBU, "Pedido "+val+ " servido en "+time);
                
               // Client.getItems().add("Cliente "+val+ " pide");

                // Wake up producer thread
                notifyAll();

                // and sleep
                Thread.sleep(time*1000);
            }
        }
    }
    
    private void addText(ListView<String> lv,String st) {
    	Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			lv.getItems().add(st);
    		}
    		
    	});
    }
}