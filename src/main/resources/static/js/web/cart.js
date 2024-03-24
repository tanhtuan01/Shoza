window.onload = function() {

	var searchCartParams = getURLParameter('check-out-payment-success');
	removeParameterFromUrl('check-out-payment-success')
	if (searchCartParams.length > 10) {
		shozaNotify(`Đặt hàng thành công. Mã đơn hàng của bạn là: ${searchCartParams}`, "Tiếp tục mua !")
		searchCartParams.delete('check-out-payment-success');
		console.log("check-out-payment-success")
	}
}

var headCheckBox = document.getElementById("headCheckBox")
var totalPrice = 0;
var totalItem = 0;
headCheckBox.addEventListener('change', function() {
	var allCb = document.querySelectorAll("input[name='cbcartitem']")
	var isChecked = headCheckBox.checked
	totalPrice = 0
	totalItem = 0
	allCb.forEach(function(cb) {

		cb.checked = isChecked
		if (cb.checked) {
			var price = parseInt(cb.getAttribute("data-price"))
			var quantity = parseInt(cb.parentNode.parentNode.querySelector("#quantityInCartItem").value)
			var total = price * quantity;
			totalPrice += total
			totalItem += quantity

		}
	})

	setToCheckOut()
	getQuantity()
})

function getQuantity() {
	var allCb = document.querySelectorAll("input[name='cbcartitem']")
	var sumQ = 0;
	allCb.forEach(function(cb) {

		var quantity = parseInt(cb.parentNode.parentNode.querySelector("#quantityInCartItem").value)

		sumQ += quantity



	})
	document.getElementById('totalQUANTITY').textContent = sumQ
}


var items = []
var data = []
function checkOut(e) {

	const paymentValue = document.querySelector('input[name="payment"]:checked').value;
	console.log(paymentValue)

	var allCb = document.querySelectorAll("input[name='cbcartitem']:checked")
	console.log(allCb)
	var orderName = document.getElementById("orderInfoName").value
	var orderPhone = document.getElementById("orderInfoPhone").value
	var orderAddress = document.getElementById("orderInfoAddress").value
	if (orderName === '') {
		document.getElementById("orderInfoName").classList.add('border-red')
		e.preventDefault()
	}
	if (orderName.trim().length > 0) {
		document.getElementById("orderInfoName").classList.remove('border-red')
	}
	if (orderPhone === '') {
		document.getElementById("orderInfoPhone").classList.add('border-red')
		e.preventDefault()
	}
	if (orderPhone.trim().length > 0) {
		document.getElementById("orderInfoPhone").classList.remove('border-red')
	}
	if (orderAddress === '') {
		document.getElementById("orderInfoAddress").classList.add('border-red')
		e.preventDefault()
	}
	if (orderAddress.trim().length > 0) {
		document.getElementById("orderInfoAddress").classList.remove('border-red')
	}
	if (allCb.length < 1) {
		var h2t = document.getElementById("cartDialog").querySelector("h2")
		h2t.textContent = "Chưa chọn sản phẩm"
		var pe = document.getElementById("cartDialog").querySelector("p")
		pe.textContent = "Bạn cần chọn tối thiểu 1 sảm phẩm để thanh toán"
		cartDialog.style.display = "block";
		e.preventDefault()
	}


	allCb.forEach(function(cb) {

		var price = parseInt(cb.getAttribute("data-price"))
		var product = parseInt(cb.getAttribute("data-product"))
		var quantity = parseInt(cb.parentNode.parentNode.querySelector("#quantityInCartItem").value)
		var item = { "product": product, "quantity": quantity, "price": price }
		items.push(item)

	})
	//console.log(JSON.stringify(items))

	//console.log(JSON.stringify(data))

	/*	var xmlhttp = new XMLHttpRequest()
		xmlhttp.onreadystatechange = function(){
			if (this.readyState == 4) {
				if (this.status == 200) {
					var results = JSON.parse(this.responseText);
					
				}
			}
		}
		var queryParam = encodeURIComponent(JSON.stringify(items));
		xmlhttp.open('GET',`${portUrl}/shoza/my-cart/checkout?data=${queryParam}`, true)
		xmlhttp.send()*/

	var odr = { "name": orderName, "phone": orderPhone, "address": orderAddress }
	data.push(odr)

	var itemsParam = encodeURIComponent(JSON.stringify(items));
	var dataParam = encodeURIComponent(JSON.stringify(data));

	//toast({title: 'Đặt hàng thành cong',  type:'success', message: 'Đơn hàng của bạn đã được gửi đi', duration: 2500})


	/*setTimeout(function() {
		window.location.href = `${portUrl}/shoza/my-cart/checkout?items=${itemsParam}&data=${dataParam}`
	}, 2000)*/


	if (paymentValue == 1) {
		shozaNotify("Đặt hàng thành công", "Tiếp tục mua !")
		document.querySelector("#shozaNotify #cancel-delete").addEventListener("click", function() {
			window.location.href = `${portUrl}/shoza/my-cart/checkout?items=${itemsParam}&data=${dataParam}`
		})
	}
	if (paymentValue == 0) {
		console.log("ONLINE")
		window.location.href = `${portUrl}/pay/get-order?item=${itemsParam}&info=${dataParam}`
	}

	items = []
	data = []
	totalItem = 0
	totalPrice = 0


}

function closeCartDialog() {
	document.getElementById("cartDialog").style.display = 'none'
}

function setToCheckOut() {

	var allCb = document.querySelectorAll("input[name='cbcartitem']:checked")

	if (allCb.length > 0) {

		document.getElementById("tmpItem").textContent = `Tạm tính (${totalItem} sản phẩm)`
		document.getElementById("tmpPrice").textContent = totalPrice + 'đ'
		document.getElementById("tmpTotalPrice").textContent = totalPrice + 'đ'

		getQuantity()
	}
	else {
		document.getElementById("tmpItem").textContent = `Tạm tính (0 sản phẩm)`
		document.getElementById("tmpPrice").textContent = '0đ'
		document.getElementById("tmpTotalPrice").textContent = '0đ'

		getQuantity()
	}

}

function cbChange(e) {
	//console.log(e)
	var price = parseInt(e.getAttribute("data-price"))
	//console.log(price)
	var iinput = parseInt(e.parentNode.parentNode.querySelector("#quantityInCartItem").value)
	var total = 0;
	total = price * iinput
	if (e.checked) {
		totalPrice += total
		totalItem += iinput
	} else {
		totalPrice -= total
		totalItem -= iinput
	}
	setToCheckOut()
	//console.log(totalPrice)
}

function deleleItemSelected(event) {
	event.preventDefault()
	var cb = document.querySelectorAll("input[name='cbcartitem']:checked");
	console.log(cb)
}

var cartDialog = document.getElementById("cartDialog");

var ipE = document.querySelectorAll("#quantityInCartItem")


for (let i = 0; i < ipE.length; i++) {

	var ivalue = parseInt(ipE[i].value);
	var imax = parseInt(ipE[i].getAttribute("data-max"))
	var cid = parseInt(ipE[i].getAttribute("data-id"))
	var error = 0;
	/*console.log(typeof ivalue)
	console.log(typeof imax)
	console.log(typeof cid)*/
	if (ivalue > imax) {
		ipE[i].value = imax
		error = 1;
		var xmlhttp = new XMLHttpRequest()
		xmlhttp.onreadystatechange = function() {
			if (this.readyState == 4) {
				if (this.status == 200) {
					var results = JSON.parse(this.responseText);

				}
			}
		}
		xmlhttp.open('GET', `${portUrl}/webs/cart-quantity?id=${cid}&q=${ivalue}`, true)
		xmlhttp.send();

	}

	if (error > 0) {

		var h2t = document.getElementById("cartDialog").querySelector("h2")
		h2t.textContent = "Vượt quá số lượng"
		var pe = document.getElementById("cartDialog").querySelector("p")
		pe.textContent = "Sản phẩm đã được đặt lại số lượng tối đa"
		cartDialog.style.display = "block";


	}
}




function upCartItem(e, id) {
	event.preventDefault()
	xId = id;
	var pNode = e.parentNode
	var inputQuantity = parseInt(pNode.querySelector("#quantityInCartItem").value);
	var maxQuantity = parseInt(pNode.querySelector("#quantityInCartItem").getAttribute("data-max"));
	var price = parseInt(pNode.querySelector("#quantityInCartItem").getAttribute("data-price"))
	console.log('id' + id)
	if (inputQuantity <= maxQuantity - 1) {
		var allCb = document.querySelectorAll("input[name='cbcartitem']:checked");
		inputQuantity = inputQuantity + 1;
		/*totalPrice += price
					totalItem += 1
					setToCheckOut()
					getQuantity()*/
		if (allCb.length > 0) {
			allCb.forEach(function(cb) {
				var getId = parseInt(cb.parentNode.parentNode.querySelector("#quantityInCartItem").getAttribute("data-id"))


				if (getId == id) {
					totalPrice += price
					totalItem += 1
					setToCheckOut()
					getQuantity()
				}
			})
		} else {
			setToCheckOut()
			getQuantity()
		}
		getQuantity()

	}
	pNode.querySelector("#quantityInCartItem").value = inputQuantity;

	var xmlhttp = new XMLHttpRequest()
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			if (this.status == 200) {
				var results = JSON.parse(this.responseText);
				pNode.querySelector("#quantityInCartItem").value = results
			}
		}
	}
	xmlhttp.open('GET', `${portUrl}/webs/up-quantity?id=${id}`, true)
	xmlhttp.send();
}

function lowCartItem(e, id) {
	event.preventDefault()
	xId = id
	var pNode = e.parentNode
	var inputQuantity = parseInt(pNode.querySelector("#quantityInCartItem").value);
	var maxQuantity = parseInt(pNode.querySelector("#quantityInCartItem").getAttribute("data-max"));
	var price = parseInt(pNode.querySelector("#quantityInCartItem").getAttribute("data-price"))

	if (parseInt(inputQuantity) > 1 && parseInt(inputQuantity) <= maxQuantity) {
		inputQuantity = parseInt(inputQuantity) - 1;

		var allCb = document.querySelectorAll("input[name='cbcartitem']:checked");
		if (allCb.length > 0) {
			allCb.forEach(function(cb) {
				var getId = parseInt(cb.parentNode.parentNode.querySelector("#quantityInCartItem").getAttribute("data-id"))


				if (getId == id) {
					totalPrice -= price;
					totalItem -= 1
					setToCheckOut()
					getQuantity()
				}
			})
		} else {
			setToCheckOut()
			getQuantity()
		}
		getQuantity()
	}
	pNode.querySelector("#quantityInCartItem").value = inputQuantity;

	var xmlhttp = new XMLHttpRequest()
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			if (this.status == 200) {
				var results = JSON.parse(this.responseText);
				pNode.querySelector("#quantityInCartItem").value = results
			}
		}
	}
	xmlhttp.open('GET', `${portUrl}/webs/low-quantity?id=${id}`, true)
	xmlhttp.send();
}