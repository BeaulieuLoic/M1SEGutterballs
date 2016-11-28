package monitor;

import thread.Client;

public class Guichet extends Thread {

	private CreationGroupe cGroupe;
	private Client clientAct;
	
	private static int nbGrp = 0;

	public Guichet(CreationGroupe cGrp) {
		cGroupe = cGrp;
	}



	public synchronized void fairePayerClient(Client cl) {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public synchronized void waitClient(){
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			waitClient();
		}		
	}

}
