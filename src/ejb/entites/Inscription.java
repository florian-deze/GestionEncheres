package ejb.entites;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

@Entity
public class Inscription implements Serializable {

	private static final long serialVersionUID = 1L;

	public int codeInscription;
	public double plafond;
	public Article article;
	public Set<Offre> offres;
	public Acheteur acheteur;

	public Inscription() {
	}

	@Id
	@GeneratedValue
	public int getCodeInscription() {
		return codeInscription;
	}

	public void setCodeInscription(int codeInscription) {
		this.codeInscription = codeInscription;
	}

	public double getPlafond() {
		return plafond;
	}

	public void setPlafond(double plafond) {
		this.plafond = plafond;
	}

	@ManyToOne
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	@OneToMany(mappedBy = "inscription", fetch=FetchType.EAGER)
	public Set<Offre> getOffres() {
//		System.out.println("************ offres dans class Inscription: " + offres);
		return offres;
	}

	public void setOffres(Set<Offre> offres) {
		this.offres = offres;
	}

	@ManyToOne
	public Acheteur getAcheteur() {
		return acheteur;
	}

	public void setAcheteur(Acheteur acheteur) {
		this.acheteur = acheteur;
	}

}
