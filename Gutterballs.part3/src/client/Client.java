package client;


import java.util.List;

import bowling.Bowling;
import bowling.PisteJeu;
import bowling.SalleDanse;
import bowling.guichet.Guichet;
import bowling.stockChaussure.GuichetStockChaussure;
import bowling.stockChaussure.PrioriteChaussureMonitor;
import bowling.stockChaussure.StockChaussure;
import Main.Main;


public class Client extends Thread {

	private int id;
	private Chaussure chaussure;
	private Groupe groupe;
	private Guichet guichet;

	private boolean isReady;
	private boolean asPayed;

	private GuichetStockChaussure guichetChaussure;
	private PisteJeu pisteJeu;
	private SalleDanse salleDanse;
	private Bowling bowling;

	public Client(int id, Guichet guichet, SalleDanse sd, Bowling bl, GuichetStockChaussure stock) {
		this.id = id;
		this.chaussure = new Chaussure(id);
		this.guichet = guichet;

		salleDanse = sd;

		isReady = false;
		asPayed = false;

		guichetChaussure = stock;
		bowling = bl;
	}

	public boolean gotGroupe() {
		return groupe != null;
	}

	public boolean asPayed() {
		return asPayed;
	}

	public void setPayed(boolean b){
		asPayed=b;
	}
	
	public boolean isReady() {
		return isReady;
	}

	public void setIsReady(boolean b) {
		isReady = b;
	}

	public Chaussure getChaussure() {
		return chaussure;
	}

	public void setChaussure(Chaussure chaussure) {
		this.chaussure = chaussure;
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

	public void prevenirBowlingPartieFinit() {
		bowling.nouvellePisteDispo();
	}

	public void payer() {
		guichet.fairePayerClient(this);
	}

	public int getPriorite(){
		if(chaussure.isBowling()){
			return PrioriteChaussureMonitor.prioMax;
		}else if(groupe.nbClientGetChaussureBowling() >= 1){
			return PrioriteChaussureMonitor.prioInt;
		}else{
			return PrioriteChaussureMonitor.prioMin;
		}
		
	}
	
	
	public void run() {
		boolean afficherClient = Main.afficheMsgClient;

		if (afficherClient) {

			System.out.println(this + "-> Guichet");
		}

		guichet.addToGroup(this);// go to guichet et attendre

		groupe.waitGroupeFull(this);

		if (afficherClient) {
			System.out.println(this + " " + groupe + " à finit d'attendre dans Guichet. -> go StockChaussure ");
		}

		// go to salle des chaussures
		guichetChaussure.switchChaussure(this); // se chausse

		groupe.waitAllHaveShoe(this);
		if (afficherClient) {
			System.out.println(this + " " + groupe + " à finit d'attendre dans stockChaussure. -> go SalleDeDanse");
		}

		// go to salle de danse et attend que tout les membres du groupe y soit
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		groupe.waitGroupeSalleDanse(this);

		if (afficherClient) {
			System.out.println(
					this + " " + groupe + " à finit d'attendre son groupe dans salleDanse. -> attend piste de jeu");
		}

		// attends d'etre notifié par soit un membre de son groupe soit par le
		// bowling qu'une place est libre
		salleDanse.waitPisteDispo(groupe);

		if (afficherClient) {
			System.out.println(this + " " + groupe + "  piste de jeu trouver. -> go to pisteDeJeux");
		}

		// attendre que son groupe soit au complet sur la piste puis joue
		pisteJeu.waitGroupeAndPlay(groupe, this);

		if (afficherClient) {
			System.out.println(
					this + " " + groupe + " à finit d'attendre son groupe dans pisteJeux. -> joue une partie");
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
		
		guichetChaussure.switchChaussure(this);
		System.out.println("zzzzz");

		
		if (chaussure.getId() != id) {
			System.out.println("!!!!! Erreur chaussure du client != this.id !!!!!");
		}

		if (afficherClient) {
			System.out.println(this + " exit");
		}
		// go exit
	}

	public boolean equals(Client cl) {
		return id == cl.id;
	}

}
