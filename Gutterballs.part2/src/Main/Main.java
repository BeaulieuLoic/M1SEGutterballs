package Main;


import java.util.LinkedList;
import java.util.List;

import model.CreationGroupe;
import model.StockChaussure;
import monitor.*;
import monitorAndThread.*;
import thread.Client;
import thread.Guichetier;



public class Main {
	public static final int nbGuichetier = 3;
	public static final int nbPiste = 3;
	public static final int nbGroupe = 10;
	
	
	

	public static void main(String[] args) {

		
		List<Client> lc = new LinkedList<>();
		List<Guichetier> lGuichetier = new LinkedList<>();
		
		SalleDanse salleDanse = new SalleDanse();
		StockChaussure stockChaussure = new StockChaussure();
		CreationGroupe grpCreation = new CreationGroupe(salleDanse);
		Guichet guichet = new Guichet();
				
		Bowling bowling = new Bowling(salleDanse);
		salleDanse.setBowling(bowling);

		for (int i = 0; i < nbGuichetier; i++) {
			lGuichetier.add(new Guichetier(grpCreation, guichet));
		}
		
		for (int i = 0; i < nbPiste; i++) {
			bowling.addPiste(new PisteJeu(i));
		}
		
		
		for (int i = 0; i < nbGroupe*Groupe.nbMaxClient; i++) {
			lc.add(new Client(i, guichet, salleDanse,bowling, stockChaussure));
		}		

		
		
		for (Guichetier guichetier : lGuichetier) {
			guichetier.setDaemon(true);
			guichetier.start();
		}
		
		for (Client client : lc) {
			client.start();
		}
		
		bowling.setDaemon(true);//quand plus de client le bowling ferme
		bowling.start();
		
		for (Client client : lc) {
			try {
				client.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Stat :");
		System.out.println(bowling.getStat());

	}
	
	public static void sleep(int ms){
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}