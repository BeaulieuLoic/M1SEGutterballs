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
	public synchronized String toString() {
		return "PisteJeu [id=" + id + ", nombre de partie jou�es =" + nbPartieJouer + "]";
	}

	public void lancerPartie() {
		try {
			Thread.sleep(Main.Main.dureePartie);
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

	/**
	 * pas de synchro car utilis� uniquement lors de l'affichage des stats
	 * */
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

		// tout le groupe est arriv�, lancement de la partie, termine la partie,
		// pr�vient le bowling et reveil tout les client
		// le dernier client arriv� lance la partie
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
			System.out.println(grp + " partie finie. " + this);
			grp.setAllIsReady(false);
			notifyAll();
		}
	}
}
