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
    <c:if test="${ ! empty utilisateur }">
		<div class="h-100 d-flex align-items-center justify-content-center">
        	<table class="table table-striped">
                <tr>
                    <th><c:out value="${ utilisateur.classement }"/></th>           
                    <th><c:out value="${ utilisateur.nomUtilisateur }"/></th>
                    <th><c:out value="${ utilisateur.elo }"/></th>   
                </tr>
        	</table>
		</div>
		<div class="h-100 d-flex align-items-center justify-content-center">
	       	<table class="table table-striped">
	       		<c:forEach items="${ parties }" var="mapParties" varStatus="boucle">
	           		<tr>
	                    <td><c:out value="${ mapParties.id }"/></td>
	                    <td><c:out value="${ mapParties.date }"/></td>
	                    <td><c:out value="${ mapParties.temps }"/></td>
	                </tr>
               </c:forEach>
          	</table>
		</div>
    </c:if>
</body>
</html>