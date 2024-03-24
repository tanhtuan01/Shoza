function openFilter(e) {

}

document.getElementById("left-form-price").addEventListener("submit", function(e) {

    var formPrice = document.getElementById("left-form-price");
    var minPrice = formPrice.querySelector("#minPrice").value;
    var maxPrice = formPrice.querySelector("#maxPrice").value;

    if (isNaN(minPrice) || isNaN(maxPrice)) {
        e.preventDefault();
    } else if (minPrice.trim().length == 0 && minPrice.trim().length == 0) {
        e.preventDefault();
    } else {
        e.submit
    }
})


function searchProduct (){
	var input = document.getElementById("textSeach").value
	if(input.trim().length > 0){
		window.location.href = `${portUrl}/shoza/search?product=${input}`
	}
}