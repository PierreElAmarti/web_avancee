<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <c:if test="${ ! empty utilisateur }">
		<div class="h-100 d-flex align-items-center justify-content-center">
        	<table class="table table-striped">
                <tr>
                    <th scope="col">Classement</th>           
                    <th scope="col">Nom d'utilisateur</th>
                    <th scope="col">Elo</th>   
                </tr>
                <tr>
                    <th><c:out value="${ utilisateur.classement }"/></th>           
                    <th><c:out value="${ utilisateur.nomUtilisateur }"/></th>
                    <th><c:out value="${ utilisateur.elo }"/></th>   
                </tr>
        	</table>
		</div>
		<div class="h-100 d-flex align-items-center justify-content-center">
	       	<table class="table table-striped">
                <tr>
                    <th scope="col">Date</th>           
                    <th scope="col">Temps</th>
                    <th scope="col">Nom d'utilisateur</th>   
                    <th scope="col">Elo</th>           
                    <th scope="col">Gagnant</th>
                    <th scope="col">Nom d'utilisateur</th>  
                    <th scope="col">Elo</th>           
                    <th scope="col">Gagnant</th> 
                </tr>
	       		<c:forEach items="${ parties }" var="mapPartiesMaitre" varStatus="boucle">
	           		<tr>
	           			<td><fmt:formatDate value="${mapPartiesMaitre.date}" type="date" pattern="yyyy-MM-dd" /></td>
    					<td><c:out value="${ mapPartiesMaitre.temps }"/></td>
	                    
	       				<c:forEach items="${ mapPartiesMaitre.partieFils }" var="mapParties" varStatus="boucle">
	                    	<td><c:out value="${ mapParties.utilisateur.nomUtilisateur }"/></td>
	                    	<td><c:out value="${ mapParties.score }"/></td>
	                    	<c:if test="${ mapParties.gagnant == true }">
	                    		<td>Oui</td>
	                    	</c:if>
	                    	<c:if test="${ mapParties.gagnant == false }">
	                    		<td>Non</td>
	                    	</c:if>
	       				</c:forEach>
	                </tr>
               </c:forEach>
          	</table>
		</div>
    </c:if>
</body>
</html>