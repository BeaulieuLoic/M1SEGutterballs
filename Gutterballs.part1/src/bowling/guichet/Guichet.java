package bowling.guichet;

import bowling.SalleDanse;
import client.Client;
import client.Groupe;

public class Guichet {

	private Groupe groupe;
	private SalleDanse salleDanse;
	private static int nbGrp = 0;

	public Guichet(SalleDanse salleDanse) {
		this.salleDanse = salleDanse;
		groupe = new Groupe(nbGrp, salleDanse);
	}

	/**
	 * Ajoute le client dans le groupe est cours de création
	 * */
	public synchronized void addToGroup(Client client) {
		groupe.addClient(client);
		client.setGroupe(groupe);
		try {
			Thread.sleep(Main.Main.dureeCreationGroupe);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (groupe.isFull()) {
			System.out.println(groupe + " à finit d'être créé.");
			nbGrp++;
			groupe = new Groupe(nbGrp, salleDanse);
		}
	}

	
	public synchronized void fairePayerClient(Client cl) {
		try {
			Thread.sleep(Main.Main.dureePayement);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
