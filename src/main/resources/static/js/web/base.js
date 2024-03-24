const portUrl = "http://localhost:1481"

function checkENumber(e) {
	if (e.key == 'e' || e.key == 'E') {
		e.preventDefault();
	}
}

function searchInput() {
	setTimeout(function() {
		var inputText = document.getElementById("textSeach").value
		if (inputText.trim().length > 0) {
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.onreadystatechange = function() {
				if (this.readyState == 4) {
					if (this.status == 200) {
						var result = JSON.parse(this.responseText);
						var Ul = document.querySelector(".search-input-option");
						Ul.innerHTML = ''; // Xóa các phần tử cũ trong danh sách trước khi thêm mới

						for (var i = 0; i < result.length; i++) {
							var name = result[i].productName;
							var title = result[i].productTitle;
							var img = result[i].productImage;
							
							var Li = document.createElement("li");
							var a = document.createElement("a");
							a.setAttribute('href', `${portUrl}/shoza/product/${title}`);
							a.textContent = name;

							var Img = document.createElement("img");
							Img.setAttribute('src', `/img/web/product/${img}`);

							Li.appendChild(Img);
							Li.appendChild(a);
							Ul.appendChild(Li);
							
							if(i == 4){
								break;
							}
						}
					}
				}
			};
			xmlhttp.open('GET', `${portUrl}/shoza/search-by?product=${inputText}`, true);
			xmlhttp.send();
			document.getElementById("searchInputBox").style.display = 'block'
			document.getElementById("removeBoxInput").style.display = 'block'
		} else {
			document.querySelector(".search-input-option").innerHTML = ''
			document.getElementById("searchInputBox").style.display = 'none'
			document.getElementById("removeBoxInput").style.display = 'none'
		}
	}, 1000)
}

function removeBoxInput() {
	document.getElementById("searchInputBox").style.display = 'none'
	document.getElementById("removeBoxInput").style.display = 'none'
	document.getElementById("textSeach").value = ''
}

var totalQUANTITY = parseInt(document.getElementById("totalQUANTITY").textContent);

if (totalQUANTITY == 0) {
	document.getElementById("cartBoxEmpty").style.display = 'block'
	document.getElementById("cartNotNull").style.display = 'none'
} else {
	document.getElementById("cartNotNull").style.display = 'block'
	document.getElementById("cartBoxEmpty").style.display = 'none'
}

function closeNotify (){
	document.querySelector("#shozaNotify").style.display = "none"
}

function shozaNotify(message = "", btntext = ""){
	document.querySelector("#shozaNotify").style.display = "block"
	document.querySelector("#shozaNotify #txt-msg").textContent = message
	document.querySelector("#shozaNotify #cancel-delete").textContent = btntext
}


var shozaNotifyDiv = document.querySelector("#shozaNotify")
document.querySelector("#shozaNotify").addEventListener('click', function(e){
	if(e.target == shozaNotifyDiv){
		shozaNotifyDiv.style.display ='none'
	}
})


function getURLParameter(name) {
  name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
  var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
  var results = regex.exec(location.search);
  return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

function removeParameterFromUrl(parameterName) {
  var currentUrl = window.location.href;

  // Xây dựng biểu thức chính quy để tìm tham số cần xóa
  var regex = new RegExp('[\\?&]' + parameterName + '=[^&]+', 'g');

  // Xóa tham số từ URL
  var newUrl = currentUrl.replace(regex, '');

  // Thay thế URL hiện tại bằng URL mới không có tham số
  history.replaceState(null, '', newUrl);
}

var orderCode  =document.getElementById("orderCode")

orderCode.addEventListener('input', function(){
	var orderCodeValue = orderCode.value
	if(orderCodeValue.trim().length > 0){
		document.querySelector("#link-item-order .item-order-box").classList.add("show")
	}else{
		
			document.querySelector("#link-item-order .item-order-box").classList.remove("show")
	
	}
})

var orderPhone  =document.getElementById("orderPhone")

orderPhone.addEventListener('input', function(){
	var orderPhoneValue = orderPhone.value
	if(orderPhoneValue.trim().length > 0){
		document.querySelector("#link-item-order .item-order-box").classList.add("show")
	}else{
		
			document.querySelector("#link-item-order .item-order-box").classList.remove("show")
	
	}
})

