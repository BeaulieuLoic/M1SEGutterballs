public abstract class Chaussure {

	protected Client client;
	protected Boolean estPorte;

	public Chaussure() {
		estPorte = false;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Boolean getEstPorte() {
		return estPorte;
	}

	public void setEstPorte(Boolean estPorte) {
		this.estPorte = estPorte;
	}

}
