<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
	<style type="text/css">
	fieldset {
	  margin: auto;
	  width: 50%;
	}
	</style>
</head>
<body>
<form action="appLogin" method="POST">
	<fieldset style="width:250px" >
		<legend>Login</legend>
		<table>
		<tr>
		  <td>Email</td>
		  <td><input type="text" id="first_name" name="email"></td>
		</tr>
		<tr>
		  <td>Password</td>
		  <td><input type="password" id="password" name="password"></td>
		</tr>
		<tr>
		  <td></td>
		  <td><button type="submit" id="send">Login</button></td>
		</tr>
		</table>
	</fieldset>
</form>
</body>
</html>
