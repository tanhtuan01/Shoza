function viewFeedback(e, id){
	var trC = e.closest("tr").querySelector(".content").textContent
	document.querySelector("#dialogOverlay").style.display = 'block'
	document.querySelector("#dialogOverlay .dialog-title").textContent = 'Nội dung góp ý'
	document.querySelector("#dialogOverlay #txt-msg").textContent = trC
	document.querySelector("#dialogOverlay .dialog-content").classList.add('feedback-content')
	document.querySelector("#dialogOverlay .dialog-buttons").classList.add('feedback-button')
	document.querySelector("#dialogOverlay #confirm-delete").style.display = 'none'
	document.querySelector("#dialogOverlay #cancel-delete").textContent = 'Đóng'
	document.querySelector("#dialogOverlay #txt-msg").style.textAlign = 'left'
	
	var xmlHttp = new XMLHttpRequest()
	xmlHttp.onreadystatechange = function(){
		if(this.readyState == 4){
			if(this.status == 200){
				e.closest("tr").classList.add('seen')
			}
		}
	}
	xmlHttp.open('GET', `${portUrl}/admin/feedback/read-one?id=${id}`, true)
	xmlHttp.send()
} 