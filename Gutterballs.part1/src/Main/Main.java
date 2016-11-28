package Main;


import java.util.LinkedList;

import model.StockChaussure;
import monitor.*;
import monitorAndThread.*;
import thread.Client;

public class Main {

	public static void main(String[] args) {

		LinkedList<Client> lc = new LinkedList<>();
		SalleDanse salleDanse = new SalleDanse();
		StockChaussure stockChaussure = new StockChaussure();
		Guichet guichet = new Guichet(salleDanse);
		
		Bowling bowling = new Bowling(salleDanse);
		salleDanse.setBowling(bowling);

		
		for (int i = 0; i < 1; i++) {
			bowling.addPiste(new PisteJeu(i));
		}
		
		
		for (int i = 0; i < 10*Groupe.nbMaxClient; i++) {
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
