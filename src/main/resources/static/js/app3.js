function fillTableReprovado(values){
	for (var i = 0; i < values.length; i++){
		var aux = values[i];
		
		$('#tabelaReprovados').append('<tr>' +
				'<td>' + aux[0] + '</td>' +
				'<td>' + aux[1] + '</td>' +
				'</tr>');	
	}
	console.log('Reprovados');
}

function fillTableAbono(values){
	for (var i = 0; i < values.length; i++){
		var aux = values[i];
		
		$('#tabelaAbonados').append('<tr>' +
				'<td>' + aux[0] + '</td>' +
				'<td>' + aux[2] + '</td>' +
				'</tr>');	
	}
	
	console.log('Abonados');
}

$(document).ready(function(){
	var dataTable = [];
	var arrayStr = [];
	
	$("#tabelaReprovados tr").remove();
	
	$.ajax({
		type: "GET",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/dashboard/reprovado",
		success: function(dataReturn){
			fillTableReprovado(dataReturn);
			getDadosAbono();
		}
	});
});

function getDadosAbono() {
	var dataTable = [];
	var arrayStr = [];
	
	$("#tabelaAbonados tr").remove();
	
	$.ajax({
		type: "GET",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/dashboard/abonado",
		success: function(dataReturn){
			fillTableAbono(dataReturn);
		}
	});
}