<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<c:import url="/WEB-INF/inc/menu.jsp" />
    <div class="container mt-5">
        <h2>Inscription</h2>
        <form method="post" action="<c:url value="/InscriptionUtilisateurServlet"/>">
            <div class="form-group">
                <label for="username">Nom d'utilisateur</label>
                <input name="nomUtilisateur" type="text" class="form-control" id="username" placeholder="Entrez votre nom d'utilisateur">
            <span class="erreur">${form.erreurs['nomUtilisateur']}</span>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input name="email" type="text" class="form-control" id="email" placeholder="Entrez votre email">
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
            <div class="form-group">
                <label for="confirmpassword">Question secrète</label>
	            <select name="questionSecrete" class="form-select">
	  				<option selected value="1">Dans quelle ville êtes-vous né(e) ?</option>
	  				<option value="2">Quel est le premier concert auquel vous avez assisté ?</option>
	  				<option value="3">Quels étaient le fabricant et le modèle de votre première voiture ?</option>
	  				<option value="4">Dans quelle ville vos parents se sont-ils rencontrés ?</option>
	  				<option value="5">Quel etait votre premier numéro de téléphone ?</option>
	  				<option value="5">Quel etait le nom de votre premier animal de compagnie ?</option>
				</select>
                <input name="reponseQuestionSecrete" type="text" class="form-control" id="reponseQuestionSecrete" placeholder="Entrez la réponse à la question secrète">
            <span class="erreur">${form.erreurs['questionSecrete']}</span>
            </div>
            
            <button type="submit" class="btn btn-primary">S'inscrire</button>
        </form>
        <p class="mt-3">Déjà un compte? <a href="<c:url value="/ConnexionUtilisateurServlet"/>">Connectez-vous ici</a></p>
    </div>
</body>
</html>