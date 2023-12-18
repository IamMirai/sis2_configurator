document.addEventListener('DOMContentLoaded', function(){
    var list_options = document.querySelectorAll(".list_option");

    list_options.forEach(function(option) {
        option.addEventListener('click', function(){
            switch (option.classList[1]) {
                case 'add':
                    var modal = document.querySelector(".background_modal");
                    modal.style.display = 'block';
                break;
                case 'remove':
                    
                break;
                default:
                break; 
            }
        });
    });

    var languages = document.querySelectorAll(".all_language_list_item");

    var acceptBtn = document.querySelector(".button_accept");

    const selectedClass = 'added';

    languages.forEach(function(language){

        language.addEventListener('click', function(){

            if(language.classList.toggle(selectedClass)){
                language.style.backgroundColor = 'var(--green-color)';
                language.style.color = 'var(--white-color)';
            }else{
                language.style.backgroundColor = 'var(--white-color)';
                language.style.color = 'var(--black-color)';
            }

            var addedlanguages = document.querySelectorAll(".added");

            if(addedlanguages.length > 0){
                acceptBtn.disabled = false;
            }else{
                acceptBtn.disabled = true;
            }
        });
    });

    var languages = document.querySelectorAll(".language_list_item");

    var removeBtn = document.querySelector(".button_remove");

    var addBtn = document.querySelector(".button_add");

    const selectedClass2 = 'removed';

    languages.forEach(function(language){

        language.addEventListener('click', function(){

            if(language.classList.toggle(selectedClass2)){
                language.style.backgroundColor = 'var(--green-color)';
                language.style.color = 'var(--white-color)';
            }else{
                language.style.backgroundColor = 'var(--primary-background-container-color)';
                language.style.color = 'var(--black-color)';
            }

            var removedlanguages = document.querySelectorAll(".removed");

            if(removedlanguages.length > 0){
                addBtn.disabled = true;
                removeBtn.disabled = false;
            }else{
                addBtn.disabled = false;
                removeBtn.disabled = true;
            }
        });
    });

});



var modal_options = document.querySelectorAll(".option");

modal_options.forEach(function(option) {
    option.addEventListener('click', function(){
        switch (option.classList[1]) {
            case 'accept':
                
            break;
            case 'cancel':
                var modal = document.querySelector(".background_modal");
                modal.style.display = 'none';

                var languages = document.querySelectorAll(".all_language_list_item");

                languages.forEach(function(language){
                    language.classList.remove('added');
                    language.style.backgroundColor = 'var(--white-color)';
                    language.style.color = 'var(--black-color)';
                });
            break;
            default:
            break;
        }
    });
});


var acceptBtn = document.querySelector(".accept");

acceptBtn.addEventListener('click', function(){
    var addedLanguages = document.querySelectorAll(".added");
    var array_addedLanguages = [];

    addedLanguages.forEach(function(addedLanguage){
        array_addedLanguages.push(addedLanguage.textContent.trim());
    });

    array_addedLanguages.forEach(function(addedLanguage){
        sendDataToInsert(addedLanguage);
    });
});

function sendDataToInsert(data) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "LanguagesServlet");
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            console.log(xhr.responseText);
        }else{
			console.log('ERROR!');
		}
        location.reload();
    };
    var encodedData = "languageToAdd=" + encodeURIComponent(data);

    xhr.send(encodedData);
}

var removeBtn = document.querySelector(".remove");

removeBtn.addEventListener('click', function() {
    var removedLanguages = document.querySelectorAll(".removed");
    var array_removedLanguages = [];

    removedLanguages.forEach(function(removedLanguage){
        array_removedLanguages.push(removedLanguage.textContent.trim());
    });

    array_removedLanguages.forEach(function(removedLanguage){
        sendDataToDelete(removedLanguage);
    });
});

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
