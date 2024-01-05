document.querySelector('.ws-email-config__default-email-details__subcontainer-button__button-change').addEventListener('click', function(){
    var email_text = document.querySelector('.ws-email-config__default-email-details__subcontainer-value__email-text');

    email_text.hidden = true;
    
    var email_input = document.querySelector('.ws-email-config__default-email-details__subcontainer-value__email-input');
    email_input.value = email_text.textContent;
    email_input.hidden = false;

    var change_email_text = document.querySelector('.ws-email-config__default-email-details__subcontainer-button__button-change');
    change_email_text.hidden = true;
    
    options_container = document.querySelector('.ws-email-config__default-email-details__subcontainer-button__options__container').style.display = 'flex';
});

var options = document.querySelectorAll('.ws-email-config__default-email-details__subcontainer-button__options__icon');

options.forEach(function(option){
    option.addEventListener('click', function(){
        var email_text = document.querySelector('.ws-email-config__default-email-details__subcontainer-value__email-text');
        var email_input = document.querySelector('.ws-email-config__default-email-details__subcontainer-value__email-input');
        var change_email_text = document.querySelector('.ws-email-config__default-email-details__subcontainer-button__button-change');
        var options_container = document.querySelector('.ws-email-config__default-email-details__subcontainer-button__options__container');
        var output = document.querySelector('.ws-email-config__output');

        switch (option.classList[1]) {
            case 'confirm':
                if (typeof email_input.value === 'string') {
                    if(email_input.value.length < 51){
                        if(emailValidation(email_input.value)){
                            output.textContent = '';
                    
                            email_text.textContent = email_input.value
                            
                            email_text.hidden = false;
            
                            email_input.hidden = true;
            
                            change_email_text.hidden = false;
            
                            options_container.style.display = 'none'
                        }else{
                            output.textContent = 'Please enter a valid email format';
                        }
                    }else{
                        output.textContent = 'The email is too long';
                    }
                }else{
                    output.textContent = 'Please enter a valid email format';
                }

                sendData(email_text.textContent);
            break;
            case 'cancel':
                output.textContent = '';

                email_text.hidden = false;

                email_input.hidden = true;

                change_email_text.hidden = false;

                options_container.style.display = 'none'
            break;
            default:
    
            break;
        }
    });
});

const FORBIDDEN_TERMINALCHARACTERS = [
    ".",
    ":",
    ",",
    ";",
    "_",
    "-",
    "!",
    "#",
    "@",
    "$",
    "%",
    "&",
    "'",
    "*",
    "+",
    "-",
    "/",
    "=",
    "?",
    "^",
    "`",
    "{",
    "|",
    "}",
    "~"
]

function emailValidation(email) {
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\.[a-zA-Z]{2,4}$/;
    var cont = 0;
    

    for(let badSyntax of FORBIDDEN_TERMINALCHARACTERS){
        
        if(email.substring(0,1).includes(badSyntax) || email.substring(email.lastIndexOf("@"),email.lastIndexOf("@")-1).includes(badSyntax)){
            cont++;
        }
    }

    if(cont == 0){
        if(email.match(emailRegex)){
            return true;
        }
    }
    return false;
}

function sendData(data) {
    var request = new XMLHttpRequest();
    request.open("POST", "EmailsServlet");
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");


    request.onreadystatechange = function () {
        if (request.readyState == 4 && request.status == 200) {
            }else{
		}
    };

    var encodedData = "email=" + encodeURIComponent(data);

    request.send(encodedData);
}
