function validateMinLength(elementId, minLength)
{
	var element = document.getElementById(elementId);
	
	if (element.value.length >= minLength)
		return true;
	
	return false;
}


function validateConfirmPassword(passwordId, confirmPasswordId, minLength)
{
	var c = document.getElementById(confirmPasswordId);
	var p = document.getElementById(passwordId);
	
	if (c.value == p.value)
		return validateMinLength(passwordId, minLength);
	
	return false;
}

function validateEmail(emailId)
{ 
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	var email = document.getElementById(emailId);
    
    return re.test(email.value);
} 


function setVisibility(element, visibility)
{
	if (visibility == true)
		$(element).fadeIn('slow');
	else
		$(element).fadeOut('slow');
}

function validateUsername(username)
{
	if (username.length >= 3) {
		jsRoutes.controllers.UserController.usernameAvailable(username).ajax({
			success: function(data) {
				if (data == 'false')
					$('#usernameOk').hide();
			},
			error: function() {
				$('#usernameOk').hide();
			}
		});
	}
	else
		$('#usernameOk').hide();
}

validateUsername(document.getElementById('username').value);
$('#username').bind('input', function() {
		if (validateMinLength('username', 3)) {
			jsRoutes.controllers.UserController.usernameAvailable(document.getElementById('username').value).ajax({
    			success: function(data) {
    				if (data == 'true')
						setVisibility('#usernameOk', true);
					else
						setVisibility('#usernameOk', false);
				},
				error: function(data) {
						setVisibility('#usernameOk', false);
				}
			})
		}
		else {
			setVisibility('#usernameOk', false);
		}
});

if (!validateMinLength('password', 6))
	$('#passwordOk').hide();

$('#password').bind('input', function() { 
	setVisibility('#passwordOk', validateMinLength('password', 6));
	setVisibility('#confirmPasswordOk', validateConfirmPassword('password', 'confirmPassword', 6));
});

if (!validateConfirmPassword('password', 'confirmPassword', 6))
	$('#confirmPasswordOk').hide();

$('#confirmPassword').bind('input', function() { 
	setVisibility('#passwordOk', validateMinLength('password', 6));
	setVisibility('#confirmPasswordOk', validateConfirmPassword('password', 'confirmPassword', 6));
});

if (!validateEmail('email', 6))
	$('#emailOk').hide();

$('#email').bind('input', function() { 
	setVisibility('#emailOk', validateEmail('email'));
});

if (!validateMinLength('name', 1))
	$('#nameOk').hide();

$('#name').bind('input', function() { 
	setVisibility('#nameOk', validateMinLength('name', 1));
});

if(!validateMinLength('surname', 1))
	$('#surnameOk').hide();

$('#surname').bind('input', function() { 
	setVisibility('#surnameOk', validateMinLength('surname', 1));
});

if(!validateMinLength('ssn', 7))
	$('#ssnOk').hide();

$('#ssn').bind('input', function() { 
	setVisibility('#ssnOk', validateMinLength('ssn', 7));
});
