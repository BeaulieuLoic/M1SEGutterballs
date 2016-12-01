package bowling.guichet;

import client.Client;

public class Guichetier extends Thread {
	
	private CreationGroupe cGroupe;
	private Guichet guichet;
	
	public Guichetier(CreationGroupe cg, Guichet g){
		cGroupe = cg;
		guichet = g;
	}
	
	
	public void run(){
		while(true){
			Client cl = guichet.getClient();//wait s'il n'y � pas de client
			
			//demande au client s'il souhaite payer ou rejoindre un groupe
			if(cl.gotGroupe()){
				cl.setPayed(true);
				//fait payer le client
				try {
					Thread.sleep(Main.Main.dureePayement);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				
				try {
					Thread.sleep(Main.Main.dureeCreationGroupe);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				cGroupe.addToGroup(cl);
			}
			guichet.wakeUpClient();// r�veil les clients qui sont attende guichet
		}
	}
}
