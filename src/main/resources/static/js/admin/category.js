document.getElementById("close").addEventListener("click", function() {
	// event.preventDefault();
	document.getElementById("modal").style.display = 'none'
})

function openModal(action, button, ID) {

	if (action === 'edit') {
		document.getElementById("titleForm").textContent = 'Chỉnh sửa danh mục';
		document.getElementById("btnDanhMuc").value = 'Lưu';
		var tenDanhMucValue = button.parentNode.parentNode.querySelector('.tendanhmuc').textContent;
		var tieuDeValue = button.parentNode.parentNode.querySelector('.tieude').textContent;
		var hinhAnh = button.parentNode.parentNode.querySelector(".hinhanh");
		var hinhAnhSrc = hinhAnh.querySelector("img").src;
		document.querySelector('#tenDanhMuc').value = tenDanhMucValue;
		document.querySelector('#tieuDe').value = tieuDeValue;
		document.querySelector('#hinhAnh').src = hinhAnhSrc;
		document.querySelector('#idDanhMuc').value = ID;
		var valueSelect = button.closest("tr").querySelector(".trangthai").getAttribute("data-value");
		console.log(valueSelect)
		document.querySelector("#trangThai").value = valueSelect
	}
	if (action === 'new') {
		document.getElementById("titleForm").textContent = 'Thêm mới danh mục';
		document.getElementById("btnDanhMuc").value = 'Thêm';
		document.querySelector('#tenDanhMuc').value = '';
		document.querySelector('#tieuDe').value = '';
		document.querySelector('#hinhAnh').src = '';
		document.querySelector('#idDanhMuc').value = '';
	}
	console.log(action)
	document.getElementById("modal").style.display = 'block'
}

function changeIMG(input) {
	var file = input.files[0];
	var previewImage = document.getElementById('hinhAnh');

	if (file) {
		var fileExtension = getFileExtension(file.name);
		var allowedExtensions = ['jpg', 'jpeg', 'png', 'gif'];
		var maxSizeInBytes = 2 * 1024 * 1024; // 2MB

		if (allowedExtensions.includes(fileExtension)) {
			if (file.size <= maxSizeInBytes) {
				var reader = new FileReader();

				reader.onload = function(e) {
					var imageSrc = e.target.result;
					previewImage.src = imageSrc;
				}

				reader.readAsDataURL(file);
			} else {
				alert('Kích thước tệp hình ảnh vượt quá 2MB.');
				input.value = '';
			}
		} else {
			alert('Chỉ chấp nhận tệp hình ảnh có phần mở rộng là JPG, JPEG, PNG hoặc GIF.');
			input.value = '';
		}
	}
}

function getFileExtension(filename) {
	return filename.split('.').pop().toLowerCase();
}

var modal = document.getElementById("modal");

document.getElementById("modal").addEventListener("click", function(e) {
	if (e.target === modal) {
		document.getElementById("modal").style.display = 'none'
	}
})



document.querySelector("#modal.modal-category form").addEventListener("submit", async function(e) {
	e.preventDefault()
	var modal = document.querySelector("#modal form");
	var img = modal.querySelector("#hinhAnh");
	var imgSrc = img.getAttribute("src");
	var id = document.querySelector("#modal.modal-category form #idDanhMuc").value;
	var title = document.querySelector("#modal.modal-category form #tieuDe").value;
	if (imgSrc == null || imgSrc == '') {
		console.log("ảnh trống")
		document.querySelector("#modal #modalCategoryMessage").textContent = "Ảnh trống";
		document.querySelector("#modal #modalCategoryMessage").classList.add("message-warrning")
	} else {
		
		if(id === ''){
			id = 0
		}
	
		const check = await checkCategoryTitle(id, title)
			
		if(check){
			document.querySelector("#modal #modalCategoryMessage").textContent = "Tiêu đề bị trùng";
			document.querySelector("#modal #modalCategoryMessage").classList.add("message-warrning")
		}else{
			this.submit()
		}
	}

})

async function checkCategoryTitle(id, title){
	const response = await fetch(`${portUrl}/api-admin/checkCategoryTitle?id=${id}&title=${title}`)
	const data = await response.json()
	return data
}

