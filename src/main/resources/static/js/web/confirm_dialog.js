var modala = document.getElementById("dialogOverlay");

document.getElementById("dialogOverlay").addEventListener("click", function(e) {
	if (e.target === modala) {
		document.getElementById("dialogOverlay").style.display = 'none'
		//document.querySelector(".shz__nav").style.display='block';
	}
})

function showConfirmationDialog(event) {
	event.preventDefault();
	//document.querySelector(".shz__nav").style.display='none';
	const deleteLink = event.currentTarget;
	const itemId = deleteLink.getAttribute('data-id');
	const dialogOverlay = document.querySelector('.dialog-overlay');
	const confirmDeleteBtn = document.getElementById('confirm-delete');
	const cancelDeleteBtn = document.getElementById('cancel-delete');

	const itemIName = deleteLink.getAttribute('data-name');
	var txtMsg = document.getElementById('txt-msg');
	/*console.log(itemId);
	console.log(itemIName);*/
	txtMsg.innerText = "Bạn có chắc muốn xóa: " + itemIName;
	// Update the delete button's href attribute with the item's ID
	confirmDeleteBtn.setAttribute('href', deleteLink.href);
	// Show the dialog
	dialogOverlay.style.display = 'flex';
}

function showDialog() {
	this.preventDefault();

	var txtMsg = document.getElementById('txt-msg');
	/*console.log(itemId);
	console.log(itemIName);*/
	txtMsg.innerText = "Sản phẩm bị quá số lượng sẽ được đặt về giá trị tối đa: ";
	// Update the delete button's href attribute with the item's ID
	confirmDeleteBtn.textContent="OK"
	// Show the dialog
	dialogOverlay.style.display = 'flex';
}


function confirmDelete(e){
	e.preventDefault();
	const dialogOverlay = document.querySelector('.dialog-overlay');
	const confirmDeleteBtn = document.getElementById('confirm-delete');
	const deleteUrl = confirmDeleteBtn.getAttribute('href'); 
	window.location.href = deleteUrl;
	dialogOverlay.style.display = 'none';
	//document.querySelector(".shz__nav").style.display='block';
}

function cancelDelete(e){
	const dialogOverlay = document.querySelector('.dialog-overlay');
	dialogOverlay.style.display = 'none';
	//document.querySelector(".shz__nav").style.display='block';
}