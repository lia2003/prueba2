<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Configurar Juego de Ahorcado</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<h1>Configura tu juego de Ahorcado</h1>
<form action="hangman" method="post">
    <input type="hidden" name="action" value="start">
    <label for="wordLength">Longitud de la palabra:</label>
    <input type="number" id="wordLength" name="wordLength" min="3" max="15" required>
    <br>
    <label for="maxAttempts">Número máximo de intentos:</label>
    <input type="number" id="maxAttempts" name="maxAttempts" min="1" max="10" required>
    <br>
    <label for="theme">Tema:</label>
    <select id="theme" name="theme" required>
        <option value="animales">Animales</option>
        <option value="frutas">Frutas</option>
        <option value="paises">Países</option>
    </select>
    <br>
    <button type="submit">Guardar Configuración</button>
</form>
<p style="color:red;"><%= session.getAttribute("message") != null ? session.getAttribute("message") : "" %></p>
</body>
</html>
