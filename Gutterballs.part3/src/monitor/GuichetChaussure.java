package monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import thread.Client;
import thread.Guichetier;

public class GuichetChaussure {

	private Queue<Client> listClient;
	public GuichetChaussure() {

		listClient = new LinkedList<Client>();
	}	
	
	public synchronized void wakeUpClient(){
		notifyAll();
	}
	
	public synchronized Client getClient(){
		while(listClient.size() == 0){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		
		return listClient.poll();
	}
	

}
