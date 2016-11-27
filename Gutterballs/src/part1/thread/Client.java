package part1.thread;

import part1.Chaussure;
import part1.ChaussureVille;
import part1.monitor.Groupe;
import part1.monitor.Guichet;
import part1.monitor.PisteJeu;
import part1.monitor.SalleDanse;
import part1.monitor.StockChaussure;
import part1.monitorAndThread.Bowling;

public class Client extends Thread {

	private int id;
	private Chaussure chaussure;
	private Groupe groupe;
	private Guichet guichet;

	private boolean estDansSalleDanse;
	private boolean estDansPisteJeu;

	private StockChaussure stockChaussure;
	private PisteJeu pisteJeu;
	private SalleDanse salleDanse;
	private Bowling bowling;

	public Client(int id, Guichet guichet, SalleDanse sd, Bowling bl,
			StockChaussure stock) {
		this.id = id;
		this.chaussure = new ChaussureVille();
		this.guichet = guichet;

		salleDanse = sd;

		estDansSalleDanse = false;
		estDansPisteJeu = false;

		stockChaussure = stock;
		bowling = bl;
	}

	public Chaussure getChaussure() {
		return chaussure;
	}

	public void setChaussure(Chaussure chaussure) {
		this.chaussure = chaussure;
	}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	public void setPisteJeu(PisteJeu pj) {
		pisteJeu = pj;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + "]";
	}

	// pour salle danse
	public void goToSalle() {
		estDansSalleDanse = true;
	}

	public void outSalle() {
		estDansSalleDanse = false;
	}

	public boolean isInSalle() {
		return estDansSalleDanse;
	}

	// pour piste jeu
	public void goToPisteJeu() {
		estDansPisteJeu = true;
	}

	public boolean isInPiste() {
		return estDansPisteJeu;
	}

	// ne pas appeler car pose probl�me (voir waitgroueandPLay)
	public void outPiste() {
		estDansPisteJeu = false;
	}

	public void prevenirBowlingPartieFinit() {
		bowling.nouvellePisteDispo();
	}

	public void payer() {
		guichet.fairePayerClient(this);
	}

	public void run() {
		boolean afficherClient = false;

		if (afficherClient) {
			System.out.println(this + "-> Guichet");
		}

		guichet.addToGroup(this);// go to guichet et attendre

		groupe.waitGroupeFull();

		if (afficherClient) {
			System.out
					.println(this
							+ " "
							+ groupe
							+ " à finit d'attendre dans Guichet. -> go StockChaussure ");
		}

		// go to salle des chaussures
		stockChaussure.changeVtoB(this); // se chausse

		if (afficherClient) {
			System.out
					.println(this
							+ " "
							+ groupe
							+ " à finit d'attendre dans stockChaussure. -> go SalleDeDanse");
		}

		// go to salle de danse et attend que tout les membres du groupe y soit
		goToSalle();
		groupe.waitGroupeSalleDanse();

		if (afficherClient) {
			System.out
					.println(this
							+ " "
							+ groupe
							+ " à finit d'attendre son groupe dans salleDanse. -> attend piste de jeu");
		}

		// attends d'etre notifié par soit un membre de son groupe soit par le
		// bowling qu'une place est libre
		salleDanse.waitPisteDispo(groupe);

		if (afficherClient) {
			System.out.println(this + " " + groupe
					+ "  piste de jeu trouver. -> go to pisteDeJeux");
		}

		// go to piste de jeu
		goToPisteJeu();

		// attendre que son groupe soit au complet sur la piste puis joue
		pisteJeu.waitGroupeAndPlay(groupe);

		if (afficherClient) {
			System.out
					.println(this
							+ " "
							+ groupe
							+ " à finit d'attendre son groupe dans pisteJeux. -> joue une partie");
		}

		// Jouer :D (le dernier client arriv� dans pisteJeu lance la partie)
		// Soit prevenir le bowling que la partie est terminée soit il se barre
		// go to guichet
		// Payer D:
		payer();

		if (afficherClient) {
			System.out.println(this + " a finit de payer -> go StockChaussure");
		}

		// go to salle des chaussures
		stockChaussure.changeBtoV(this);

		// go exit
	}

	public boolean equals(Client cl) {
		return id == cl.id;
	}

}
