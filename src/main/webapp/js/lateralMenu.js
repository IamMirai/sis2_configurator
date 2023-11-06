/*
Function to hide and show menu sub items
*/
document.addEventListener('DOMContentLoaded', function() {
	
	var infoBtns = document.querySelectorAll('.info');

	var tooltip = null;
	var openedTooltip = null;
	
	infoBtns.forEach(function(infoBtn) {
		infoBtn.addEventListener('click', function(){
			
			var menuItem_disabled = infoBtn.parentNode;
			
			tooltip = menuItem_disabled.querySelector('.tooltip');

			if(openedTooltip != null && openedTooltip.classList[1] !== tooltip.classList[1]){
				openedTooltip.classList.remove('visible');
			}
			
			if(tooltip.classList[1] == null){
				tooltip.classList.add('visible');
			}else{
				tooltip.classList.remove('visible');
			}
			openedTooltip = tooltip.classList[1] === null ? null : tooltip;
		});
	});

	var subBtns = document.querySelectorAll('.btn_title');
	var openedMenu = null;
	var dropdown = null;
	var openMenuDropdown = null;

	subBtns.forEach(function(subBtn) {
		subBtn.addEventListener('click', function() {
			
			var subMenu = this.nextElementSibling;
			
			console.log(subMenu);
			
			dropdown = this.querySelector('.dropdown');
			
			if(dropdown.style.transform === 'rotate(0deg)'){
				dropdown.style.transform = 'rotate(90deg)';
			}else{
				dropdown.style.transform = 'rotate(0deg)';
			}
	
			if(openedMenu != null){
				if (openedMenu.classList[1] !== subMenu.classList[1]) {
						console.log("He entrado 1");
						openMenuDropdown.style.transform = 'rotate(0deg)';
						
						openedMenu.classList.remove('active');
						
						if(tooltip != null){
							tooltip.classList.remove('visible');
						}
				}	
			}
			
			if(subMenu.classList[1] == null){
				console.log("He entrado 2");
				subMenu.classList.add('active');
			}else{
				console.log("He entrado 3");
				subMenu.classList.remove('active');
				if(tooltip != null){
					tooltip.classList.remove('visible');
				}
			}
			
			openedMenu = subMenu.classList[1] === null ? null : subMenu;
			
			console.log(openedMenu);

			openMenuDropdown = dropdown;
			
			subBtns.forEach(function() {
				var menu_list = document.querySelector('.menu_list');
				
				if(subMenu.classList[1] == null){
					menu_list.style.marginTop = '100px';
				}else{
					menu_list.removeAttribute('style');
				}
				
			});
		});	
	});
});