package thread;

import model.CreationGroupe;
import monitor.Guichet;

public class Guichetier extends Thread {
	
	private CreationGroupe cGroupe;
	private Guichet guichet;
	
	public Guichetier(CreationGroupe cg, Guichet g){
		cGroupe = cg;
		guichet = g;
	}
	
	
	public void run(){
		while(true){
			Client cl = guichet.getClient();
			if(cl.gotGroupe()){
				cl.setPayed(true);
				//fait payer le client
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				cGroupe.addToGroup(cl);
			}
			guichet.wakeUpClient();
		}
	}
}
