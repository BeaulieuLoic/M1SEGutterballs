package part1;

import java.util.LinkedList;
import java.util.List;

import part1.monitor.SalleDanse;
import part1.monitor.StockChaussure;
import part1.thread.Client;

public class Groupe {

	private static final int nbMaxClient = 3;

	private int id;
	private List<Client> listeClient;
	private PisteJeu pisteJeu;
	private SalleDanse salleDanse;
	private StockChaussure stockChaussure;

	public Groupe(int id, SalleDanse salleDanse, StockChaussure stockChaussure) {
		this.id = id;
		this.salleDanse = salleDanse;
		this.stockChaussure = stockChaussure;
		listeClient = new LinkedList<>();
	}

	public synchronized void addClient(Client client) {
		listeClient.add(client);
	}

	/**
	 * Pas besoin de synchronized car la méthode n'est appelé qu'une seule fois,
	 * lors de la création des objets
	 */
	public void setPisteJeu(PisteJeu pisteJeu) {
		this.pisteJeu = pisteJeu;
		for (Client client : listeClient) {
			client.setPisteJeu(pisteJeu);
		}
	}

	public synchronized boolean isFull() {
		return (listeClient.size() >= nbMaxClient);
	}

	public synchronized boolean isChausseBowling(){
		for (Client client : listeClient) {
			if (!(client.getChaussure() instanceof ChaussureBowling)) {
				return false;
			}
		}
		return true;
	}
	
	public String toString() {
		return "grp [id=" + id + "]";

	}

	public StockChaussure getStockChaussure() {
		return stockChaussure;
	}
	
	public SalleDanse getSalleDanse(){
		return salleDanse;
	}
	
	public PisteJeu getPisteJeu(){
		return pisteJeu;
	}

	public synchronized boolean isInSalle(){
		for (Client client : listeClient) {
			if (!(client.isInSalle())) {
				return false;
			}
		}
		
		return true;
	}
	
	public synchronized boolean isInPiste(){
		for (Client client : listeClient) {
			if (!(client.isInPiste())) {
				return false;
			}
		}
		return true;
	}
	
	public synchronized boolean gotPisteJeu(){
		return pisteJeu != null;
	}
	
	public synchronized void prevenirPartieFinit(){
		listeClient.get(0).prevenirBowlingPartieFinit();
	}
	
	
	
}
