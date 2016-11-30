package Main;



import java.util.LinkedList;
import java.util.List;

import bowling.Bowling;
import bowling.PisteJeu;
import bowling.SalleDanse;
import bowling.guichet.CreationGroupe;
import bowling.guichet.Guichet;
import bowling.guichet.Guichetier;
import bowling.stockChaussure.EmployerChaussure;
import bowling.stockChaussure.GuichetStockChaussure;
import bowling.stockChaussure.StockChaussure;
import client.Client;
import client.Groupe;



public class Main {
	public static final int nbGuichetier = 3;
	public static final int nbPiste = 2;
	public static final int nbGroupe = 10;
	public static final int nbClientGrp = 20;
	public static final boolean afficheMsgClient = false;
	
	public static final int dureePartie = 5000;//ms
	public static final int dureePayement = 50;
	public static final int dureeChausse = 20;
	public static final int dureeCreationGroupe = 10;
	
	public static final int dureeGoToSalleDanse = 100;
	public static final int dureeGoToPiste = 100;	

	public static void main(String[] args) {

		
		List<Client> lc = new LinkedList<>();
		List<Guichetier> lGuichetier = new LinkedList<>();
		
		SalleDanse salleDanse = new SalleDanse();
		CreationGroupe grpCreation = new CreationGroupe(salleDanse);
		Guichet guichet = new Guichet();
		
		StockChaussure stockChaussure = new StockChaussure();
		EmployerChaussure empChaussure = new EmployerChaussure(stockChaussure);
		GuichetStockChaussure guichetStockChaussure = new GuichetStockChaussure(empChaussure);
		empChaussure.setGuichet(guichetStockChaussure);
		
		Bowling bowling = new Bowling(salleDanse);
		salleDanse.setBowling(bowling);

		
		
		
		for (int i = 0; i < nbGuichetier; i++) {
			lGuichetier.add(new Guichetier(grpCreation, guichet));
		}
		
		for (int i = 0; i < nbPiste; i++) {
			bowling.addPiste(new PisteJeu(i));
		}
		
		
		for (int i = 0; i < nbGroupe*nbClientGrp; i++) {
			lc.add(new Client(i, guichet, salleDanse,bowling, guichetStockChaussure));
		}		

		
		bowling.setDaemon(true);//quand plus de client le bowling ferme
		bowling.start();
		
		empChaussure.setDaemon(true);
		empChaussure.start();
		
		for (Guichetier guichetier : lGuichetier) {
			guichetier.setDaemon(true);
			guichetier.start();
		}
		
		for (Client client : lc) {
			client.start();			
		}
		
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
