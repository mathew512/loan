<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login - Loan System</title>

    <style>
        body {
            font-family: Arial;
            background: #f1f5f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .login-box {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
            width: 320px;
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        input {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        button {
            width: 100%;
            padding: 10px;
            background: #2563eb;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        button:hover {
            background: #1d4ed8;
        }

        .error {
            color: red;
            text-align: center;
        }
    </style>
</head>

<body>

<div class="login-box">

    <h2>Loan System Login</h2>

    <form action="../login" method="post">

        <input type="text" name="username" placeholder="Username" required />

        <input type="password" name="password" placeholder="Password" required />

        <button type="submit">Login</button>

    </form>

    <p class="error">
        <%
            if (request.getParameter("error") != null) {
                out.println("Invalid username or password!");
            }
        %>
    </p>

</div>

</body>
</html>