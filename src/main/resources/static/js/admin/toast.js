function toast({title="", type="", message="", duration=3000}){

	const main = document.getElementById("toast");

	if(main){

		var autoRemoveToast = setTimeout(function (){
			main.removeChild(toast);
		},duration + 1000);

		var icons = {
			success: 'fa-solid fa-circle-check',
			warning: 'fa-solid fa-triangle-exclamation',
			error: 'fa-solid fa-ban'
		}

		var icon = icons[type];

		var delay = (duration / 1000).toFixed(2);

		const toast = document.createElement("div");

		toast.onclick = function(e){
			if(e.target.closest('.toast__close')){
				main.removeChild(toast);
				clearTimeout(autoRemoveToast);
			}
		}

		toast.classList.add("toast", `toast--${type}`);

		toast.style.animation = `sideIn 1s ease,sideHide 1s ${delay}s forwards`;

		toast.innerHTML = `
	
			<div class="toast__icon">
				<i class="${icon}"></i>
			</div>
			<div class="toast__body">
				<h3 class="toast__title">${title}</h3>
				<p class="toast__message">${message}</p>
			</div>
			<div class="toast__close">
				<i class="fa-solid fa-xmark"></i>
			</div>		

		`

		main.appendChild(toast);

	}

}