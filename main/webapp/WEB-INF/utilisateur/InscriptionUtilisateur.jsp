<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
   	<link type="text/css" rel="stylesheet" href="<c:url value="/style.css"/>" />
</head>
<body>
	<c:import url="/WEB-INF/inc/menu.jsp" />
    <div class="container mt-5">
        <h2>Inscription</h2>
        <form method="post" action="<c:url value="/InscriptionUtilisateurServlet"/>">
            <div class="form-group">
                <label for="username">Nom d'utilisateur</label>
                <input name="nomUtilisateur" type="text" class="form-control" id="username" value="${utilisateur.nomUtilisateur}" placeholder="Entrez votre nom d'utilisateur">
            <span class="erreur">${form.erreurs['nomUtilisateur']}</span>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input name="email" type="text" class="form-control" id="email" value="${utilisateur.adresseMail}" placeholder="Entrez votre email">
            <span class="erreur">${form.erreurs['email']}</span>
            </div>
            <div class="form-group">
                <label for="password">Mot de passe</label>
                <input name="motDePasse" type="password" class="form-control" id="password" placeholder="Entrez votre mot de passe">
            <span class="erreur">${form.erreurs['motDePasse']}</span>
            </div>
            <div class="form-group">
                <label for="confirmpassword">Confirmer mot de passe</label>
                <input name="confirmerMotDePasse" type="password" class="form-control" id="password" placeholder="Confirmer votre mot de passe">
            <span class="erreur">${form.erreurs['confirmerMotDePasse']}</span>
            </div>
            
            <c:import url="/WEB-INF/inc/questionSecrete.jsp" />
            
            <button type="submit" class="btn btn-primary">S'inscrire</button>
        </form>
        <p class="mt-3">Déjà un compte? <a href="<c:url value="/ConnexionUtilisateurServlet"/>">Connectez-vous ici</a></p>
    </div>
</body>
</html>