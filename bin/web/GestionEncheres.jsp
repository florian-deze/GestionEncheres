<%@page import="ejb.entites.*" %>
<%@page import="ejb.sessions.*" %>
<%@page import="javax.naming.InitialContext" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.HashSet" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Collections" %>
<%@page import="java.text.NumberFormat" %>
<%@page import="java.text.DecimalFormat" %>

<%! String address; %>
<%! InitialContext ic; %>
<%! Object obj; %>
<%! ServiceGestionEncheres sge; %>
<%! String code; %>
<%! Article article; %>
<%! String adresseEmail; %>
<%! String prix; %>
<%! Set<Offre> offres; %>
<%! List<Offre> list; %>
<%! Set<Inscription> inscription; %>
<%! NumberFormat formatter; %>

<%
	address = "ejb:gestionEncheres/gestionEncheresSessions//"
     	    + "ServiceGestionEncheresBean!ejb.sessions.ServiceGestionEncheresRemote";
	formatter = new DecimalFormat("#0.00");  


   	ic = new InitialContext();
   	obj = ic.lookup(address);
   	sge = (ServiceGestionEncheres) obj;
   	
    	
   	code = request.getParameter("code");
   	adresseEmail = request.getParameter("adresseEmail");
   	prix = request.getParameter("prix");
   	
    if ((adresseEmail != null) && (prix != null)) {
    	try {
    		sge.proposerOffre(adresseEmail, code, Double.parseDouble(prix));
    	} catch(EnchereTermineeException e1) {
    		e1.printStackTrace();
    	} catch(EnchereNonCommenceeException e2) {
    		e2.printStackTrace();
       	} catch(InscriptionInconnuException e3) {
       		e3.printStackTrace();
       	}
    }
    
    try {
   	article = sge.getArticle(code);

   	
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des encheres</title>
</head>
<body>

<h1>Descriptif Article</h1>

<p>
    Article <%=sge.getArticle(code).getNom() %> (ref : <%=sge.getArticle(code).getCode() %>), prix initial de <%=sge.getArticle(code).getPrixInitial() %> 
</p>

<p>
	Enchère en cours, prix en cours <%= formatter.format(sge.getMeilleureOffre(code).getPrixOffre()) %>
</p>

<ul>
    <!-- Boucle : parcours de toutes les offres proposees -->
    <% 
    
    offres = new HashSet<Offre>();
    inscription = sge.getArticle(code).getInscription();
    for (Inscription inscri : inscription){
        offres.addAll(inscri.getOffres());
    }
    List<Offre> list = new ArrayList<Offre>(offres);
    Collections.sort(list);
    
    for (Offre offre : list){ %>
    	<li>Le <%=offre.getDateHeureJour() %> : offre de <%= formatter.format(offre.getPrixOffre()) %> par <%= offre.getInscription().getAcheteur().getAdresseEmail() %></li>
    <!-- Fin de boucle -->
    <% } %>
    
</ul>

<h1>Faire une offre</h1>

<form  action="GestionEncheres" method="get">
    <input type="hidden" name="code" value="<%=sge.getArticle(code).getCode()%>">
    Email acheteur : <input type="email" name="adresseEmail">
    Prix proposé : <input type="number" step="any" name="prix">
    <input type="submit" value="Encherir"/>
</form>

</body>
</html>
<%
    } catch (ArticleInconnuException ex2) {
    	%>
    	<html>
    	<body>
    		<h1>Article <%=code %> inconnu</h1>

    	</body>
    	</html>
    	<%
	}
	%>