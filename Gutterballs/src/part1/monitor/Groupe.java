package part1.monitor;

import java.util.LinkedList;
import java.util.List;

import part1.ChaussureBowling;
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
	 * Pas besoin de synchronized car la méthode n'est appelé qu'une seule
	 * fois, lors de la création des objets
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

	public synchronized boolean isFullShoesBowling() {
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


	public PisteJeu getPisteJeu() {
		return pisteJeu;
	}

	public synchronized boolean isInSalle() {
		for (Client client : listeClient) {
			if (!(client.isInSalle())) {
				return false;
			}
		}

		return true;
	}

	public synchronized boolean isInPiste() {
		for (Client client : listeClient) {
			if (!(client.isInPiste())) {
				return false;
			}
		}
		return true;
	}

	public synchronized boolean gotPisteJeu() {
		return pisteJeu != null;
	}

	public synchronized void prevenirPartieFinit() {
		listeClient.get(0).prevenirBowlingPartieFinit();
	}

	/**
	 * 
	 * 
	 * */
	public synchronized void waitGroupeSalleDanse() {
		if (!isInSalle()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			salleDanse.addGroupe(this);
			System.out.println(this + " est dans la salle de danse.");
			notifyAll();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Groupe other = (Groupe) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public synchronized void waitGroupeFull() {
		if (!isFull()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			notifyAll();
		}
	}

	public synchronized void waitAllHaveShoe() {
		if (isFullShoesBowling()) {
			System.out.println(this + " est chaussé.");
			notifyAll();
		} else {

			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
