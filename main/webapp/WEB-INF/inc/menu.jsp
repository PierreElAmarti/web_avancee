<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link"  href="<c:url value="/AcceuilServlet"/>">Classement</a>
            </li>
            
            <c:choose>
            	<c:when test="${empty sessionScope.id}">
		            <li class="nav-item">
		                <a class="nav-link" href="<c:url value="/InscriptionUtilisateurServlet"/>">Inscription</a>
		            </li>
		            <li class="nav-item">
		                <a class="nav-link" href="<c:url value="/ConnexionUtilisateurServlet"/>">Connexion</a>
		            </li>
            	</c:when>
            	<c:otherwise>
		            <li class="nav-item">
		                <a class="nav-link" href="<c:url value="/DeconnexionUtilisateurServlet"/>">Déconnexion</a>
		            </li>
		            <li class="nav-item">
		                <a class="nav-link" href="<c:url value="/ProfilServlet"/>">Profil</a>
		            </li>
		            <li class="nav-item">
		                <a class="nav-link" href="<c:url value="/CompteServlet"/>">Compte</a>
		            </li>
		    		<c:if test="${ sessionScope.permission > 9 }">
			            <li class="nav-item">
			                <a class="nav-link"  href="<c:url value="/AdminServlet"/>">Ajouter une partie</a>
			            </li>
		    		</c:if>
            	</c:otherwise>
            </c:choose>
            
        </ul>
    </div>
</nav>