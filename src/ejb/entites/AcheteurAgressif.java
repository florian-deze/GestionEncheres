package ejb.entites;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class AcheteurAgressif extends AcheteurSystematique implements Serializable {

	private static final long serialVersionUID = 1L;

	public AcheteurAgressif() {}
	
	public double surenchere(double prixActuel){
		return (prixActuel *(1.5));
	}
	
	
}
