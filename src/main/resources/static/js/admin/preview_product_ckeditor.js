var previewData = document.getElementById("previewAddProduct");

var DATAs = previewData.querySelector("#DATA").textContent
var createDiv = document.createElement("div")
var datareplace = DATAs.replace(/"/g, '')
createDiv.classList.add("preview-content-des")
createDiv.innerHTML = datareplace
previewData.appendChild(createDiv)

/*var reloadCalled = false;

function reload() {
    if (!reloadCalled) {
        reloadCalled = true;
        setTimeout(function() {
            window.location.reload();
        }, 3000);
    }
}*/