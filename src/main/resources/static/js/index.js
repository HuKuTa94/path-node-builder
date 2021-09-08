// -------------------------------------------------- INITIALIZATION ---------------------------------------------------
let sourceTextArea;
let resultTextArea;

function onload() {
    sourceTextArea = document.getElementById("source-text");
    resultTextArea = document.getElementById("result-text");
}

// -------------------------------------------------- BROWSER METHODS --------------------------------------------------
function pasteTextFromClipboard() {
    navigator.clipboard.readText()
        .then(text => {
            sourceTextArea.value = text;
        })
}

function copyTextToClipboard() {
    navigator.clipboard.writeText(resultTextArea.value);
}

// ---------------------------------------------------- API METHODS ----------------------------------------------------
function sendData() {
    fetch("api/distance-matrix/?isNewVersion=true", {
        method: 'POST',
        body: sourceTextArea.value,
        headers: {'Content-Type': 'text/plain; charset=utf-8'},
    })
    .then(response => {
        response.text().then(text => {
            resultTextArea.value = text;
        })
    })
}
