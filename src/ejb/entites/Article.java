package ejb.entites;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Article implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String code;
	public String nom;
	public double prixInitial;
	public Etat etat;
	public double prixMeilleurOffre;
	public Set<Inscription> inscription;
	
	public Article() {}
	
	@Id
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}

	public String getNom() {return nom;}
	public void setNom(String nom) {this.nom = nom;}

	public double getPrixInitial() {return prixInitial;}
	public void setPrixInitial(double prixInitial) {this.prixInitial = prixInitial;}

	public Etat getEtat() {return etat;}
	public void setEtat(Etat etat) {this.etat = etat;}

	public double getPrixMeilleurOffre() {return prixMeilleurOffre;}
	public void setPrixMeilleurOffre(double prixMeilleurOffre) {this.prixMeilleurOffre = prixMeilleurOffre;}
	
	@OneToMany(mappedBy="article", fetch=FetchType.EAGER)
	public Set<Inscription> getInscription() {return inscription;}
	public void setInscription(Set<Inscription> inscription) {this.inscription = inscription;}

}
