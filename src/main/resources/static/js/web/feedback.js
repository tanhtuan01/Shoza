var feedback = document.querySelector("#shozaFeedback textarea")
feedback.addEventListener('click', ()=>{
	feedback.classList.add('focus-textarea')
})
feedback.addEventListener('blur', () => {
  feedback.classList.remove('focus-textarea');
});

var buttonSendFeedback = document.querySelector("#shozaFeedback form button")

feedback.addEventListener('input', ()=>{
	if(feedback.value.trim().length > 0){
		buttonSendFeedback.removeAttribute('disabled')
	}else{
		buttonSendFeedback.setAttribute('disabled', 'disabled');
	}
})


buttonSendFeedback.addEventListener('click', ()=>{
	var content =feedback.value.trim()
	var xmlhttp = new XMLHttpRequest()
	xmlhttp.onreadystatechange = function(){
		if(this.readyState == 4){
			if(this.status == 200){
				shozaNotify('Góp ý của bạn đã được ghi lại.           ! Cảm ơn bạn đã góp ý giúp chúng tôi cải thiện thêm!','Không có gì')
			}
		}
	}
	
	xmlhttp.open('GET',`${portUrl}/shoza/feedback/save?feedback-content=${content}`, true)
	xmlhttp.send()
	feedback.value = ''
	buttonSendFeedback.setAttribute('disabled', 'disabled');
})