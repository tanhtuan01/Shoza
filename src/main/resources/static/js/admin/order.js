function updateOrderStatus(btn, orderId) {
	console.log(orderId)
	var status = parseInt(btn.closest("tr").querySelector("#orderStatus").value)


	var xmlhttp = new XMLHttpRequest()
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			if (this.status == 200) {
				console.log('Cập nhật thành công')
				toast({ title: "Cập nhật thành công", type: "success", message: "Cập nhật đơn hàng thành công", duration: 2500 })
				if (status == 3) {
					btn.classList.add('hide')
				}
			}
		}
	}
	xmlhttp.open('GET', `${portUrl}/admin/order/updateOrderStatus?oid=${orderId}&ostt=${status}`, true)
	xmlhttp.send()
}

function openOrderDetail(id) {
	
	document.querySelector("#dialogOverlay").style.display = 'block'
	document.querySelector("#dialogOverlay .dialog-title").textContent = 'Chi tiết đặt hàng'
	document.querySelector("#dialogOverlay #txt-msg").textContent = "Danh sách"
	document.querySelector("#dialogOverlay .dialog-content").classList.add('order-detail-content')
	document.querySelector("#dialogOverlay .dialog-buttons").classList.add('order-detail-button')
	document.querySelector("#dialogOverlay #confirm-delete").style.display = 'none'
	document.querySelector("#dialogOverlay #cancel-delete").textContent = 'Đóng'
	document.querySelector("#dialogOverlay #txt-msg").style.textAlign = 'left'
	console.log(id)
	var ulExists = document.querySelector('#dialogOverlay ul')
	if(ulExists){
		ulExists.remove()
	}
	var xmlhttp = new XMLHttpRequest()
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			if (this.status == 200) {
				var result = JSON.parse(this.responseText)
				console.log(result)
				
				
				var Ul = document.createElement('ul')
				
				for (let i = 0; i < result.length; i++) {
					var Li = document.createElement('li')
					var div = document.createElement('div')
					div.classList.add('detail-item')
					var divLeft = document.createElement('div')
					divLeft.classList.add('detail-left')
					var divRight = document.createElement('div')
					divRight.classList.add('detail-right')
					
					var productName = document.createElement("p")
					productName.textContent = result[i].productName
					
					var pQuantity = document.createElement("p")
					pQuantity.textContent = "Số lượng: " + result[i].quantity
					
					var pTotalPrice = document.createElement("p")
					pTotalPrice.textContent = "Đơn giá: " + result[i].totalPrice.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
					
					var img = document.createElement('img')
					img.src = '/img/web/product/' + result[i].image
					
					divRight.appendChild(productName)
					divRight.appendChild(pQuantity)
					divRight.appendChild(pTotalPrice)
					divLeft.appendChild(img)
					div.appendChild(divLeft)
					div.appendChild(divRight)
					Li.appendChild(div)
					Ul.appendChild(Li)
					document.querySelector("#dialogOverlay .dialog-content").appendChild(Ul)
				}

			}
		}
	}
	xmlhttp.open('GET', `${portUrl}/admin/order-detail/order-detail-by?id=${id}`)
	xmlhttp.send()
}

var dayOption = document.querySelector(".order-option form #day")
for(let i = 1; i <= 31; i ++){
	var option = document.createElement("option")
	option.value = i
	option.text= i
	dayOption.appendChild(option)
}

var monthOption = document.querySelector(".order-option form #month")
for(let i = 1; i <= 12; i ++){
	var option = document.createElement("option")
	option.value = i
	option.text= i
	monthOption.appendChild(option)
}

var currentTime = new Date();
var year = currentTime.getFullYear();
var years = year + 15;

var yearOption = document.querySelector(".order-option form #year");
for (let i = year; i <= years; i++) {
  var option = document.createElement("option");
  option.value = i;
  option.text = i;
  yearOption.appendChild(option);
}

function updateDayOptions() {
  var selectedMonth = monthOption.value;
  var selectedYear = yearOption.value;
  var daysInMonth = new Date(selectedYear, selectedMonth, 0).getDate();

  // Xóa tất cả các tùy chọn ngày hiện có
  while (dayOption.firstChild) {
    dayOption.removeChild(dayOption.firstChild);
  }

  // Tạo danh sách tùy chọn ngày mới
  for (let i = 1; i <= daysInMonth; i++) {
    var option = document.createElement("option");
    option.value = i;
    option.text = i;
    dayOption.appendChild(option);
  }
}

// Lắng nghe sự kiện thay đổi tháng và cập nhật danh sách ngày
monthOption.addEventListener("change", updateDayOptions);
yearOption.addEventListener("change", updateDayOptions);

// Khởi tạo danh sách ngày ban đầu khi trang được tải
updateDayOptions();