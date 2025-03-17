<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
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
	</div>
</body>
</html>