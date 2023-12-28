//This function sends data to insert to Servlet using methond POST
function sendData(data) {
    var request = new XMLHttpRequest();
    request.open("POST", "wsColorsServlet");
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");


    request.onreadystatechange = function () {
        if (request.readyState == 4 && request.status == 200) {
            }else{
		}
    };

    var encodedData = "colors=" + encodeURIComponent(data);

    request.send(encodedData);
}

//This function sends data to remove to Servlet using methond DELETE
function sendDataToDelete(data) {
    var url = "LanguagesServlet?languageToRemove=" + encodeURIComponent(data);
    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", url);

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            console.log(xhr.responseText);
        }else{
			console.log('ERROR!');
		}
        location.reload();
    };
    xhr.send();
}