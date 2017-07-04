package ejb.sessions;

import ejb.entites.Acheteur;
import ejb.entites.Article;
import ejb.entites.Inscription;
import ejb.entites.Offre;

import java.util.Collection;

public interface ServiceGestionEncheres {

    void creerArticle(String code, String nom, double prixInitial) throws ArticleDejaCreeException, PrixInitialException;

    Article getArticle(String code) throws ArticleInconnuException;

    Collection<Acheteur> getAcheteurs();

    void creerAcheteurClassique(String adresseEmail) throws AcheteurDejaCreeException;

    void creerAcheteurSystematique(String adresseEmail, int pourcentage) throws AcheteurDejaCreeException;

    void creerAcheteurAgressif(String adresseEmail) throws AcheteurDejaCreeException;

    void creerAcheteurAleatoire(String adresseEmail) throws AcheteurDejaCreeException;

    Acheteur getAcheteur(String adresseEmail) throws AcheteurInconnuException;

    Collection<Article> getArticles();

    void ouvertureEnchere(String code);

    Acheteur clotureEnchere(String code) throws OffreInconnuException;

    Inscription getInscription (int codeInscription) throws InscriptionInconnuException;

    void inscription(String email, String code, int codeInscription, double plafond) throws InscriptionImpossibleException, InscriptionDejaCreeException, ArticleInconnuException, AcheteurInconnuException;

    void proposerOffre(String email, String code, Double prix) throws EnchereTermineeException, EnchereNonCommenceeException,InscriptionInconnuException;

    public Offre getMeilleureOffre(String code);
    
}