package part1;

import java.util.LinkedList;

import part1.monitor.Groupe;
import part1.monitor.Guichet;
import part1.monitor.PisteJeu;
import part1.monitor.SalleDanse;
import part1.monitor.StockChaussure;
import part1.monitorAndThread.Bowling;
import part1.thread.Client;

public class Main {

	public static void main(String[] args) {

		LinkedList<Client> lc = new LinkedList<>();
		SalleDanse salleDanse = new SalleDanse();
		StockChaussure stockChaussure = new StockChaussure();
		Guichet guichet = new Guichet(salleDanse);
		
		Bowling bowling = new Bowling(salleDanse);
		salleDanse.setBowling(bowling);

		
		for (int i = 0; i < 5; i++) {
			bowling.addPiste(new PisteJeu(i));
		}
		
		
		for (int i = 0; i < 30*Groupe.nbMaxClient; i++) {
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

}
