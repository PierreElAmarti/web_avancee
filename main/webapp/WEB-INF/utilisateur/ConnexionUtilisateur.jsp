<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<c:import url="/WEB-INF/inc/menu.jsp" />
    <div class="container mt-5">
        <h2>Connexion</h2>
       	<form method="post" action="<c:url value="/ConnexionUtilisateurServlet"/>">
            <div class="form-group">
                <label for="loginUsername">Nom d'utilisateur</label>
                <input name="nomUtilisateur" type="text" class="form-control" id="loginUsername" value="${form.nomUtilisateur}" placeholder="Entrez votre nom d'utilisateur">
            </div>
            <div class="form-group">
                <label for="loginPassword">Mot de passe</label>
                <input name="motDePasse" type="password" class="form-control" id="loginPassword" placeholder="Entrez votre mot de passe">
            </div>
            <span class="erreur">${form.erreurs['connexion']}</span>
            <button type="submit" class="btn btn-primary">Se connecter</button>
        </form>
        <p class="mt-3">Pas de compte? <a href="<c:url value="/InscriptionUtilisateurServlet"/>">Connexion </a>
        <a href="<c:url value="InscriptionUtilisateurServlet"/>">Mot de passe oublié ? </a>
        </p>
    </div>
</body>
</html>