var productDescription = document.getElementById("productDescription").textContent

var productDescriptionContainer =  document.getElementById("innerDescription")
var productDiv = document.createElement("div")
var buttonViewMore = document.createElement("button")
buttonViewMore.textContent = 'Xem Thêm'
buttonViewMore.classList.add('btn-read-more')
buttonViewMore.id = "readMoreButton"


productDiv.id = "AboutDescription"
productDiv.innerHTML = productDescription
productDescriptionContainer.appendChild(productDiv)
productDescriptionContainer.appendChild(buttonViewMore)


document.getElementById("productDescription").style.display = "none"

var readMoreButton  = document.getElementById("readMoreButton")
var outerDes  = document.getElementById("FullDescription")
var innerDes = document.getElementById("innerDescription")

function checkDesHeight(){
	if(innerDes.scrollHeight > outerDes.clientHeight){
		readMoreButton.style.display = 'block';
		
		innerDes.querySelector("#AboutDescription").classList.add('blurDes')
	}else{
		readMoreButton.style.display = 'none'
		innerDes.querySelector("#AboutDescription").classList.remove('blurDes')
	}
	/*console.log('INNER: ' + innerDes.scrollHeight)
	console.log('OUTER: ' + outerDes.clientHeight)*/
}

readMoreButton.addEventListener('click', function(){
	outerDes.style.maxHeight = outerDes.clientHeight + 800 + "px"
	checkDesHeight()
})

checkDesHeight()