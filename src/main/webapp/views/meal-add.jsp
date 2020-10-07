<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Add meal</title>
    <style>
        input[type=text], input[type=datetime-local], input[type=number] {
            width: 200px;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add meal</h2>

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
        <input type="text" name="description" id="description" placeholder="Enter meal description..." required>
    </label>
    <br><br>
    <label for="calories">
        <b>Calories</b>
        <br>
        <input type="number" name="calories" id="calories" min="0" placeholder="Enter meal calories..." required>
    </label>
    <br><br>
    <input type="submit" value="Save">
    <input type="reset" value="Cancel">
</form>

</body>
</html>
