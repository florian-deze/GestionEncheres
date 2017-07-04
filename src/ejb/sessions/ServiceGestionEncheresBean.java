package ejb.sessions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ejb.entites.AcheteurClassique;
import ejb.entites.AcheteurSystematique;
import ejb.entites.AcheteurAgressif;
import ejb.entites.AcheteurAleatoire;
import ejb.entites.Acheteur;
import ejb.entites.Article;
import ejb.entites.Etat;
import ejb.entites.Offre;
import ejb.entites.Inscription;
import java.sql.Timestamp;
import java.util.Calendar;

@Stateless
public class ServiceGestionEncheresBean implements ServiceGestionEncheresRemote, ServiceGestionEncheresLocal {

	@PersistenceContext(unitName = "gestionEncheres")
	protected EntityManager em;

	public ServiceGestionEncheresBean() {
	}

	/* creation d'un article */
	public void creerArticle(String code, String nom, double prixInitial)
			throws ArticleDejaCreeException, PrixInitialException {
		try {
			this.getArticle(code);
			throw new ArticleDejaCreeException();
		} catch (ArticleInconnuException e) {
			Article article = new Article();
			article.setCode(code);
			article.setNom(nom);
			if (prixInitial >= 0) {
				article.setPrixInitial(prixInitial);
			} else {
				throw new PrixInitialException();
			}
			article.setEtat(Etat.NONCOMMENCEE);
			article.setPrixMeilleurOffre(prixInitial);
			/* article.setInscription(new HashSet<>()); */
			em.persist(article);
		}
	}

	/* recuperation d'un article */
	public Article getArticle(String code) throws ArticleInconnuException {
		Article article = (Article) em.find(Article.class, code);
		if (article == null)
			throw new ArticleInconnuException();
		return article;
	}

	/* liste des acheteurs */
	@SuppressWarnings("unchecked")
	public Collection<Acheteur> getAcheteurs() {
		return em.createQuery("from Acheteur a").getResultList();
	}

	/* creation d'un acheteur classique */
	public void creerAcheteurClassique(String adresseEmail) throws AcheteurDejaCreeException {
		try {
			this.getAcheteur(adresseEmail);
			throw new AcheteurDejaCreeException();
		} catch (AcheteurInconnuException e) {
			AcheteurClassique acheteurClassique = new AcheteurClassique();
			acheteurClassique.setAdresseEmail(adresseEmail);
			em.persist(acheteurClassique);
		}
	}

	/* creation d'un acheteur systematique */
	public void creerAcheteurSystematique(String adresseEmail, int pourcentage) throws AcheteurDejaCreeException {
		try {
			this.getAcheteur(adresseEmail);
			throw new AcheteurDejaCreeException();
		} catch (AcheteurInconnuException e) {
			AcheteurSystematique acheteurSystematique = new AcheteurSystematique();
			acheteurSystematique.setAdresseEmail(adresseEmail);
			acheteurSystematique.setPourcentage(pourcentage);
			em.persist(acheteurSystematique);
		}
	}

	/* creation d'un acheteur agressif */
	public void creerAcheteurAgressif(String adresseEmail) throws AcheteurDejaCreeException {
		try {
			this.getAcheteur(adresseEmail);
			throw new AcheteurDejaCreeException();
		} catch (AcheteurInconnuException e) {
			AcheteurAgressif acheteurAgressif = new AcheteurAgressif();
			acheteurAgressif.setAdresseEmail(adresseEmail);
			em.persist(acheteurAgressif);
		}
	}

	/* creation d'un acheteur aleatoire */
	public void creerAcheteurAleatoire(String adresseEmail) throws AcheteurDejaCreeException {
		try {
			this.getAcheteur(adresseEmail);
			throw new AcheteurDejaCreeException();
		} catch (AcheteurInconnuException e) {
			AcheteurAleatoire acheteurAleatoire = new AcheteurAleatoire();
			acheteurAleatoire.setAdresseEmail(adresseEmail);
			em.persist(acheteurAleatoire);
		}
	}

	/* recuperation d'un acheteur */
	public Acheteur getAcheteur(String adresseEmail) throws AcheteurInconnuException {
		Acheteur acheteur = (Acheteur) em.find(Acheteur.class, adresseEmail);
		if (acheteur == null)
			throw new AcheteurInconnuException();
		return acheteur;
	}

	/* liste des articles */
	@SuppressWarnings("unchecked")
	public Collection<Article> getArticles() {
		return em.createQuery("from Article a").getResultList();
	}

	/* est-ce que reference correspond au nom ou au code */
	public Article consultationArticle(String code) throws ArticleInconnuException {
		Article article = (Article) em.find(Article.class, code);
		if (article == null)
			throw new ArticleInconnuException();
		return article;
	}

	/* ouverture des encheres */
	public void ouvertureEnchere(String code) {
		try {
			Article article = getArticle(code);
			article.setEtat(Etat.ENCOURS);
		} catch (ArticleInconnuException ex) {
		}
	}

	/* cloture des encheres, rajouter l'acheteur qui gagne */
	public Acheteur clotureEnchere(String code) throws OffreInconnuException {
		try {
			Article article = getArticle(code);
			article.setEtat(Etat.TERMINEE);
			Offre offre = new Offre();
			offre = getMeilleureOffre(code);
			if (offre == null)
				throw new OffreInconnuException();
			return (offre.getInscription().getAcheteur());
		} catch (ArticleInconnuException ex) {
		}
		return null;
	}

	/* recuperation d'une inscription */
	public Inscription getInscription(int codeInscription) throws InscriptionInconnuException {
		Inscription inscription = (Inscription) em.find(Inscription.class, codeInscription);
		if (inscription == null)
			throw new InscriptionInconnuException();
		return inscription;
	}

	public void inscription(String email, String code, int codeInscription, double plafond)
			throws InscriptionImpossibleException, InscriptionDejaCreeException, ArticleInconnuException,
			AcheteurInconnuException {
		try {
			Article article = getArticle(code);
			Acheteur acheteur = getAcheteur(email);
			Set<Acheteur> acheteurs = new HashSet<Acheteur>();
			Set<Inscription> inscription = new HashSet<Inscription>();
			if (article == null)
				throw new ArticleInconnuException();

			if (article.getEtat() == Etat.TERMINEE) {
				throw new InscriptionImpossibleException();
			}

			if (acheteur == null)
				throw new AcheteurInconnuException();
			/* contient la liste de tout les acheteurs inscrit a l'article */
			inscription = article.getInscription();
			for (Inscription insc : inscription) {
				acheteurs.add(insc.getAcheteur());
			}
			/* Verifie si l'acheteur est deja inscrit */
			for (Acheteur a : acheteurs) {
				if ((a.getAdresseEmail()).equals(email)) {
					throw new InscriptionImpossibleException();
				}
			}
			Inscription inscrip = new Inscription();
			inscrip.setAcheteur(acheteur);
			inscrip.setArticle(article);
			inscrip.setPlafond(plafond);
			// ajout de la nouvelle inscription
			inscription.add(inscrip);
			article.setInscription(inscription);
			em.persist(inscrip);
		} catch (ArticleInconnuException ex) {
		} catch (AcheteurInconnuException ex2) {
		}
	}

	/* proposition d'une offre */
	public void proposerOffre(String email, String code, Double prix)
			throws EnchereTermineeException, EnchereNonCommenceeException, InscriptionInconnuException {
		try {
			Acheteur acheteur = getAcheteur(email);
			Article article = getArticle(code);
			if (article.getEtat() == Etat.TERMINEE) {
				throw new EnchereTermineeException();
			} else if (article.getEtat() == Etat.NONCOMMENCEE) {
				throw new EnchereNonCommenceeException();
			}
			Inscription inscription = new Inscription();
			Set<Inscription> inscrip = new HashSet<Inscription>();
			/* Je reccupere toute les inscriptions a l'article */
			inscrip = article.getInscription();
			int codeInscription = 0;
			for (Inscription inscri : inscrip) {
				/* je cherche l'inscription reliant l'acheteur a l'article */
				if (inscri.getAcheteur().getAdresseEmail().equals(email))
					codeInscription = inscri.getCodeInscription();
			}
			/* Attribution de l'identifiant de l'inscription */
			inscription.setCodeInscription(codeInscription);
			/* verifie que l'inscription existe */
			inscription = (Inscription) em.find(Inscription.class, inscription.getCodeInscription());
			if (inscription.getCodeInscription() == 0) {
				throw new InscriptionInconnuException();
			}
			inscription.setArticle(article);
			inscription.setAcheteur(acheteur);
			inscription.setCodeInscription(codeInscription);
			if ((prix > article.getPrixMeilleurOffre())&&(inscription.getPlafond()>prix)) {
				System.out.println("Prix accepter");
				Offre offre = new Offre();
				offre.setDateHeureJour(new Timestamp((Calendar.getInstance().getTime().getTime())));
				offre.setPrixOffre(prix);
				offre.setInscription(inscription);
				offre.getInscription().setAcheteur(acheteur);
				article.setPrixMeilleurOffre(prix);
				em.persist(offre);
				em.persist(article);
				
				Set<Offre> otmp = inscription.getOffres();
				otmp.add(offre);
				inscription.setOffres(otmp);

				gestionOffre(article.getCode());
			}
		} catch (AcheteurInconnuException ex) {
		} catch (ArticleInconnuException ex2) {
		}
	}


	/* lancement d'offre a partir de la proposition */
	private void gestionOffre(String code) {
		try{
			boolean end = false;
			Article article = getArticle(code);
			while (!end) {
				end = true;
				for (Inscription i : article.getInscription()) {
					Acheteur acheteur= i.getAcheteur();
					double articleTestNull=article.getPrixMeilleurOffre();
					double prix=acheteur.surenchere(articleTestNull);
					double plafond = i.getPlafond();
					String adresseMail=acheteur.getAdresseEmail();
					boolean memeAcheteur=true;
					//recherche du meilleur acheteur actuel
					Offre MeilleurOffre = new Offre();
					MeilleurOffre= getMeilleureOffre(article.getCode());
					// Si ce n'est pas la premiere offre
					if(MeilleurOffre!=null){
						//on reccupere l'adresse de l'acheteur de la meilleur offre
						Acheteur MeilleurAcheteur = MeilleurOffre.getInscription().getAcheteur();
						adresseMail=MeilleurAcheteur.getAdresseEmail();
						memeAcheteur= adresseMail.equals(acheteur.getAdresseEmail());
					}
					else
						memeAcheteur=false; // car il n'y a pas d'offre avant
						//verification de la validite de l'offre
					if(!(memeAcheteur)){
						if ((prix > articleTestNull) && (prix < plafond)) {
							Offre offre = new Offre();
							offre.setDateHeureJour(new Timestamp((Calendar.getInstance().getTime().getTime())));
							offre.setPrixOffre(prix);
							offre.setInscription(i);
							System.out.println(
									"Surenchere de -- " + offre.getInscription().getAcheteur().getAdresseEmail()
											+ " d'un montant de : " + offre.getPrixOffre()
											+ " et un ancien prix a : " + article.getPrixMeilleurOffre());
							article.setPrixMeilleurOffre(offre.getPrixOffre());
							em.persist(offre);
							em.persist(article);
							
							Set<Offre> otmp = i.getOffres();
							otmp.add(offre);
							i.setOffres(otmp);

							end = false;
						}
					}
				}
			}
		}catch(ArticleInconnuException ex){}
	}


	public Offre getMeilleureOffre(String code) {
		try {
			Article article = getArticle(code);
			Set<Offre> offres = new HashSet<Offre>();
			Set<Inscription> inscription = new HashSet<Inscription>();

			inscription.addAll(article.getInscription());
			for (Inscription inscri : inscription) {
				offres.addAll(inscri.getOffres());
			}
			List<Offre> list = new ArrayList<Offre>(offres);
			Collections.sort(list);
			return (list.get(0));
		} catch (ArticleInconnuException ex) {
			ex.printStackTrace();
		} catch (IndexOutOfBoundsException ex2) {
			ex2.printStackTrace();
		}
		return null;
	}
}