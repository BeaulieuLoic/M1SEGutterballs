package part1;
public abstract class Chaussure {

	protected Client client;
	protected boolean estPorte;

	public Chaussure() {
		estPorte = false;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public boolean getEstPorte() {
		return estPorte;
	}

	public void setEstPorte(boolean estPorte) {
		this.estPorte = estPorte;
	}

}
