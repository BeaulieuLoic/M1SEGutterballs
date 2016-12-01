package bowling.guichet;

import bowling.SalleDanse;
import client.Client;
import client.Groupe;

public class CreationGroupe {
	private static int nbGrp = 0;
	private Groupe groupeEncoursCreation;
	private SalleDanse salleDanse;
	
	public CreationGroupe(SalleDanse s){
		groupeEncoursCreation = new Groupe(nbGrp, s);
		salleDanse = s;
	}
	
	/**
	 * Ajoute un client dans le groupe qui est en cours de création
	 * */
	public synchronized void addToGroup(Client client) {
		groupeEncoursCreation.addClient(client);
		client.setGroupe(groupeEncoursCreation);

		
		if (groupeEncoursCreation.isFull()) {
			System.out.println(groupeEncoursCreation + " à  finit d'être créé.");
			nbGrp++;
			groupeEncoursCreation = new Groupe(nbGrp, salleDanse);
		}
	}
	
}
