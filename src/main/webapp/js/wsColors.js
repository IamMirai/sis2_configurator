document.querySelectorAll('input[type=color]').forEach(function (picker) {

    var targetLabel = document.querySelector('label[for="' + picker.id + '"]'), codeArea = document.createElement('span');

    codeArea.innerHTML = picker.value;
    targetLabel.appendChild(codeArea);


    picker.addEventListener('change', function () {
        codeArea.innerHTML = picker.value;
        targetLabel.appendChild(codeArea);
    });
});

document.querySelector('.save-colors__button').addEventListener('click', function(){
    var colors = "";
    document.querySelectorAll('.color-picker__actual-color').forEach(function(father_color){
        colors += father_color.getElementsByTagName("span")[0].innerHTML;
    });
    
    checkData(colors);
});

function checkData(colors) {

    if (typeof colors === 'string') {
        if(colors.length == 21){
            var hexFormat = /^[a-zA-Z0-9#]+$/;
    
            if(hexFormat.test(colors)){
                sendData(colors);
            }
        }
      } else {
      } 
}

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