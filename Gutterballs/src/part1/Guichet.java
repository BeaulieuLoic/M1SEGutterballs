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
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (groupe.isFull()) {
				System.out.println(groupe + " à finit d'être crée.");
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
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			nbGrp++;
			groupe = new Groupe(nbGrp, salleDanse, stockChaussure);
			groupe.addClient(client);
			client.setGroupe(groupe);
		}

	}

}
