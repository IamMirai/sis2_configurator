var btn_hide_show_password = document.querySelector('.btn_hide_show_password');

btn_hide_show_password.addEventListener('click', function() {

	var input_password = document.querySelector('.input_password');

	if (input_password.type === 'password') {
		input_password.type = 'text';
		btn_hide_show_password.textContent = 'Show password';

	} else if (input_password.type === 'text') {
		input_password.type = 'password';
		btn_hide_show_password.textContent = 'Hide password';
	}

});

