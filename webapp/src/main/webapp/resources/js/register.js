$(document).ready(function() {
	$('#password').strengthMeter('progressBar', {
		container : $('#password-progress-bar-container'),
		hierarchy : {
			'0' : 'progress-bar-danger',
			'15' : 'progress-bar-warning',
			'30' : 'progress-bar-success'
		}
	});

	$('.glyphicon-ok').hide();
	$('.glyphicon-remove').hide();

	$('.password-retype, #password').keyup(function() {
		var pwValue = document.querySelector('#password').value;
		var reTypeValue = document.querySelector('.password-retype').value;

		if (reTypeValue !== pwValue) {
			$('.has-feedback').addClass('has-error');
			$('.has-feedback').removeClass('has-success');
			$('.glyphicon-ok').hide();
			$('.glyphicon-remove').show();
		} else {
			$('.has-feedback').addClass('has-success');
			$('.has-feedback').removeClass('has-error');
			$('.glyphicon-ok').show();
			$('.glyphicon-remove').hide();
		}
	});
});

var password = document.querySelector("#password"), confirm_password = document
		.querySelector(".password-retype");

function validatePassword() {
	if (password.value != confirm_password.value) {
		confirm_password.setCustomValidity("Passwords Don't Match");
	} else {
		confirm_password.setCustomValidity('');
	}
}

password.onchange = validatePassword;
confirm_password.onkeyup = validatePassword;