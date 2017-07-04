package ejb.entites;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
public abstract class Acheteur implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String adresseEmail;
	public Set<Inscription> inscription;
	public Acheteur() {}
	
	@Id
	public String getAdresseEmail() { return adresseEmail; }
	public void setAdresseEmail(String adresseEmail) {
		this.adresseEmail = adresseEmail;
	}

	@OneToMany(mappedBy="acheteur")
	public Set<Inscription> getInscription() {return inscription;}
	public void setInscription(Set<Inscription> inscription) {this.inscription = inscription;}
		
	public double surenchere(double prixActuel){
		return(this.surenchere(prixActuel));
	}

}
