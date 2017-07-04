package ejb.entites;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Offre implements Serializable, Comparable {

	private static final long serialVersionUID = 1L;

	public int codeOffre;
	public Inscription inscription;
	public double prixOffre;
	public Timestamp dateHeureJour;
	
	public double getPrixOffre() {
		return prixOffre;
	}

	public void setPrixOffre(double prixOffre) {
		this.prixOffre = prixOffre;
	}

	public Offre(){}

	@Id @GeneratedValue
	public int getCodeOffre() {return codeOffre;}
	public void setCodeOffre(int codeOffre) {this.codeOffre = codeOffre;}
		
	@ManyToOne
	public Inscription getInscription() {return inscription;}
	public void setInscription(Inscription inscription) {this.inscription = inscription;}

	/*Pour un tri dans l'ordre decroissant */
	@Override
	public int compareTo(Object arg0) {
		return (-1)*((Double )this.getPrixOffre()).compareTo(((Offre) arg0).getPrixOffre());
	}
	
	public Timestamp getDateHeureJour() {return dateHeureJour;}
	public void setDateHeureJour(Timestamp dateHeureJour) {this.dateHeureJour = dateHeureJour;}
	
}
