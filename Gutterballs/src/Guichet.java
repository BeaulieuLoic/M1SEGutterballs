public class Guichet {

	private Groupe groupe;
	private SalleDanse salleDanse;
	private StockChaussure stockChaussure;

	public Guichet(SalleDanse salleDanse, StockChaussure stockChaussure) {
		this.salleDanse = salleDanse;
		this.stockChaussure = stockChaussure;

		groupe = new Groupe(salleDanse, stockChaussure);
	}

	public void addToGroup(Client client) {
		if (!groupe.isFull()) {
			groupe.addClient(client);
		} else {
			groupe = new Groupe(salleDanse, stockChaussure);
			groupe.addClient(client);
		}
	}
	

}
