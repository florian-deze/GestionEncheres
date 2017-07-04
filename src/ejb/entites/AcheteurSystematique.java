package ejb.entites;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class AcheteurSystematique extends Acheteur implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public AcheteurSystematique() {}

	public double pourcentage;

	public double getPourcentage() {
		return pourcentage;
	}

	public void setPourcentage(double pourcentage) {
		this.pourcentage = pourcentage;
	}
	
	public double surenchere(double prixActuel){
		return(prixActuel*(1+pourcentage/100));
	}
}
