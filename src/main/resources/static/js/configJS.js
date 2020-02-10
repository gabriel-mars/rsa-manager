var myVar;

function loading() {
	console.log('here');
  myVar = setTimeout(showPage, 3000);
}

function showPage() {
  document.getElementById("loading").style.display = "none";
  document.getElementById("content").style.display = "block";
}