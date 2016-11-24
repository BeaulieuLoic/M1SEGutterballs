package part1;

public class Guichet {

	private Groupe groupe;
	private SalleDanse salleDanse;
	private StockChaussure stockChaussure;
	private static int nbGrp = 0;

	public Guichet(SalleDanse salleDanse, StockChaussure stockChaussure) {
		this.salleDanse = salleDanse;
		this.stockChaussure = stockChaussure;
		groupe = new Groupe(nbGrp, salleDanse, stockChaussure);
	}

	public synchronized void addToGroup(Client client) {
		if (!groupe.isFull()) {
			groupe.addClient(client);
			client.setGroupe(groupe);
			if (groupe.isFull()) {
				System.out.println(groupe + " est prêt.");
				notifyAll();

			} else {
				while (!groupe.isFull()) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			nbGrp++;
			groupe = new Groupe(nbGrp, salleDanse, stockChaussure);
			groupe.addClient(client);
			client.setGroupe(groupe);
		}

	}

}
