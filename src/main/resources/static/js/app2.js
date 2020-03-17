// Dados time
var ary = [];

// Dados para os gráficos
var dados = [];

//Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart'], 'language': 'pt'});

function getDadosTime(){
	let selectOrg = document.getElementById('organizacao');
	let date = document.getElementById('data_inicio').value;
  
	let strOrg = selectOrg.options[selectOrg.selectedIndex].value;
	
	ary = [];
	
	ary.push({ Org: strOrg, Data: date });
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/time",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			var result = Object.keys(dataReturn).map(function (key) {       
		        return [String(key), dataReturn[key]]; 
		    });
			
			dados = dataReturn;
			// Set a callback to run when the Google Visualization API is loaded.
			google.charts.setOnLoadCallback(drawColumn2(result));
			google.charts.setOnLoadCallback(getDadosItensMensal);
			google.charts.setOnLoadCallback(getDadosItensTotais);
			google.charts.setOnLoadCallback(getDadosItensMes);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

// Gráfico de itens consolidados de aprovados e rejeitados
function drawColumn2(values){
	var aux = [];
	var arrayStr = [];
	var valoresAux = [];
	
	var result = Object.keys(dados).map(function (key) {       
        return [String(key), dados[key]]; 
    }); 
	
	result.sort(function(a, b){
		if(a[0] > b[0]){
			return 1;
		}
		
		if(a[0] < b[0]){
			return -1;
		}
		
		return 0;
	});
	
	for(var i = 0; i < result.length; i++){
		var aux2 = [];
		aux = result[i];
		arrayStr = aux[0].split(':');

		aux2[0] = arrayStr[0];
		aux2[1] = parseInt(arrayStr[1]);
		aux2[2] = parseInt(aux[1]);
		
		valoresAux.push(aux2);
	}
	
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Data');
    data.addColumn('number', 'Aprovados');
    data.addColumn('number', 'Rejeitados');
	
	for (var i = 0; i < valoresAux.length; i++){
		var a = valoresAux[i];
		data.addRow([a[0], a[2], a[1]]); 
	}
	
	var options = {
			title: "Consolidado de Itens Aprovados e Rejeitados",
			isStacked: true
	};
    
	var chart = new google.visualization.ColumnChart(document.getElementById("chartAtividadeMes"));
	chart.draw(data, options);
}

// Gráfico de Pizza dos itens trabalhados
function getDadosItensMensal(){
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/itens/trabalhado",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			drawPieTrabalhados(dataReturn);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function drawPieTrabalhados(values){
	var aux = [];
	
	var result = Object.keys(values).map(function (key) {       
        return [Number(key), values[key]]; 
    });
	
	aux = result[0];
	
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');
    
	data.addRows([
      ['Aprovados', parseInt(aux[1])],
      ['Reprovados', parseInt(aux[0])]
    ]);

    var options = {title:'Itens trabalhados no mês - TIME', pieSliceText: 'value-and-percentage'};

    var chart = new google.visualization.PieChart(document.getElementById('chartItensTrabalhados'));
    chart.draw(data, options);
}

//Gráfico de Pizza dos itens totais do mês
function getDadosItensTotais(){
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/itens/total",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			drawPieTotais(dataReturn);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function drawPieTotais(values){
	var aux = [];
	
	var result = Object.keys(values).map(function (key) {       
        return [Number(key), values[key]]; 
    });
	
	aux = result[0];
	
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');
    
	data.addRows([
      ['Ap+Re*', parseInt(aux[1])],
      ['Abonados', parseInt(aux[0])]
    ]);
	
    var options = {title:'Itens totais no mês - TIME', pieSliceText: 'value-and-percentage'};

    var chart = new google.visualization.PieChart(document.getElementById('chartItensTotais'));
    chart.draw(data, options);
}

// Funções para os gráficos de ranqueamento
function getDadosItensMes(){
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/itens/mensal",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			drawChartTotais(dataReturn);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

// Gráfico de ranqueamento DESCONSIDERANDO abonos
function drawChartTotais(values){
	
	console.log(values);
	
	var lstAux = [];
	var soma = 0;
	var media = 0;
	var mediaAux = 0;
	var dp = 0;
	
	for(var i = 0; i < values.length; i++){
		var strArray = [];
		var aux2 = [];
		var aux = values[i];
		
		aux2[0] = aux[0];
		aux2[1] = parseInt(aux[1]);
		aux2[2] = aux[2];
		aux2[3] = aux[3];
		
		lstAux.push(aux2);
	}
	
	lstAux.sort(function(a, b){
		if(a[1] < b[1]){
			return 1;
		}
		
		if(a[1] > b[1]){
			return -1;
		}
		
		return 0;
	});
	
	for(var i = 0; i < lstAux.length; i++){
		var a = lstAux[i];
		
		soma += parseInt(a[1]);
	}
  
	media = soma / lstAux.length;
	
	soma = 0;
	
	for(var i = 0; i < lstAux.length; i++){
		var a = lstAux[i];
		
		soma = soma + Math.pow((a[1] - media), 2);
	}
	
	mediaAux = soma / lstAux.length;
	dp = Math.sqrt(mediaAux);
	
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Colaborador');
    data.addColumn('number', 'Ap+Re*');
    data.addColumn('number', 'Média');
	data.addColumn('number', 'DP superior');
	data.addColumn('number', 'DP inferior');
	data.addColumn('number', 'Sites');
	
	for (var i = 0; i < lstAux.length; i++){
		var a = lstAux[i];
		data.addRow([a[0], a[1], media, (media + dp), dp, a[3]]); 
	}
	
	var options = {
		  title: "Ranqueamento de acordo com os itens AP + RE*",
		  hAxis: {
	          title: 'Colaborador',
	          viewWindow: {
	            min: [7, 30, 0],
	            max: [17, 30, 0]
	          }
	        },
	        vAxes: [
		    	  {title: "Itens"},
		    	  {title: "Sites"}
		      ],
		    
		    series: {
		    	0: {type: 'bars', targetAxisIndex: 0},
		    	1: {type: 'line', targetAxisIndex: 0},
		    	2: {type: 'line', targetAxisIndex: 0},
		    	3: {type: 'line', targetAxisIndex: 0},
		    	4: {type: 'line', targetAxisIndex: 1}}
	 };
    
	var chart = new google.visualization.ComboChart(document.getElementById("chartRankMes"));
	chart.draw(data, options);
	
	drawChartAbonos(lstAux);
}

//Gráfico de ranqueamento CONSIDERANDO abonos
function drawChartAbonos(values){
	var soma = 0;
	var media = 0;
	var mediaAux = 0;
	var dp = 0;
	
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Colaborador');
    data.addColumn('number', 'Totais');
    data.addColumn('number', 'Média');
	data.addColumn('number', 'DP superior');
	data.addColumn('number', 'DP inferior');
    
    values.sort(function(a, b){
		if(a[2] < b[2]){
			return 1;
		}
		
		if(a[2] > b[2]){
			return -1;
		}
		
		return 0;
	});
    
    for(var i = 0; i < values.length; i++){
		var a = values[i];
		
		soma += parseInt(a[2]);
	}
  
	media = soma / values.length;
	
	soma = 0;
	
	for(var i = 0; i < values.length; i++){
		var a = values[i];
		
		soma = soma + Math.pow((a[2] - media), 2);
	}
	
	mediaAux = soma / values.length;
	dp = Math.sqrt(mediaAux);
	
	for (var i = 0; i < values.length; i++){
		var a = values[i];
		data.addRow([a[0], a[2], media, (media + dp), dp]);  
	}
	
	var options = {
		  title: "Ranqueamento de acordo com os itens totais",
		  seriesType: 'bars',
		  series: {1: {type: 'line'}, 2: {type: 'line'}, 3: {type: 'line'}},
		  hAxis: {
	          title: 'Colaborador',
	          viewWindow: {
	            min: [7, 30, 0],
	            max: [17, 30, 0]
	          }
	        },
	        vAxis: {
	            title: 'Itens'
	          }
	 };
    
	var chart = new google.visualization.ComboChart(document.getElementById("chartRankAbonoMes"));
	chart.draw(data, options);
}

// Funções para relatórios de falhas INDIVIDUAIS no RSA
function getFalhasColab(){
	let selectColab = document.getElementById('colaborador');
  
	let strColab = selectColab.options[selectColab.selectedIndex].value;
	
	ary = [];
	
	ary.push({ Colab: strColab });
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/falha/individual",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			var result = Object.keys(dataReturn).map(function (key) {       
		        return [String(key), dataReturn[key]]; 
		    });
			google.charts.setOnLoadCallback(drawChartFalhas(result));
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function drawChartFalhas(values){
	var aux = [];
	
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');
    
    for(var i = 0; i < values.length; i++){
    	aux = values[i];

    	data.addRow([aux[0], aux[1]]);
    }

    var options = {title:'Falhas totais no mês - Individual', pieSliceText: 'value-and-percentage'};

    var chart = new google.visualization.PieChart(document.getElementById('chartFalhasColab'));
    chart.draw(data, options);
    
    getDataTable();
}

function getDataTable(){
	var dataTable = [];
	var arrayStr = [];
	
	let tabela = document.getElementById('tabelaFalhas');
	$("#tabelaFalhas tr").remove();
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/falha/individual/detalhe",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			fillTable(dataReturn);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function fillTable(values){
	for (var i = 0; i < values.length; i++){
		var aux = values[i];
		
		$('#tabelaFalhas').append('<tr>' +
				'<td>' + aux[0] + '</td>' +
				'<td>' + aux[1] + '</td>' +
				'<td>' + aux[2] + '</td>' +
				'<td>' + aux[3] + '</td>' +
				'</tr>');	
	}
}

//Funções para relatórios de falhas TIME no RSA
function getFalhasTime(){
	let date = document.getElementById('data_inicio').value;
	
	ary = [];
	
	ary.push({ Data: date });
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/falha/mensal/time",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			var result = Object.keys(dataReturn).map(function (key) {       
		        return [String(key), dataReturn[key]]; 
		    });
			google.charts.setOnLoadCallback(drawChartFalhasTime(result));
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function drawChartFalhasTime(values){
	var aux = [];
	
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');
    
    for(var i = 0; i < values.length; i++){
    	aux = values[i];

    	data.addRow([aux[0], aux[1]]);
    }

    var options = {title:'Falhas totais no mês - TIME', pieSliceText: 'value-and-percentage'};

    var chart = new google.visualization.PieChart(document.getElementById('chartFalhasTime'));
    chart.draw(data, options);
    
    getDataTableTime();
}

function getDataTableTime(){
	var dataTable = [];
	var arrayStr = [];
	
	let tabela = document.getElementById('tabelaFalhas');
	$("#tabelaFalhas tr").remove();
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/falha/mensal/time/detalhe",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			fillTable(dataReturn);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function getDadosDiario(){
	let selectOrg = document.getElementById('organizacao');
	let date = document.getElementById('data_inicio').value;
  
	let strOrg = selectOrg.options[selectOrg.selectedIndex].value;
	
	ary = [];
	
	ary.push({ Org: strOrg, Data: date });
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/diario",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			google.charts.setOnLoadCallback(drawColumnDiario(dataReturn));
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

// Gráficos de relatório diário
function drawColumnDiario(dataReturn){
	var soma = 0;
	var media = 0;
	var mediaAux = 0;
	var dp = 0;
	var auxDp = [];
	
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Topping');
	data.addColumn('number', 'Itens Ap+Re*');
	data.addColumn('number', 'Média');
	data.addColumn('number', 'DP superior');
	data.addColumn('number', 'DP inferior');
	
	for(var i = 0; i < dataReturn.length; i++){
		var a = dataReturn[i];
		
		soma += parseInt(a[4]);
	}
  
	media = soma / dataReturn.length;
	
	soma = 0;
	
	for(var i = 0; i < dataReturn.length; i++){
		var a = dataReturn[i];
		
		soma = soma + Math.pow((a[4] - media), 2);
	}
	
	mediaAux = soma / dataReturn.length;
	dp = Math.sqrt(mediaAux);
	
	for(var i = 0; i < dataReturn.length; i++){
		var a = dataReturn[i];
		
  	  	data.addRow([a[0], a[4], media, (media + dp), dp]); 
	}

	var options = {
		  title:'Itens avaliados no dia',
		  seriesType: 'bars',
		  series: {1: {type: 'line'}, 2: {type: 'line'}, 3: {type: 'line'}},
		  hAxis: {
	          title: 'Colaborador',
	          viewWindow: {
	            min: [7, 30, 0],
	            max: [17, 30, 0]
	          }
	        },
	        vAxis: {
	            title: 'Itens'
	          }
	 };

	// Instantiate and draw our chart, passing in some options.
	var chart = new google.visualization.ComboChart(document.getElementById('chartAtividadeDiario'));
	chart.draw(data, options);
	
	getDadosItensDiario();
}

function getDadosItensDiario(){
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/diario/trabalhado",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			drawPieTrabalhadosDiario(dataReturn);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function drawPieTrabalhadosDiario(values){
	var aux = [];
	
	var result = Object.keys(values).map(function (key) {       
        return [Number(key), values[key]]; 
    });
	
	aux = result[0];
	
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');
    
	data.addRows([
      ['Aprovados', parseInt(aux[1])],
      ['Reprovados', parseInt(aux[0])]
    ]);

    var options = {title:'Itens trabalhados no dia - TIME', pieSliceText: 'value-and-percentage'};

    var chart = new google.visualization.PieChart(document.getElementById('chartItensTrabalhadosDiario'));
    chart.draw(data, options);
    
    getDadosItensTotaisDiario();
}

//Gráfico de Pizza dos itens totais do mês
function getDadosItensTotaisDiario(){
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/diario/total",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			drawPieTotaisDiario(dataReturn);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function drawPieTotaisDiario(values){
	var aux = [];
	
	var result = Object.keys(values).map(function (key) {       
        return [Number(key), values[key]]; 
    });
	
	aux = result[0];
	
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');
    
	data.addRows([
      ['Ap+Re*', parseInt(aux[1])],
      ['Abonados', parseInt(aux[0])]
    ]);
	
    var options = {title:'Itens totais no dia - TIME', pieSliceText: 'value-and-percentage'};

    var chart = new google.visualization.PieChart(document.getElementById('chartItensTotaisDiario'));
    chart.draw(data, options);
}