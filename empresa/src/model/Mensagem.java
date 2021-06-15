package model;

public class Mensagem {
	
	private String men, sender;

	public String getMen() {
		return men;
	}

	public void setMen(String men) {
		this.men = men;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Mensagem(String men, String sender) {
		this.men = men;
		this.sender = sender;
	}
	
	
}
