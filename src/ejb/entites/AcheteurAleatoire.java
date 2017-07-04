package ejb.entites;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class AcheteurAleatoire extends Acheteur implements Serializable {

	private static final long serialVersionUID = 1L;

	public AcheteurAleatoire() {}

	public double surenchere(double prixActuel){
		int nb = (int) (Math.random()*11);
		return(prixActuel + nb);
	}
}
