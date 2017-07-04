package client;

import ejb.sessions.*;

import javax.naming.InitialContext;
import javax.naming.NamingException ;


public class Main {


    public static void main(String[] args) {
        String address = "ejb:gestionEncheres/gestionEncheresSessions/"
                + "ServiceGestionEncheresBean!ejb.sessions.ServiceGestionEncheresRemote";
        try {
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup(address);
            ServiceGestionEncheres sge = (ServiceGestionEncheres) obj;

            // Creation d'acheteurs

            System.out.println("*** Creation d'acheteurs ***");

            try {
                sge.creerAcheteurAgressif("john.smith@gmail.com");
            } catch (AcheteurDejaCreeException e) {
                System.err.println("Mr Smith est deja inscrit");
            }

            try {
                sge.creerAcheteurAleatoire("paul.dupont@free.fr");
            } catch (AcheteurDejaCreeException e) {
                System.err.println("Mr Dupont est deja inscrit");
            }

            try {
                sge.creerAcheteurClassique("moi@free.fr");
            } catch (AcheteurDejaCreeException e) {
                System.err.println("Mr Moi est deja inscrit");
            }

            try {
                sge.creerAcheteurSystematique("alain.dubois@yahoo.fr", 50);
            } catch (AcheteurDejaCreeException e) {
                System.err.println("Mr Dubois est deja inscrit");
            }
            

            try {
                sge.creerAcheteurAgressif("adam.smith@gmail.com");
            } catch (AcheteurDejaCreeException e) {
                System.err.println("Mr Smith est deja inscrit");
            }

            // Creation d'article

            System.out.println("*** Creation d'articles ***");
            try {
                sge.creerArticle("re12", "cactus qui pique", 12.5);
            } catch (ArticleDejaCreeException e) {
                e.printStackTrace();
            } catch (PrixInitialException e) {
                e.printStackTrace();
            }

            try {
                sge.creerArticle("re17", "tablette 10 pouces", 20);
            } catch (ArticleDejaCreeException e) {
                e.printStackTrace();
            } catch (PrixInitialException e) {
                e.printStackTrace();
            }


            // Gestion des inscriptions

            System.out.println("*** Gestion des inscriptions ***");

            try {
                sge.inscription("john.smith@gmail.com", "re12", 01, 4500);
            } catch (InscriptionImpossibleException e) {
                e.printStackTrace();
            } catch (ArticleInconnuException e) {
                e.printStackTrace();
            } catch (AcheteurInconnuException e) {
                e.printStackTrace();
            } catch (InscriptionDejaCreeException e) {
                e.printStackTrace();
            }
            
            try {
                sge.inscription("adam.smith@gmail.com", "re12", 07, 9000);
            } catch (InscriptionImpossibleException e) {
                e.printStackTrace();
            } catch (ArticleInconnuException e) {
                e.printStackTrace();
            } catch (AcheteurInconnuException e) {
                e.printStackTrace();
            } catch (InscriptionDejaCreeException e) {
                e.printStackTrace();
            }

            try {
                sge.inscription("paul.dupont@free.fr", "re12", 02, 2500);
            } catch (InscriptionImpossibleException e) {
                e.printStackTrace();
            } catch (ArticleInconnuException e) {
                e.printStackTrace();
            } catch (AcheteurInconnuException e) {
                e.printStackTrace();
            } catch (InscriptionDejaCreeException e) {
                e.printStackTrace();
            }

            try {
                sge.inscription("moi@free.fr", "re12", 03, 100000.0);
            } catch (InscriptionImpossibleException e) {
                e.printStackTrace();
            } catch (ArticleInconnuException e) {
                e.printStackTrace();
            } catch (AcheteurInconnuException e) {
                e.printStackTrace();
            } catch (InscriptionDejaCreeException e) {
                e.printStackTrace();
            }

            try {
                sge.inscription("alain.dubois@yahoo.fr", "re12", 04, 1000);
            } catch (InscriptionImpossibleException e) {
                e.printStackTrace();
            } catch (ArticleInconnuException e) {
                e.printStackTrace();
            } catch (AcheteurInconnuException e) {
                e.printStackTrace();
            } catch (InscriptionDejaCreeException e) {
                e.printStackTrace();
            }

            try {
                sge.inscription("paul.dupont@free.fr", "re17", 05, 3000);
            } catch (InscriptionImpossibleException e) {
                e.printStackTrace();
            } catch (ArticleInconnuException e) {
                e.printStackTrace();
            } catch (AcheteurInconnuException e) {
                e.printStackTrace();
            } catch (InscriptionDejaCreeException e) {
                e.printStackTrace();
            }

            try {
                sge.inscription("moi@free.fr", "re17", 06, 3000);
            } catch (InscriptionImpossibleException e) {
                e.printStackTrace();
            } catch (ArticleInconnuException e) {
                e.printStackTrace();
            } catch (AcheteurInconnuException e) {
                e.printStackTrace();
            } catch (InscriptionDejaCreeException e) {
                e.printStackTrace();
            }

            // Debut des encheres

            sge.ouvertureEnchere("re12");
            sge.ouvertureEnchere("re17");

            System.out.println("OK, vous pouvez proposer une offre ! ");

            try {
                sge.proposerOffre("moi@free.fr", "re12", 50.0);
               // sge.clotureEnchere("re12");
                sge.proposerOffre("moi@free.fr", "re17", 261.0);
               // sge.clotureEnchere("re17");
            }/* catch (AcheteurInconnuException e) {
                e.printStackTrace();
            } */catch (EnchereTermineeException e2) {
                e2.printStackTrace();
            } catch (EnchereNonCommenceeException e3) {
                e3.printStackTrace();
            }/* catch (ArticleInconnuException e4) {
                e4.printStackTrace();
            } */catch (InscriptionInconnuException e5) {
				e5.printStackTrace();
			}
        	System.out.println("fin des offres !");
            
            
        } catch (NamingException e) {
            e.printStackTrace();
        }
           

    }


}