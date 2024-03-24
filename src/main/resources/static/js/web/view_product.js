document.getElementById('productViewImage').setAttribute('Cache-Control', 'no-cache, no-store, must-revalidate');

imageZoom("productViewImage", "ZoomImage");

function imageZoom(imgID, resultID) {
	var img, lens, result, cx, cy;
	img = document.getElementById(imgID);
	result = document.getElementById(resultID);

	/* Tạo ống kính: */
	lens = document.createElement("DIV");
	lens.setAttribute("class", "img-zoom-lens");

	/* Chèn ống kính vào trước hình ảnh: */
	img.parentElement.insertBefore(lens, img);

	/* Tính toán tỷ lệ giữa phần tử kết quả và ống kính: */
	cx = result.offsetWidth / lens.offsetWidth;
	cy = result.offsetHeight / lens.offsetHeight;

	/* Ẩn phần tử kết quả ban đầu: */
	result.style.display = "none";

	/* Thêm sự kiện di chuột vào ống kính và hình ảnh */
	lens.addEventListener("mousemove", moveLens);
	img.addEventListener("mousemove", moveLens);
	lens.addEventListener("mousedown", moveLens);
	lens.addEventListener("touchmove", moveLens);
	img.addEventListener("touchmove", moveLens);
	lens.addEventListener("touchend", moveLens);
	img.addEventListener("touchend", moveLens);
	lens.addEventListener("mouseleave", hideLens);
	img.addEventListener("mouseleave", hideLens);

	function moveLens(e) {
		var pos, x, y;

		/* Ngăn chặn các hành động khác xảy ra khi di chuột qua hình ảnh */
		e.preventDefault();

		/* Hiển thị phần tử kết quả */
		result.style.display = "block";

		lens.style.display = "block";
		/* Lấy vị trí x và y của con trỏ chuột */
		pos = getCursorPos(e);
		x = pos.x - (lens.offsetWidth / 2);
		y = pos.y - (lens.offsetHeight / 2);

		/* Giới hạn vị trí của ống kính */
		if (x > img.width - lens.offsetWidth) {
			x = img.width - lens.offsetWidth;
		}
		if (x < 0) {
			x = 0;
		}
		if (y > img.height - lens.offsetHeight) {
			y = img.height - lens.offsetHeight;
		}
		if (y < 0) {
			y = 0;
		}

		/* Đặt vị trí của ống kính */
		lens.style.left = x + 20 + "px";
		lens.style.top = y + 10 + "px";

		/* Hiển thị phần tử kết quả */
		result.style.backgroundImage = "url('" + img.src + "')";
		result.style.backgroundSize = (img.width * cx) + "px " + (img.height * cy) + "px";
		result.style.backgroundPosition = "-" + (x * cx) + "px -" + (y * cy) + "px";
	}

	function hideLens() {
		/* Ẩn ống kính và phần tử kết quả */
		lens.style.display = "none";
		result.style.display = "none";
	}

	function getCursorPos(e) {
		var a, x = 0,
			y = 0;
		e = e || window.event;
		a = img.getBoundingClientRect();
		x = e.pageX - a.left - window.pageXOffset;
		y = e.pageY - a.top - window.pageYOffset;
		return { x: x, y: y };
	}
}


$(document).ready(function() {
	$('.v-list-img').slick({
		slidesToShow: 6,
		lazyLoad: 'ondemand',
		prevArrow: false,
		nextArrow: false,
		dots: false,
		variableWidth: true,
	});
});


function changeSubImg(e) {
	var productViewImage = document.getElementById("productViewImage");
	productViewImage.src = e.src
}

function lowQuantity() {
	var inputQuantity = document.getElementById("InInputQuantity").value;
	var maxQuantity = parseInt(document.getElementById("InInputQuantity").getAttribute("data-max"));

	if (parseInt(inputQuantity) > 1 && parseInt(inputQuantity) <= maxQuantity) {
		inputQuantity = parseInt(inputQuantity) - 1;
	}
	document.getElementById("InInputQuantity").value = inputQuantity;
}
function upQuantity() {
	var inputQuantity = document.getElementById("InInputQuantity").value;
	var maxQuantity = parseInt(document.getElementById("InInputQuantity").getAttribute("data-max"));

	if (parseInt(inputQuantity) <= maxQuantity - 1) {
		inputQuantity = parseInt(inputQuantity) + 1;
	}
	document.getElementById("InInputQuantity").value = inputQuantity;
}

function checkInputQuantity(e) {
	var inputQuantity = parseInt(document.getElementById("InInputQuantity").value)
	var maxQuantity = parseInt(document.getElementById("InInputQuantity").getAttribute("data-max"));

	if (inputQuantity > maxQuantity) {
		document.getElementById("InInputQuantity").value = maxQuantity
	}
	if (inputQuantity < 1) {
		document.getElementById("InInputQuantity").value = 1
	}
}

async function checkCartBeforeAdd(product, quantity) {
	const response = await fetch(`${portUrl}/shoza/check/checkCartBeforeAdd?id_product=${product}&quantity=${quantity}`);
	const data = await response.json();
	return data;
}

async function btnAddToCart(e, product) {
	e.preventDefault()
	
	var inputQuantity = parseInt(document.getElementById("InInputQuantity").value)
	href = e.target.getAttribute("href")
	var new_href = `${href}?p=${product}&q=${inputQuantity}`

	const check = await checkCartBeforeAdd(product, inputQuantity)
	document.querySelector("#dialogOverlay").style.display = 'block'
	document.querySelector("#dialogOverlay .dialog-title").textContent = ''
	document.querySelector("#dialogOverlay .dialog-content").classList.add('cart-content-dialog')
	if (check) {

		document.querySelector("#dialogOverlay #confirm-delete").style.display = 'none'
		/*window.location.href = new_href*/
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			if (this.readyState == 4) {
				if (this.status == 200) {
					document.querySelector("#dialogOverlay #txt-msg").textContent = 'Thêm vào giỏ hàng thành công'
					document.querySelector("#dialogOverlay #cancel-delete").textContent = 'Đóng'
					document.querySelector("#dialogOverlay #cancel-delete").addEventListener('click', function() {
						window.location = location
						document.querySelector("#dialogOverlay").style.display = 'none'
					})
				} else {
					document.querySelector("#dialogOverlay #txt-msg").textContent = 'Bạn phải đăng nhập trước'

					document.querySelector("#dialogOverlay #cancel-delete").textContent = 'Đóng'
				}
			}
		}
		xmlhttp.open('GET', new_href, true)
		xmlhttp.send()
	}
	else {
		document.querySelector("#dialogOverlay #txt-msg").textContent = 'Vượt quá số lượng cho sản phẩm này'
		document.querySelector("#dialogOverlay #confirm-delete").style.display = 'none'
		document.querySelector("#dialogOverlay #cancel-delete").textContent = 'Đóng'
	}

}
// COMMENT
var cmt = document.querySelector("#comment textarea")
console.log(cmt)
cmt.addEventListener('click', () => {
	cmt.classList.add('focus-textarea')
})
cmt.addEventListener('blur', () => {
	cmt.classList.remove('focus-textarea');
});

var buttonSendCmt = document.querySelector("#comment button")

cmt.addEventListener('input', () => {
	if (cmt.value.trim().length > 0) {
		buttonSendCmt.removeAttribute('disabled')
	} else {
		buttonSendCmt.setAttribute('disabled', 'disabled');
	}
})
