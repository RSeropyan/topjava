<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Edit meal</title>
    <style>
        input[type=text], input[type=datetime-local], input[type=number] {
            width: 200px;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>

<form action="meals" method="post">
    <label for="datetime">
        <b>DateTime</b>
        <br>
        <input type="datetime-local" name="datetime" id="datetime" required>
    </label>
    <br><br>
    <label for="description">
        <b>Description</b>
        <br>
        <input type="text" name="description" id="description" required>
    </label>
    <br><br>
    <label for="calories">
        <b>Calories</b>
        <br>
        <input type="number" name="calories" id="calories" min="0" required>
    </label>
    <br><br>
    <input type="submit" value="Save">
    <input type="reset" value="Cancel">
</form>

</body>
</html>
