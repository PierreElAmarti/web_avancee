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
	<div class="h-100 d-flex align-items-center justify-content-center">
	 <c:choose>
            <%-- Si aucun client n'existe en session, affichage d'un message par défaut. --%>
            <c:when test="${ empty utilisateurs }">
                <p class="erreur">Aucun utilisateur enregistré.</p>
            </c:when>
            <%-- Sinon, affichage du tableau. --%>
            <c:otherwise>
            <table class="table table-striped">
                <tr>
                    <th scope="col">Classement</th>           
                    <th scope="col">Nom d'utilisateur</th>
                    <th scope="col">Elo</th>   
                </tr>
                <%-- Parcours de la Map des clients en session, et utilisation de l'objet varStatus. --%>
                <c:forEach items="${ utilisateurs }" var="mapUtilisateurs" varStatus="boucle">
	                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
		                <c:choose>
				            <c:when test="${ mapUtilisateurs.classement == 1 }">
				                	<tr class="gold">
				            </c:when>
				            <c:when test="${ mapUtilisateurs.classement == 2 }">
				                	<tr class="silver">
				            </c:when>
				            <c:when test="${ mapUtilisateurs.classement == 3 }">
				                	<tr class="bronze">
				            </c:when>
				            <c:otherwise>
				                	<tr>
				            </c:otherwise>
		       	 		</c:choose>
	                    <%-- Affichage des propriétés du bean Client, qui est stocké en tant que valeur de l'entrée courante de la map --%>
	                    <th  scope="col"><c:out value="${ mapUtilisateurs.classement }"/></th>
	                    <td><c:out value="${ mapUtilisateurs.nomUtilisateur }"/></td>
	                    <td><c:out value="${ mapUtilisateurs.elo }"/></td>
	                </tr>
                </c:forEach>
		        <c:if test="${ ! empty utilisateur }">
	                <tr class="blank_space">
	                	<td colspan="3">&nbsp;</td>
	                </tr>
		                <tr>
		                    <th scope="col">Classement</th>           
		                    <th scope="col">Nom d'utilisateur</th>
		                    <th scope="col">Elo</th>       
		                </tr>
		                <c:choose>
		            <%-- Si aucun client n'existe en session, affichage d'un message par défaut. --%>
				            <c:when test="${ mapUtilisateurs.classement == 1 }">
				                	<tr class="gold">
				            </c:when>
				            <c:when test="${ mapUtilisateurs.classement == 2 }">
				                	<tr class="silver">
				            </c:when>
				            <c:when test="${ mapUtilisateurs.classement == 3 }">
				                	<tr class="bronze">
				            </c:when>
				            <c:otherwise>
				                	<tr>
				            </c:otherwise>
		       	 		</c:choose>
		                    <%-- Affichage des propriétés du bean Client, qui est stocké en tant que valeur de l'entrée courante de la map --%>
		                    <th scope="row"><c:out value="${ utilisateur.classement }"/></th>
		                    <td><c:out value="${ utilisateur.nomUtilisateur }"/></td>
		                    <td><c:out value="${ utilisateur.elo }"/></td>
		                </tr>
		        </c:if>
            </table>
            </c:otherwise>
        </c:choose>
        </div>
</body>
</html>