import java.util.LinkedList;
import java.util.List;

public class Groupe {

	private static final int nbMaxClient = 5;

	private List<Client> listeClient;
	private PisteJeu pisteJeu;
	private SalleDanse salleDanse;
	private StockChaussure stockChaussure;

	public Groupe(PisteJeu pisteJeu, SalleDanse salleDanse,
			StockChaussure stockChaussure) {
		this.pisteJeu = pisteJeu;
		this.salleDanse = salleDanse;
		this.stockChaussure = stockChaussure;
		listeClient = new LinkedList<>();
	}

	public void addClient(Client client) {
			listeClient.add(client);
	}

	public boolean isFull(){
		return (listeClient.size() >= nbMaxClient);
	}
	
}
