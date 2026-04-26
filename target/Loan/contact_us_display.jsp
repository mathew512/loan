<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="contact" class="app.pages.ContactUs" scope="request"/>
<jsp:setProperty name="contact" property="*"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Message Sent</title>
    <style>
        body { font-family: Arial; background-color: #f4f6f8; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .card { background: #fff; padding: 40px; border-radius: 10px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); text-align: center; max-width: 500px; }
        h1 { color: #333; margin-bottom: 10px; }
        p { color: #555; margin-bottom: 20px; }
        a.button { display: inline-block; padding: 12px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 6px; }
        a.button:hover { background-color: #0056b3; }
    </style>
</head>
<body>
<div class="card">
    <h1>Thank You!</h1>
    <p><strong>Name:</strong> ${contact.name}</p>
    <p><strong>Email:</strong> ${contact.email}</p>
    <p><strong>Subject:</strong> <c:out value="${contact.subject}" default="No Subject"/></p>
    <p><strong>Message:</strong> ${contact.message}</p>
    <p>Your message has been successfully submitted. We will get back to you shortly.</p>
    <a href="index.jsp" class="button">Back to Home</a>
</div>
</body>
</html>
