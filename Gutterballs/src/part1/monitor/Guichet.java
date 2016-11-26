package part1.monitor;

import part1.thread.Client;

public class Guichet {

	private Groupe groupe;
	private SalleDanse salleDanse;
	private StockChaussure stockChaussure;
	private static int nbGrp = 0;

	public Guichet(SalleDanse salleDanse, StockChaussure stockChaussure) {
		this.salleDanse = salleDanse;
		this.stockChaussure = stockChaussure;
		groupe = new Groupe(nbGrp, salleDanse, stockChaussure);
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
			groupe = new Groupe(nbGrp, salleDanse, stockChaussure);
		}
	}

	public synchronized void fairePayerClient(Client cl) {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
