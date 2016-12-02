package client;

import java.util.LinkedList;
import java.util.List;

import bowling.PisteJeu;
import bowling.SalleDanse;
import Main.Main;

public class Groupe {

	public static final int nbMaxClient = Main.nbClientGrp;

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
	 * fois, lors de la crÃ©ation des objets dans le main
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

	public synchronized boolean isAllReady() {
		for (Client client : listeClient) {
			if (!client.isReady()) {
				return false;
			}
		}
		return true;
	}

	public synchronized void setAllIsReady(boolean b) {
		for (Client client : listeClient) {
			client.setIsReady(b);
		}
	}

	public synchronized boolean isFullShoesBowling() {
		for (Client client : listeClient) {
			if (!(client.getChaussure().isBowling())) {
				return false;
			}
		}
		return true;
	}

	public synchronized String toString() {
		return "grp [id=" + id + "]";

	}

	public synchronized boolean gotPisteJeu() {
		return pisteDeJeuAttribuer;
	}

	public synchronized void prevenirPartieFinit() {
		listeClient.get(0).prevenirBowlingPartieFinit();
	}

	public synchronized void waitGroupeSalleDanse(Client cl) {
		cl.setIsReady(true);
		// if car le dernier client qui arrive va forcément passer dans le else
		if (!isAllReady()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			salleDanse.addGroupe(this);
			System.out.println(this + " est dans la salle de danse.");
			setAllIsReady(false);
			notifyAll();
		}
	}

	public synchronized void waitGroupeFull(Client cl) {
		cl.setIsReady(true);
		if (!isFull() || !isAllReady()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			setAllIsReady(false);
			notifyAll();
		}
	}

	public synchronized void waitAllHaveShoe(Client cl) {
		cl.setIsReady(true);

		if (!isAllReady()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			System.out.println(this + " est chaussé.");
			setAllIsReady(false);
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

}
