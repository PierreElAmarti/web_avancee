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
        <h2>Changement de mot de passe</h2>
       	<form method="post" action="<c:url value="MotDePasseOublieServlet"/>">
       	
       	<c:if test="${empty sessionScope.id}">
            <div class="form-group">
                <label for="loginUsername">Nom d'utilisateur</label>
                <input name="nomUtilisateur" type="text" class="form-control" id="loginUsername" value="${form.nomUtilisateur}" placeholder="Entrez votre nom d'utilisateur">
            </div>
        </c:if>     
           <c:import url="/WEB-INF/inc/questionSecrete.jsp" />
            
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
            <button type="submit" class="btn btn-primary">Changer mot de passe</button>
        </form>
    </div>
</body>
</html>