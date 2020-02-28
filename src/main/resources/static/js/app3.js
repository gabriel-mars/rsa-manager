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

function fillTableFalhasPeriodo(values){
	for (var i = 0; i < values.length; i++){
		var aux = values[i];
		
		$('#tabelaFalhasInatel').append('<tr>' +
				'<td>' + aux[0] + '</td>' +
				'<td>' + aux[4] + '</td>' +
				'<td>' + aux[1] + '</td>' +
				'<td>' + aux[2] + '</td>' +
				'<td>' + aux[3] + '</td>' +
				'</tr>');	
	}
}

function fillTableFalhasPeriodoEricsson(values){
	for (var i = 0; i < values.length; i++){
		var aux = values[i];
		
		$('#tabelaFalhasEricsson').append('<tr>' +
				'<td>' + aux[0] + '</td>' +
				'<td>' + aux[4] + '</td>' +
				'<td>' + aux[1] + '</td>' +
				'<td>' + aux[2] + '</td>' +
				'<td>' + aux[3] + '</td>' +
				'</tr>');	
	}
}

function fillTableFalhasPeriodoOMR(values){
	for (var i = 0; i < values.length; i++){
		var aux = values[i];
		
		$('#tabelaFalhasOMR').append('<tr>' +
				'<td>' + aux[0] + '</td>' +
				'<td>' + aux[4] + '</td>' +
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

function getFalhasInatel(){
	let data_inicial = document.getElementById('data_inicio').value;
	let data_final = document.getElementById('data_fim').value;
	
	ary = [];
	
	ary.push({ DataInicial: data_inicial, DataFinal: data_final, Org: 'INATEL' });
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/falha/periodo/individual",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			google.charts.setOnLoadCallback(drawPie(dataReturn));
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function getDataFalhasPeriodo(){
	$("#tabelaFalhasInatel tr").remove();
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/falha/periodo/individual/detail",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			fillTableFalhasPeriodo(dataReturn);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
	
	 var aux = ary[0];
	 aux.Org = 'ERICSSON';
	    
	 ary[0] = aux;
	 
	getFalhasEricsson();
}

function drawPie(values){
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');

    for(var i = 0; i < values.length; i++){
    	aux = values[i];

    	data.addRow([aux[1], aux[0]]);
    }

    var options = {title:'Falhas totais no período - INATEL', pieSliceText: 'value-and-percentage'};

    var chart = new google.visualization.PieChart(document.getElementById('chartFalhasInatel'));
    chart.draw(data, options);
    
    getDataFalhasPeriodo();
}

function getFalhasEricsson(){
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/falha/periodo/individual",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			google.charts.setOnLoadCallback(drawPieEricsson(dataReturn));
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function drawPieEricsson(values){
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');

    for(var i = 0; i < values.length; i++){
    	aux = values[i];

    	data.addRow([aux[1], aux[0]]);
    }

    var options = {title:'Falhas totais no período - Ericsson', pieSliceText: 'value-and-percentage'};

    var chart = new google.visualization.PieChart(document.getElementById('chartFalhasEricsson'));
    chart.draw(data, options);
    
    getDataFalhasPeriodoEricsson();
}

function getDataFalhasPeriodoEricsson(){
	$("#tabelaFalhasEricsson tr").remove();
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/falha/periodo/individual/detail",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			fillTableFalhasPeriodoEricsson(dataReturn);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
	
	var aux = ary[0];
    aux.Org = 'OMR - CLIENTE';
    
    ary[0] = aux;
    
    getFalhasOMR();
}

function getFalhasOMR(){
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/falha/periodo/individual",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			google.charts.setOnLoadCallback(drawPieOMR(dataReturn));
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function drawPieOMR(values){
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');

    for(var i = 0; i < values.length; i++){
    	aux = values[i];

    	data.addRow([aux[1], aux[0]]);
    }

    var options = {title:'Falhas totais no período - OMR', pieSliceText: 'value-and-percentage'};

    var chart = new google.visualization.PieChart(document.getElementById('chartFalhasOMR'));
    chart.draw(data, options);
    
    getDataFalhasPeriodoOMR();
}

function getDataFalhasPeriodoOMR(){
	$("#tabelaFalhasOMR tr").remove();
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/falha/periodo/individual/detail",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			fillTableFalhasPeriodoOMR(dataReturn);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

// Análise de reprovações
function getReprovacoes(){
	let data_inicial = document.getElementById('data_inicio').value;
	let data_final = document.getElementById('data_fim').value;
	
	ary = [];
	
	ary.push({ DataInicial: data_inicial, DataFinal: data_final});
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/falha/melhoria/reprovacoes",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			google.charts.setOnLoadCallback(drawChartReprovacoes(dataReturn));
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function drawChartReprovacoes(values){
	console.log(values);
	var soma = 0;
	var acumulado = 0;
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Topping');
	data.addColumn('number', 'Reprovações');
	data.addColumn('number', 'Porcentagem de falhas');
	
	for(var i = 0; i < values.length; i++){
		var a = values[i];
		soma += a[1]
	}
  
	for(var i = 0; i < values.length; i++){
		var a = values[i];
		acumulado += a[1] / soma;
  	  	data.addRow([a[0], a[1], acumulado]);
	}

	var options = {
		  title:'Rejeições no Processo RSA',
		  hAxis: {
			  title: 'Reprovações',
	          textStyle: {fontSize: 10},
	          viewWindow: {
	        	  min: [7, 30, 0],
	        	  max: [17, 30, 0]
	          },
	      },
	      vAxes: [
	    	  {title: "Itens",
	    	   gridlines: {count: 5},
	    	   baseline: 0,
	    	   format: "#"},
	    	  {title: "",
	    	   gridlines: {count: 2},
	    	   baseline: 0,
	    	   format: '#%'}
	      ],
		  series: [{ targetAxisIndex: 0 }, { targetAxisIndex: 1, type: "line" }],
		  seriesType: 'bars'  
	 };

	// Instantiate and draw our chart, passing in some options.
	var chart = new google.visualization.ComboChart(document.getElementById('chartTotalReprovacoes'));
	chart.draw(data, options); 
}