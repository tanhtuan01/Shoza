var fileInput = document.getElementById('selectListImg');
var imageContainer = document.getElementById('listImgSelected');
var totalSizeLimit = 10 * 1024 * 1024; // 10 megabyte
var totalSize = 0;

fileInput.addEventListener('change', function() {
    while (imageContainer.firstChild) {
        imageContainer.removeChild(imageContainer.firstChild);
    }
    var selectedFiles = fileInput.files;

    for (var i = 0; i < selectedFiles.length; i++) {
        var file = selectedFiles[i];

        // Kiểm tra kiểu tệp tin
        if (!file.type.startsWith('image/')) {
            alert('Vui lòng chỉ chọn tệp tin ảnh.');
            // Xóa tệp tin đã chọn
            fileInput.value = '';
            // Xóa hình ảnh đã hiển thị
            while (imageContainer.firstChild) {
                imageContainer.removeChild(imageContainer.firstChild);
            }
            return;
        }

        var reader = new FileReader();

        reader.onload = (function(file) {
            return function(e) {
                var img = document.createElement('img');
                img.src = e.target.result;

                var imageContainerItem = document.createElement('div');
                imageContainerItem.classList.add('img-selected-item');
                imageContainerItem.appendChild(img);

                imageContainer.appendChild(imageContainerItem);

                // Tính tổng kích thước các tệp tin
                totalSize += file.size;

                // Kiểm tra nếu tổng kích thước vượt quá giới hạn
                if (totalSize > totalSizeLimit) {
                    alert('Tổng kích thước các tệp tin vượt quá 10 megabyte.');
                    // Xóa tệp tin đã chọn
                    fileInput.value = '';
                    // Xóa hình ảnh đã hiển thị
                    while (imageContainer.firstChild) {
                        imageContainer.removeChild(imageContainer.firstChild);
                    }
                    // Đặt lại tổng kích thước
                    totalSize = 0;
                    return;
                }
            };
        })(file);

        reader.readAsDataURL(file);
    }
});


function changeImgPR(input) {
    var file = input.files[0];
    var imgSelected = document.getElementById('imgSelected');

    if (file) {
        var fileExtension = getFileExtension(file.name);
        var extensions = ['jpg', 'jpeg', 'png', 'gif'];

        if (extensions.includes(fileExtension)) {
            var reader = new FileReader();

            reader.onload = function(e) {
                var imageSrc = e.target.result;
                imgSelected.src = imageSrc;
            }

            reader.readAsDataURL(file);
        } else {
            alert('Chỉ chấp nhận tệp hình ảnh có phần mở rộng là JPG, JPEG, PNG hoặc GIF.');
            input.value = '';
        }
    }
}


document.getElementById('price').addEventListener('input', calculateDiscountedPrice);
document.getElementById('discount').addEventListener('input', calculateDiscountedPrice);

function calculateDiscountedPrice() {
    var price = parseFloat(document.getElementById('price').value);
    var discount = parseFloat(document.getElementById('discount').value);

    if (!isNaN(price) && !isNaN(discount)) {
        var newPrice = price - (price * (discount / 100));
        document.getElementById('newPrice').value = newPrice.toFixed(0);
    } else {
        document.getElementById('newPrice').value = '';
    }
}

function checkENumber(e) {
    if (e.key == 'e' || e.key == 'E') {
        e.preventDefault();
    }
}

function getFileExtension(filename) {
	return filename.split('.').pop().toLowerCase();
}

document.getElementById("formAddProduct").addEventListener('submit', async function(e){
	e.preventDefault()
	
	var id = document.querySelector("#formAddProduct #ID").value
	
	var title = document.querySelector("#formAddProduct #tieuDe").value
	
	var ACT = document.querySelector("#formAddProduct #ACT").value
	
	
	
	if(ACT === 'add'){
		id = 0	
	}
			const check = await checkProductTitle(id, title)
		if(check){
			toast({title:"Trùng Tiêu Đề", type:"warning", message:"Hãy tùy chỉnh lại tiêu đề, tiêu đề đã bị trùng",duration: 2500})
		}else{
			this.submit()
		}
	
})

async function checkProductTitle(id, title){
	const response = await fetch(`${portUrl}/api-admin/checkProductTitle?id=${id}&title=${title}`)
	const data = await response.json()
	return data
}