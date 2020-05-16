<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
	<style type="text/css">
	fieldset {
	  width: 300px;
	  margin: auto;
	}
	</style>
</head>
<body>
<fieldset>
	<legend>Registration</legend>
	<table>
	<tr>
	  <td>First Name</td>
	  <td><input type="text" id="first_name"></td>
	</tr>
	<tr>
	  <td>Second Name</td>
	  <td><input type="text" id="second_name"></td>
	</tr>
	<tr>
	  <td>Last Name</td>
	  <td><input type="text" id="last_name"></td>
	</tr>
	<tr>
	  <td>Passport Id</td>
	  <td><input type="text" id="passport_id"></td>
	</tr>
	<tr>
	  <td>E-mail</td>
	  <td><input type="text" id="email"></td>
	</tr>
	<tr>
	  <td>Password</td>
	  <td><input type="password" id="password"></td>
	</tr>
	<tr>
	  <td></td>
	  <td><button type="button" id="send">Register</td>
	</tr>
	</table>
</fieldset>
<script type="text/javascript">
window.onload = function() {
    var register = {};

    var send = document.getElementById("send");
    send.addEventListener("click", function() {
        var elem = document.getElementById("first_name");
        register["firstName"] = elem.value;
		var elem = document.getElementById("second_name");
        register["secondName"] = elem.value;
		var elem = document.getElementById("last_name");
        register["lastName"] = elem.value;
		var elem = document.getElementById("passport_id");
        register["passportId"] = elem.value;
        var elem = document.getElementById("email");
        register["email"] = elem.value;
        var elem = document.getElementById("password");
        register["password"] = elem.value;
        sendData(register);
	})
}

function sendData(data) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "register/addNewUser", true);
    xhr.setRequestHeader("Content-type", "application/json; charset=utf-8");
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
			if (xhr.status == 200) {
				console.log("success");
				window.location.href = "./index";
			} else {
				console.log("errors");
			}
        }
    }
    var json = JSON.stringify(data);
    xhr.send(json);
}
</script>
</body>
</html>
