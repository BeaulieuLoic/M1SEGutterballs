package monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import thread.Client;
import thread.Guichetier;

public class Guichet {

	private Queue<Client> listClientAct;
	public Guichet() {

		listClientAct = new LinkedList<Client>();
	}	

	public synchronized void fairePayerClient(Client cl) {
		listClientAct.add(cl);
		notifyAll();
		while(!cl.asPayed()){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void wakeUpClient(){
		notifyAll();
	}
	
	public synchronized void addToGroup(Client cl){
		listClientAct.add(cl);
		notifyAll();
		while(!cl.gotGroupe()){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public synchronized Client getClient(){
		while(listClientAct.size() == 0){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		
		return listClientAct.poll();
	}
	

}
