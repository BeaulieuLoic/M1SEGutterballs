package bowling.guichet;

import java.util.LinkedList;
import java.util.Queue;

import client.Client;

public class Guichet {

	private Queue<Client> listClientAct;
	public Guichet() {
		listClientAct = new LinkedList<Client>();
	}	

	/**
	 * demmande à un guichetier de le faire payer
	 * */
	public synchronized void fairePayerClient(Client cl) {
		listClientAct.add(cl);
		notifyAll();//réveil les guichetiers
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
	
	/**
	 * demmande au guichetier de le placer dans un groupe
	 * */
	public synchronized void addToGroup(Client cl){
		listClientAct.add(cl);
		notifyAll();//réveil les guichetiers
		while(!cl.gotGroupe()){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * renvoi le client qui est en 1er dans la file d'attente, s'il n'y en à pas, endort le guichetier
	 * */
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
