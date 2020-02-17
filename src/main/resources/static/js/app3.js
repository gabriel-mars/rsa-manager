// Dados time
var ary = [];

// Dados para os gráficos
var dados = [];

//Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart'], 'language': 'pt'});

function fillTableReprovado(values){
	for (var i = 0; i < values.length; i++){
		var aux = values[i];
		
		$('#tabelaReprovados').append('<tr>' +
				'<td>' + aux[0] + '</td>' +
				'<td>' + aux[1] + '</td>' +
				'</tr>');	
	}
}

function fillTableAbono(values){
	for (var i = 0; i < values.length; i++){
		var aux = values[i];
		
		$('#tabelaAbonados').append('<tr>' +
				'<td>' + aux[0] + '</td>' +
				'<td>' + aux[2] + '</td>' +
				'</tr>');	
	}
}

function fillTableDetailAbono(values){
	for (var i = 0; i < values.length; i++){
		var aux = values[i];
		
		$('#itens').append('<tr>' +
				'<td>' + aux[0] + '</td>' +
				'<td>' + aux[2] + '</td>' +
				'<td>' + aux[1] + '</td>' +
				'<td>' + aux[3] + '</td>' +
				'</tr>');	
	}
}

function fillTableDetailReprovado(values){
	for (var i = 0; i < values.length; i++){
		var aux = values[i];
		
		$('#itens').append('<tr>' +
				'<td>' + aux[0] + '</td>' +
				'<td>' + aux[1] + '</td>' +
				'<td>' + aux[2] + '</td>' +
				'<td>' + aux[3] + '</td>' +
				'</tr>');	
	}
}

function getDadosAbono() {	
	$("#tabelaAbonados tr").remove();
	
	$.ajax({
		type: "GET",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		beforeSend: function(){
			$(".loader").show();
		},
		url: "/relatorio/dashboard/abonado",
		success: function(dataReturn){
			fillTableAbono(dataReturn);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function getDadosReprovado(){
	let date = document.getElementById('data_inicio').value;
	
	ary = [];
	$("#itens tr").remove();
	
	ary.push({ Data: date });
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		beforeSend: function(){
			$(".loader").show();
		},
		url: "/relatorio/time/reprovado",
		data: JSON.stringify(ary),
		success: function(dataReturn){
			fillTableDetailReprovado(dataReturn);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function getDadosAbonado(){
	let date = document.getElementById('data_inicio').value;
	
	ary = [];
	$("#itens tr").remove();
	
	ary.push({ Data: date });
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/time/abonado",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			fillTableDetailAbono(dataReturn);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function getFalhasSemanal(){
	let selectColab = document.getElementById('colaborador');
	let data_inicial = document.getElementById('data_inicio').value;
	let data_final = document.getElementById('data_fim').value;
	
	let strColab = selectColab.options[selectColab.selectedIndex].value;
	
	ary = [];
	
	ary.push({ DataInicial: data_inicial, DataFinal: data_final, Colab: strColab });
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/falha/semanal/individual",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			// Set a callback to run when the Google Visualization API is loaded.
			google.charts.setOnLoadCallback(drawPie(dataReturn));
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function drawPie(values){
	console.log(values);
}