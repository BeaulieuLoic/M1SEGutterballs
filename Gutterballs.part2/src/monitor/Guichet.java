package monitor;

import thread.Client;

public class Guichet {

	private Groupe groupe;
	private SalleDanse salleDanse;
	private static int nbGrp = 0;

	public Guichet(SalleDanse salleDanse) {
		this.salleDanse = salleDanse;
		groupe = new Groupe(nbGrp, salleDanse);
	}

	public synchronized void addToGroup(Client client) {

		groupe.addClient(client);
		client.setGroupe(groupe);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (groupe.isFull()) {
			System.out.println(groupe + " Ã  finit d'être créé.");
			nbGrp++;
			groupe = new Groupe(nbGrp, salleDanse);
		}
	}

	public synchronized void fairePayerClient(Client cl) {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
