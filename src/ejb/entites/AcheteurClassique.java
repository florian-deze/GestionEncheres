package ejb.entites;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class AcheteurClassique extends Acheteur implements Serializable {

	private static final long serialVersionUID = 1L;

	public AcheteurClassique() {}

	public double surenchere(double prixActuel){
		return(prixActuel);
	}
}
