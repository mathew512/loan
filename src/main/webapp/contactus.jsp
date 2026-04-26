<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Contact Us</title>
    <style>
        body { font-family: Arial; margin: 0; background-color: #f4f6f8; }
        .container { width: 90%; max-width: 900px; margin: 50px auto; background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }
        h1 { text-align: center; color: #333; }
        form { display: grid; gap: 15px; }
        input, textarea { width: 100%; padding: 12px; border: 1px solid #ccc; border-radius: 6px; font-size: 14px; }
        textarea { resize: vertical; min-height: 120px; }
        button { background-color: #007bff; color: white; border: none; padding: 12px; font-size: 16px; border-radius: 6px; cursor: pointer; }
        button:hover { background-color: #0056b3; }
    </style>
</head>
<body>
<div class="container">
    <h1>Contact Us</h1>
    <form action="contact_us_display.jsp" method="post">
        <input type="text" name="name" placeholder="Your Name" required>
        <input type="email" name="email" placeholder="Your Email" required>
        <input type="text" name="subject" placeholder="Subject">
        <textarea name="message" placeholder="Your Message" required></textarea>
        <button type="submit">Send Message</button>
    </form>
</div>
</body>
</html>
