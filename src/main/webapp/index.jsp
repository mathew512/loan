<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Loan Management System - About Us</title>
    <style>
        body { font-family: Arial; margin: 0; background-color: #f4f6f8; }
        header { background-color: #0f172a; color: white; padding: 20px; text-align: center; }
        nav { background:#1e293b; padding: 12px; text-align:center; }
        nav a { color:white; margin:0 15px; text-decoration:none; font-weight:bold; }
        nav a:hover { color:#fca311; }
        section { margin: 20px; padding: 20px; background: white; border-radius: 8px; }
        footer { margin-top: 30px; padding: 20px; background:#0f172a; color:white; text-align:center; }
        footer a { color:#fca311; text-decoration:none; }
    </style>
</head>
<body>

<%-- ================= HEADER ================= --%>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" pattern="H" var="hour" />

<header>
    <h1>
        <c:choose>
            <c:when test="${hour < 12}">Good Morning — Loan Management System</c:when>
            <c:when test="${hour > 17}">Good Evening — Loan Management System</c:when>
            <c:otherwise>Good Afternoon — Loan Management System</c:otherwise>
        </c:choose>
    </h1>
    <p>${applicationScope.applicationName}</p>
</header>

<%-- ================= NAVBAR ================= --%>
<nav>
    <a href="./">About Us</a>
    <a href="./dashboard">Dashboard</a>
    <a href="./login">Login</a>
</nav>

<%-- ================= CONTENT ================= --%>
<section>
    <h2>Who We Are</h2>
    <p>We are a modern loan management system designed to simplify financial operations, empower businesses, and enhance customer experiences. 
       Our platform combines reliability, scalability, and user-friendly tools to streamline loan processing, repayment tracking, and financial management. 
       Built with clean architecture and robust technology, we aim to deliver efficiency and trust for every transaction.</p>
</section>


<section>
    <h2>Our Mission</h2>
    <p>To provide a reliable, scalable, and easy-to-use loan management platform for businesses and customers.</p>
</section>

<section>
    <h2>What We Offer</h2>
    <c:set var="offers" value="${['Customer Management','Loan Processing','Repayment Tracking','Loan Type Configuration']}" />
    <ul>
        <c:forEach var="item" items="${offers}">
            <li>${item}</li>
        </c:forEach>
    </ul>
</section>

<section>
    <h2>Why Choose Us</h2>
    <p>We use a clean architecture, fast processing, and a scalable built for real-world financial systems.</p>
</section>

<%-- ================= FOOTER ================= --%>
<footer>
    <p>&copy; 2026 Loan System | All Rights Reserved</p>
    <p><a href="./contactus.jsp">Contact Us</a></p>
</footer>

</body>
</html>
