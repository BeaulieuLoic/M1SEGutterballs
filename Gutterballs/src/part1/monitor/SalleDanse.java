package part1.monitor;

import java.util.LinkedList;
import java.util.List;

import part1.Groupe;
import part1.PisteJeu;
import part1.thread.Bowling;

public class SalleDanse {
	private List<Groupe> listGroupe;
	private Bowling bowling;

	public SalleDanse() {
		listGroupe = new LinkedList<>();
	}

	public void setBowling(Bowling b) {
		bowling = b;
	}

	public synchronized boolean reserverPiste(Groupe grp) {
		PisteJeu piste = bowling.getPisteLibre();
		if (piste != null) {
			piste.setGroupe(grp);
			grp.setPisteJeu(piste);
			return true;
		} else {
			return false;
		}

	}

	public synchronized void waitPisteDispo(Groupe grp) {
		while (!(grp.gotPisteJeu())) {
			// demandé une piste si son groupe n'à pas déjà trouvé une piste
			if (!grp.gotPisteJeu() && reserverPiste(grp)) {
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

	/**
	 * 
	 * 
	 * */
	public synchronized void waitGroupe(Groupe grp) {
		while (!grp.isInSalle()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!listGroupe.contains(grp)) {
			System.out.println(grp + " est dans la salle de danse.");
			listGroupe.add(grp);
			notifyAll();
		}
	}

	public synchronized void nouvellePisteDispo() {
		notifyAll();
	}

}
