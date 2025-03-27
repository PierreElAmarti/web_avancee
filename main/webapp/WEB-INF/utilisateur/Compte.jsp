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
       	<form method="post" action="<c:url value="/CompteServlet"/>">
           <div class="form-group">
                <label for="username">Nom d'utilisateur</label>
                <input name="nomUtilisateur" type="text" class="form-control" id="username" value="${utilisateur.nomUtilisateur}" placeholder="Entrez votre nom d'utilisateur">
            <span class="erreur">${form.erreurs['nomUtilisateur']}</span>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input name="" type="text" class="form-control" id="email" value="${utilisateur.adresseMail}" disabled>
            <span class="erreur">${form.erreurs['email']}</span>
            </div>
            <div class="form-group">
                <label for="password">Mot de passe</label>
                <input name="" type="password" class="form-control" id="password" value="********" disabled>
            </div>
            <button type="submit" class="btn btn-primary">Sauvegarder</button>
        </form>
        <p><a href="<c:url value="/MotDePasseOublieServlet"/>">Changer de mot de passe</a></p>
    </div>
</body>
</html>