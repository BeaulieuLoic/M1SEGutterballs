package part1;

import java.util.LinkedList;

import part1.monitor.Guichet;
import part1.monitor.SalleDanse;
import part1.monitor.StockChaussure;
import part1.thread.Bowling;
import part1.thread.Client;

public class Main {

	public static void main(String[] args) {

		LinkedList<Client> lc = new LinkedList<>();
		SalleDanse salleDanse = new SalleDanse();
		StockChaussure stockChaussure = new StockChaussure();
		Guichet guichet = new Guichet(salleDanse, stockChaussure);
		
		Bowling bowling = new Bowling(salleDanse);
		salleDanse.setBowling(bowling);

		
		for (int i = 0; i < 2; i++) {
			bowling.addPiste(new PisteJeu());
		}
		
		
		for (int i = 0; i < 6; i++) {
			lc.add(new Client(i, guichet, salleDanse,bowling));
		}
		
		
		
		
		for (Client client : lc) {
			client.start();
		}
		//bowling.start();
		
		
		
		
		for (Client client : lc) {
			try {
				client.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/*try {
			bowling.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

}
