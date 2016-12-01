package Main;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import client.Client;
import client.Groupe;
import bowling.Bowling;
import bowling.PisteJeu;
import bowling.SalleDanse;
import bowling.guichet.CreationGroupe;
import bowling.guichet.Guichet;
import bowling.guichet.Guichetier;
import bowling.stockChaussure.StockChaussure;




public class Main {
	public static final int nbGuichetier = 3;
	public static final int nbPiste = 2;
	public static final int nbGroupe = 5;
	public static final int nbClientGrp =5;
	public static final boolean afficheMsgClient = true;
	
	public static final int dureePartie = 500;//ms
	public static final int dureePayement = 50;
	public static final int dureeChausse = 50;
	public static final int dureeCreationGroupe = 500;
	
	public static final int dureeGoToSalleDanse = 100;
	public static final int dureeGoToPiste = 100;	
	
	

	public static void main(String[] args) {

		try {
			File file = new File("../fichierTraces/part2/nbGrp_"+nbGroupe+" nbClientGrp_"+nbClientGrp+" nbPiste_"+nbPiste+" .txt");
			PrintStream printStream = new PrintStream(file);
			System.setOut(printStream);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		System.out.println("Nombre de groupe : "+nbGroupe);
		System.out.println("Nombre de client par groupe : "+nbClientGrp);
		System.out.println("Nombre de piste : "+nbPiste);
		System.out.println("---------------------------");
		
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
		
		
		for (int i = 0; i < nbGroupe*nbClientGrp; i++) {
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
		
		System.out.println("---------------------------");
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
