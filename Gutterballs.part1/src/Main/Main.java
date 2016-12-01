package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;

import client.Client;
import client.Groupe;
import bowling.Bowling;
import bowling.PisteJeu;
import bowling.SalleDanse;
import bowling.guichet.Guichet;
import bowling.stockChaussure.StockChaussure;

public class Main {
	public static final int nbPiste = 2;
	public static final int nbGroupe = 3;
	public static final int nbClientGrp = 5;
	public static final boolean afficheMsgClient = true;

	public static final int dureePartie = 500;// ms
	public static final int dureePayement = 50;
	public static final int dureeChausse = 50;
	public static final int dureeCreationGroupe = 10;

	public static final int dureeGoToSalleDanse = 100;
	public static final int dureeGoToPiste = 100;

	public static void main(String[] args) {
		
		try {
			File file = new File("../fichierTraces/part1/nbGrp_"+nbGroupe+" nbClientGrp_"+nbClientGrp+" nbPiste_"+nbPiste+" .txt");
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

		LinkedList<Client> lc = new LinkedList<>();
		SalleDanse salleDanse = new SalleDanse();
		StockChaussure stockChaussure = new StockChaussure();
		Guichet guichet = new Guichet(salleDanse);

		Bowling bowling = new Bowling(salleDanse);
		salleDanse.setBowling(bowling);

		for (int i = 0; i < nbPiste; i++) {
			bowling.addPiste(new PisteJeu(i));
		}

		for (int i = 0; i < nbGroupe * nbClientGrp; i++) {
			lc.add(new Client(i, guichet, salleDanse, bowling, stockChaussure));
		}

		for (Client client : lc) {
			client.start();
		}

		bowling.setDaemon(true);// quand plus de client le bowling ferme
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

	public static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
