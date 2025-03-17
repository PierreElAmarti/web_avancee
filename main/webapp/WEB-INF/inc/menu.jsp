<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link"  href="<c:url value=""/>">Classement</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/InscriptionUtilisateurServlet"/>">Inscription</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/ConnexionUtilisateurServlet"/>">Connexion</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value=""/>">Profil</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value=""/>">Historique</a>
            </li>
            
        </ul>
    </div>
</nav>