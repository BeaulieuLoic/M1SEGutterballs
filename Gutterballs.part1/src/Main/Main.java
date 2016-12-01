package Main;


import java.util.LinkedList;

import client.Client;
import client.Groupe;
import bowling.Bowling;
import bowling.PisteJeu;
import bowling.SalleDanse;
import bowling.guichet.Guichet;
import bowling.stockChaussure.StockChaussure;

public class Main {
	public static final int nbPiste = 3;
	public static final int nbGroupe = 10;
	public static final int nbClientGrp =5;
	public static final boolean afficheMsgClient = false;
	
	public static final int dureePartie = 500;//ms
	public static final int dureePayement = 50;
	public static final int dureeChausse = 50;
	public static final int dureeCreationGroupe = 10;
	
	public static final int dureeGoToSalleDanse = 100;
	public static final int dureeGoToPiste = 100;	
	
	
	public static void main(String[] args) {

		LinkedList<Client> lc = new LinkedList<>();
		SalleDanse salleDanse = new SalleDanse();
		StockChaussure stockChaussure = new StockChaussure();
		Guichet guichet = new Guichet(salleDanse);
		
		Bowling bowling = new Bowling(salleDanse);
		salleDanse.setBowling(bowling);

		
		for (int i = 0; i < nbPiste; i++) {
			bowling.addPiste(new PisteJeu(i));
		}
		
		
		for (int i = 0; i < nbGroupe*nbClientGrp; i++) {
			lc.add(new Client(i, guichet, salleDanse,bowling, stockChaussure));
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
