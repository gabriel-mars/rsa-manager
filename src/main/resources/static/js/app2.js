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
		success: function(dataReturn){
			var result = Object.keys(dataReturn).map(function (key) {       
		        return [String(key), dataReturn[key]]; 
		    });
			
			dados = dataReturn;
			// Set a callback to run when the Google Visualization API is loaded.
			google.charts.setOnLoadCallback(drawColumn2(result));
			google.charts.setOnLoadCallback(getDadosItensMensal);
			google.charts.setOnLoadCallback(getDadosItensTotais);
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
		success: function(dataReturn){
			
			drawPieTrabalhados(dataReturn);
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

    var options = {title:'Itens trabalhados no mês - TIME'};

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
		success: function(dataReturn){
			
			drawPieTotais(dataReturn);
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
	
    var options = {title:'Itens totais no mês - TIME'};

    var chart = new google.visualization.PieChart(document.getElementById('chartItensTotais'));
    chart.draw(data, options);
}

// Funções para relatórios de falhas no RSA
function getFalhasColab(){
	let selectColab = document.getElementById('colaborador');
	let date = document.getElementById('data_inicio').value;
  
	let strColab = selectColab.options[selectColab.selectedIndex].value;
	
	ary = [];
	
	ary.push({ Data: date, Colab: strColab });
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/falha/mensal/individual",
		data: JSON.stringify(ary),
		success: function(dataReturn){
			var result = Object.keys(dataReturn).map(function (key) {       
		        return [String(key), dataReturn[key]]; 
		    });
			google.charts.setOnLoadCallback(drawChartFalhas(result));
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

    var options = {title:'Falhas totais no mês - Individual'};

    var chart = new google.visualization.PieChart(document.getElementById('chartFalhasColab'));
    chart.draw(data, options);
    
    getDataTable();
}

function getDataTable(){
	var dataTable = [];
	var arrayStr = [];
	
	let tabela = document.getElementById('tabelaFalhas');
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/falha/mensal/individual/detalhe",
		data: JSON.stringify(ary),
		success: function(dataReturn){
			var result = Object.keys(dataReturn).map(function (key) {       
		        return [String(key), dataReturn[key]]; 
		    });
			
			for(var i = 0; i < result.length; i++){
				var aux2 = [];
				var aux = result[i];
				arrayStr = aux[0].split(':');
				
				aux2[0] = arrayStr[0];
				aux2[1] = arrayStr[1];
				aux2[2] = aux[1];
				
				dataTable.push(aux2);
				fillTable(dataTable);
			}
		}
	});
}

function fillTable(values){
	for (var i = 0; i < values.length; i++){
		console.log(values[0]);
		var aux = values[i];
		
		$('#tabelaFalhas').append('<tr>' +
				'<td>' + aux[0] + '</td>' +
				'<td>' + aux[1] + '</td>' +
				'<td>' + aux[2] + '</td>' +
				'</tr>');
		
		
	}
}