<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Juego de Ahorcado</title>
    <link rel="stylesheet" href="css/styles.css">
    <script src="js/script.js"></script>
</head>
<body>
<h1>Juego de Ahorcado</h1>
<p>Palabra: <%= String.valueOf((char[]) session.getAttribute("guessedWord")) %></p>
<p>Intentos restantes: <%= session.getAttribute("attemptsLeft") %></p>
<p>Letras incorrectas: <%= session.getAttribute("wrongGuesses").toString() %></p>

<form action="hangman" method="post">
    <input type="hidden" name="action" value="guess">
    <label for="guess">Ingresa una letra:</label>
    <input type="text" id="guess" name="guess" maxlength="1" required>
    <button type="submit">Adivinar</button>
</form>

<%
    char[] guessedWord = (char[]) session.getAttribute("guessedWord");
    boolean wordGuessed = true;
    for (char c : guessedWord) {
        if (c == '_') {
            wordGuessed = false;
            break;
        }
    }
    if (wordGuessed) {
%>
<p>¡Felicidades! Has adivinado la palabra: <%= session.getAttribute("selectedWord") %></p>
<% } else if ((int) session.getAttribute("attemptsLeft") == 0) { %>
<p>¡Perdiste! La palabra era: <%= session.getAttribute("selectedWord") %></p>
<% } %>
</body>
</html>
