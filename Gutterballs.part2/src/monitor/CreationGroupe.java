package monitor;

import thread.Client;

public class CreationGroupe {
	private static int nbGrp = 0;
	private Groupe groupeEncoursCreation;
	private SalleDanse salleDanse;
	
	public CreationGroupe(SalleDanse s){
		groupeEncoursCreation = new Groupe(nbGrp, s);
		salleDanse = s;
	}
	
	public synchronized void addToGroup(Client client) {

		groupeEncoursCreation.addClient(client);
		client.setGroupe(groupeEncoursCreation);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (groupeEncoursCreation.isFull()) {
			System.out.println(groupeEncoursCreation + " Ã  finit d'être créé.");
			nbGrp++;
			groupeEncoursCreation = new Groupe(nbGrp, salleDanse);
		}
	}
	
}
