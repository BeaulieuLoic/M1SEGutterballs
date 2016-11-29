package bowling;

import client.Client;
import client.Groupe;

public class PisteJeu {
	private int id;
	private Groupe groupe;
	private int nbPartieJouer;

	public PisteJeu(int id) {
		groupe = null;
		nbPartieJouer = 0;
		this.id = id;
	}

	@Override
	public String toString() {
		return "PisteJeu [id=" + id + ", nombre de partie jouée =" + nbPartieJouer + "]";
	}

	public void lancerPartie() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean estOccupe() {
		return groupe != null;
	}

	public void setGroupe(Groupe grp) {
		groupe = grp;
	}

	public int getNbPartieJouer() {
		return nbPartieJouer;
	}

	public void partieTermine() {
		Groupe tmp = groupe;
		groupe = null;
		tmp.prevenirPartieFinit();
		nbPartieJouer++;
	}

	public synchronized void waitGroupeAndPlay(Groupe grp, Client cl) {
		cl.setIsReady(true);

		// tout le groupe est arrivé, lancement de la partie, termine la partie,
		// prévient le bowling et reveil tout les client
		// seul 1 thread rentre dedans car partieTermine() va metre à null le
		// groupe actuel de la piste
		if (!grp.isAllReady()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println(grp + " est dans pisteDeJeu. debut de la partie ...");
			lancerPartie();
			partieTermine();
			System.out.println(grp + " partie finit. " + this);
			grp.setAllIsReady(false);
			notifyAll();
		}
	}
}
