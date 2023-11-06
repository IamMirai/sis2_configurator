var languages = document.querySelectorAll('.language');

languages.forEach(function(language){
	language.addEventListener('click', function(){
		sendData(language.textContent.substring(0,3).toLowerCase());
	});
});

function sendData(data) {
	
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "LoginServlet");
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            console.log(xhr.responseText);
            location.reload();
            
        }else{
			console.log('ERROR!');
		}
    };

    xhr.send("language=" + data);
}

