const portUrl = "http://localhost:1481"

function userShow() {
	var userShow = document.getElementById("user-show");
	userShow.classList.toggle("user-show-active");
}

function toggleMenu() {
	var leftContent = document.getElementById("leftContent");

	leftContent.classList.toggle("toggle");
}



function fullScreen() {
	if (!document.fullscreenElement) {
		if (document.documentElement.requestFullscreen) {
			document.documentElement.requestFullscreen();
		} else if (document.documentElement.mozRequestFullScreen) {
			document.documentElement.mozRequestFullScreen();
		} else if (document.documentElement.webkitRequestFullscreen) {
			document.documentElement.webkitRequestFullscreen();
		} else if (document.documentElement.msRequestFullscreen) {
			document.documentElement.msRequestFullscreen();
		}

	} else {
		if (document.exitFullscreen) {
			document.exitFullscreen();
		} else if (document.mozCancelFullScreen) {
			document.mozCancelFullScreen();
		} else if (document.webkitExitFullscreen) {
			document.webkitExitFullscreen();
		} else if (document.msExitFullscreen) {
			document.msExitFullscreen();
		}

	}
}

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

window.onload = function(){
	var messageParam = getURLParameter('notify');
	removeParameterFromUrl('notify')
	if(messageParam == 'category-success'){
		toast({title:"Lưu thành công", type:"success", message:"Danh mục được lưu thành công",duration: 2500})
	}
	if(messageParam == 'category-delsuccess'){
		toast({title:"Xóa thành công", type:"success", message:"Danh mục đã được xóa",duration: 2500})
	}
	if(messageParam == 'product-addsuccess'){
		toast({title:"Thêm thành công", type:"success", message:"Thêm sản phẩm thành công",duration: 2500})
	}
	if(messageParam == 'product-editsuccess'){
		toast({title:"Chỉnh sửa thành công", type:"success", message:"Sản phẩm đã được chỉnh sửa",duration: 2500})
	}
	if(messageParam == 'product-delsuccess'){
		toast({title:"Xóa thành công", type:"success", message:"Sản phẩm đã được xóa",duration: 2500})
	}
	
}