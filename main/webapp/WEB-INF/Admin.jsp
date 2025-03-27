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
   	<link type="text/css" rel="stylesheet" href="<c:url value="/style.css"/>" />
</head>
<body>
	<c:import url="/WEB-INF/inc/menu.jsp" />
    <div class="container mt-5">
        <h2>Ajouter une Partie</h2>
       	<form method="post" action="<c:url value="/AdminServlet"/>">
            <div class="form-group">
                <label for="date">Date</label>
                <input name="date" type="date" class="form-control" id="date">
  				<span class="erreur">${form.erreurs['date']}</span>
            </div>
            <div class="form-group">
                <label for="temps">Temp de la partie</label>
            	<input type="time" id="temps" name="temps">
  				<span class="erreur">${form.erreurs['temps']}</span>
            </div>
            <div>
                <label for="gagnant1">Joueur 1 gagnant</label>
            	<input type="radio" id="gagnant1" name="gagnant" value="gagnant1" checked />
                <br/>
                <label for="gagnant2">Joueur 2 gagnant</label>
            	<input type="radio" id="gagnant2" name="gagnant" value="gagnant2" />
                <br/>
            </div>
            <div>
                <label for="nomUtilisateur1">Nom d'utilisateur du joueur 1</label>
                <input name="nomUtilisateur1" type="text" class="form-control" id="nomUtilisateur1" placeholder="Entrez le nom d'utilisateur du joueur 1">
  				<span class="erreur">${form.erreurs['nomUtilisateur1']}</span>
                <br/>
                <label for="nomUtilisateur2">Nom d'utilisateur du joueur 2</label>
            	<input name="nomUtilisateur2" type="text" class="form-control" id="nomUtilisateur2" placeholder="Entrez le nom d'utilisateur du joueur 2">
  				<span class="erreur">${form.erreurs['nomUtilisateur2']}</span>
                <br/>
            </div>
            <div>
                <label for="score1">Score du joueur 1</label>
            	<input type="number" id="score1" name="score1">
  				<span class="erreur">${form.erreurs['score1']}</span>
                <br/>
                <label for="score2">Score du joueur 2</label>
            	<input type="number" id="score2" name="score2">
  				<span class="erreur">${form.erreurs['score2']}</span>
            </div>
            <button type="submit" class="btn btn-primary">Créer la partie</button>
        </form>
    </div>
</body>
</html>