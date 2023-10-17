/*
Function to hide and show menu sub items
*/
document.addEventListener('DOMContentLoaded', function() {

	var subBtns = document.querySelectorAll('.btn_title');
	var openMenu = null;
	var dropdown = null;
	var openMenuDropdown = null;

	subBtns.forEach(function(subBtn) {
		subBtn.addEventListener('click', function() {
			
			var subMenu = this.nextElementSibling;
			
			dropdown = this.querySelector('.dropdown');
			
			if(dropdown.style.transform === 'rotate(0deg)'){
				dropdown.style.transform = 'rotate(90deg)';
			}else{
				dropdown.style.transform = 'rotate(0deg)';
			}
	
			if(openMenu != null){
				if (openMenu.classList[1] !== subMenu.classList[1]) {
						openMenuDropdown.style.transform = 'rotate(0deg)';
						
						openMenu.classList.remove('active');
				}	
			}
			
			if(subMenu.classList[1] == null){
				subMenu.classList.add('active');
			}else{
				subMenu.classList.remove('active');
			}
			
			openMenu = subMenu.classList[1] === null ? null : subMenu;

			openMenuDropdown = dropdown;
			
			subBtns.forEach(function() {
				var menu_list = document.querySelector('.menu_list');
				
				console.log(subMenu.classList[1]);
				
				if(subMenu.classList[1] == null){
					menu_list.style.marginTop = '100px';
				}else{
					menu_list.removeAttribute('style');
				}
				
			});
		});
	});
});