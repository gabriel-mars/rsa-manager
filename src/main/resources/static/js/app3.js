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

$(document).ready(function(){
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

function getDadosReprovado(){
	let date = document.getElementById('data_inicio').value;
	
	ary = [];
	
	ary.push({ Data: date });
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/time/reprovado",
		data: JSON.stringify(ary),
		success: function(dataReturn){
			google.charts.setOnLoadCallback(drawPieReprovados(dataReturn));
		}
	});
}

function getDadosAbonado(){
let date = document.getElementById('data_inicio').value;
	
	ary = [];
	
	ary.push({ Data: date });
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/time/abonado",
		data: JSON.stringify(ary),
		success: function(dataReturn){
			google.charts.setOnLoadCallback(drawPieAbonados(dataReturn));
		}
	});
}

function drawPieReprovados(dataReturn){
	var aux = [];
	
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');
    
    for(var i = 0; i < dataReturn.length; i++){
    	aux = dataReturn[i];

    	data.addRow([aux[0], aux[1]]);
    }

    var options = {title:'Top 5 - Mais reprovados no mês'};

    var chart = new google.visualization.PieChart(document.getElementById('chartItensReprovados'));
    chart.draw(data, options);
}

function drawPieAbonados(dataReturn){
	var aux = [];
	
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');
    
    for(var i = 0; i < dataReturn.length; i++){
    	aux = dataReturn[i];

    	data.addRow([aux[0], aux[2]]);
    }

    var options = {title:'Top 5 - Mais abonados no mês'};

    var chart = new google.visualization.PieChart(document.getElementById('chartItensAbonados'));
    chart.draw(data, options);
}