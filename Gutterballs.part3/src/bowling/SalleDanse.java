package bowling;

import java.util.LinkedList;
import java.util.List;

import client.Groupe;

public class SalleDanse {
	private List<Groupe> listGroupe;
	private Bowling bowling;

	public SalleDanse() {
		listGroupe = new LinkedList<>();
	}

	public void setBowling(Bowling b) {
		bowling = b;
	}

	// ajoute un groupe seulement s'il n'y est pas déjà
	public synchronized boolean addGroupe(Groupe grp) {
		if (!listGroupe.contains(grp)) {
			listGroupe.add(grp);
			return true;
		} else {
			return false;
		}
	}

	public synchronized boolean reserverPiste(Groupe grp) {
		
		if (grp.equals(listGroupe.get(0))) {
			PisteJeu piste = bowling.getPisteLibre();
			if (piste != null) {
				piste.setGroupe(grp);
				grp.setPisteJeu(piste);
				listGroupe.remove(grp);
				return true;
			}
		}
		return false;
	}

	public synchronized void waitPisteDispo(Groupe grp) {
		while (!(grp.gotPisteJeu())) {
			// demande si une piste est dispo, si oui la réserve directement
			if (reserverPiste(grp)) {
				notifyAll();
			} else {
				try {
					wait();// danse
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public synchronized void nouvellePisteDispo() {
		notifyAll();
	}

}
