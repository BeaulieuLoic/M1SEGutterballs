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

	/**
	 * Demande si une piste est disponible, s'il y en à une la réserve et renvoi true. sinon false
	 * */
	public synchronized boolean reserverPiste(Groupe grp) {
		// ajoute le groupe seulement s'il est arrivé en 1er parmis les groupe dans la salle
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

	/**
	 * Attend qu'une piste ce libère tant que le groupe n'a pas de piste de jeu
	 * */
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

	/**
	 * réveil tout les clients pour les prévenir qu'une piste est disponible
	 * */
	public synchronized void nouvellePisteDispo() {
		notifyAll();
	}

}
