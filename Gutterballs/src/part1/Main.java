package part1;

import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {

		LinkedList<Client> lc = new LinkedList<>();
		SalleDanse salleDanse = new SalleDanse();
		StockChaussure stockChaussure = new StockChaussure();
		Guichet guichet = new Guichet(salleDanse, stockChaussure);

		for (int i = 0; i < 5; i++) {
			lc.add(new Client(i, guichet));
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

	}

}
