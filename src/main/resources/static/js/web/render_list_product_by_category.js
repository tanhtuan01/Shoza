const itemss = document.querySelectorAll(".product-by-category .product-list a#p-item");
const divPr = document.querySelectorAll(".product-by-category .product-list");

for (let i = itemss.length; i % 5 !== 0; i++) {
  const emptyItem = document.createElement("a");
  emptyItem.classList.add("empty-item");
  divPr[divPr.length - 1].appendChild(emptyItem);
}