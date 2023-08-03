function HidePassword(){
	var input_password = document.getElementById('input_password');
	
	if(input_password.type === 'password'){
		input_password.type = 'text';
	}else if(input_password.type === 'text'){
		input_password.type = 'password';
	}
}