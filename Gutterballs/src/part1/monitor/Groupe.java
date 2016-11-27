package part1.monitor;

import java.util.LinkedList;
import java.util.List;

import part1.ChaussureBowling;
import part1.thread.Client;

public class Groupe {

	public static final int nbMaxClient = 3;

	private int id;
	private List<Client> listeClient;
	private SalleDanse salleDanse;
	
	private boolean pisteDeJeuAttribuer;

	public Groupe(int id, SalleDanse salleDanse) {
		this.id = id;
		this.salleDanse = salleDanse;
		listeClient = new LinkedList<>();
		pisteDeJeuAttribuer = false;
	}

	public synchronized void addClient(Client client) {
		listeClient.add(client);
	}

	/**
	 * Pas besoin de synchronized car la méthode n'est appelé qu'une seule
	 * fois, lors de la création des objets dans le main
	 */
	public void setPisteJeu(PisteJeu pisteJeu) {
		pisteDeJeuAttribuer = true;
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

	public synchronized String toString() {
		return "grp [id=" + id + "]";

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
		return pisteDeJeuAttribuer;
	}

	public synchronized void prevenirPartieFinit() {
		listeClient.get(0).prevenirBowlingPartieFinit();
	}

	/**
	 * 
	 * 
	 * */
	public synchronized void waitGroupeSalleDanse() {
		// if car le dernier client qui arrive va forc�ment passer dans le else
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

	// utile ???
	@Override
	public synchronized boolean equals(Object obj) {
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
