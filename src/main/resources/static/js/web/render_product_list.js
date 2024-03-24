const divContainer = document.querySelector(".shz-product-content");
const divRowBoxList = document.querySelectorAll(".shz-row-box");

// Ẩn tất cả các .shz-row-box hiện có trước khi thêm các nhóm mới
divRowBoxList.forEach((divRowBox) => {
  divRowBox.style.display = "none";
});

divRowBoxList.forEach((divRowBox) => {
  const title = divRowBox.querySelector(".shz-header-title");
  const listItems = Array.from(divRowBox.querySelectorAll(".product-list a#p-item"));

  let currentProductList = divRowBox.querySelector(".product-list");

  while (listItems.length > 6) {
    const newProductList = document.createElement("div");
    newProductList.classList.add("product-list");

    const itemsToAdd = listItems.splice(6, listItems.length - 6);
    itemsToAdd.forEach((item) => {
      newProductList.appendChild(item);
    });

    currentProductList.insertAdjacentElement("afterend", newProductList);
    currentProductList = newProductList;
  }
});

// Hiển thị tất cả các .shz-row-box sau khi đã thêm các nhóm mới
divRowBoxList.forEach((divRowBox) => {
  divRowBox.style.display = "block";
});

// Xử lý nếu item < 6
const productItems = document.querySelectorAll('.product-list a');
const productContainer = document.querySelectorAll('.product-list');

/*console.log(productContainer)*/
for (let i = 0; i < productContainer.length; i++) {
	/*console.log(productContainer[i].childNodes)*/
	if (productContainer[i].childNodes.length < 6) {
		// productContainer[i].classList.add('flex-start');
		for (let j = 0; j < 6; j++) {
			productContainer[i].appendChild(document.createElement("a"))
		}
	}
}


const px = document.querySelectorAll(".shz-row-box .product-list");
console.log(px[3].querySelectorAll("a").length);

for (let i = 0; i < px.length; i++) {
  if (px[i].querySelectorAll("a").length < 6) {
    for (let j = px[i].querySelectorAll("a").length; j < 6; j++) {
      var a = document.createElement("a");
      px[i].appendChild(a);
    }
  }
}