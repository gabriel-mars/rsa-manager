// Dados colaborador
var ary = [];

//Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart'], 'language': 'pt'});

// Arrays com os dados para os gráficos
var dados = [];
var dadosMedia = [];

// Validação do dos colaboradores de cada organização
var colaboradoresInatel = [];
var colaboradoresFitec = [];
var colaboradoresEricsson = [];

$('document').ready(function(){
	$.ajax({
		url: '/colaborador/inatel',
        type: 'GET',
        success: function (dataColabInatel) {
            for (var i = 0; i < dataColabInatel.length; i++) {
            	colaboradoresInatel.push(dataColabInatel[i]);
            }
        }
	});
	
	$.ajax({
		url: '/colaborador/fitec',
        type: 'GET',
        success: function (dataColabFitec) {
            for (var i = 0; i < dataColabFitec.length; i++) {
            	colaboradoresFitec.push(dataColabFitec[i]);
            }
        }
	});
	
	$.ajax({
		url: '/colaborador/ericsson',
        type: 'GET',
        success: function (dataColabEricsson) {
            for (var i = 0; i < dataColabEricsson.length; i++) {
            	colaboradoresEricsson.push(dataColabEricsson[i]);
            }
        }
	});
});

function buscarColaboradores(e){
	$('select').formSelect();
    document.querySelector("#colaborador").innerHTML = '';
    var colaborador_select = document.querySelector("#colaborador");
    var num_colabs_inatel = colaboradoresInatel.length;
    var num_colabs_fitec = colaboradoresFitec.length;
    var num_colabs_ericsson = colaboradoresEricsson.length;
    
    if(e === 'INATEL'){
    	for (var i = 0; i < num_colabs_inatel; i++){
        	var colaborador = colaboradoresInatel[i];
        	$('#colaborador').append($('<option>').val(colaborador).text(colaborador));
        	$('select').formSelect();
        }
    } else if (e === 'FITEC'){
    	for (var i = 0; i < num_colabs_fitec; i++){
        	var colaborador = colaboradoresFitec[i];
        	$('#colaborador').append($('<option>').val(colaborador).text(colaborador));
        	$('select').formSelect();
        }
    } else {
    	for (var i = 0; i < num_colabs_ericsson; i++){
        	var colaborador = colaboradoresEricsson[i];
        	$('#colaborador').append($('<option>').val(colaborador).text(colaborador));
        	$('select').formSelect();
        }
    }
}

// Validação do formulário para o relatório
function getDados() {
	let selectOrg = document.getElementById('organizacao');
	let selectColab = document.getElementById('colaborador');
	let date = document.getElementById('data_inicio').value;
  
	let strOrg = selectOrg.options[selectOrg.selectedIndex].value;
	let strColab = selectColab.options[selectColab.selectedIndex].value;
	
	ary = [];
	
	ary.push({ Org: strOrg, Colab: strColab, Data: date });
  
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/colaborador",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			dados = dataReturn;
			dadosMedia = [];
			google.charts.setOnLoadCallback(getMedias);
			google.charts.setOnLoadCallback(getItensColab);
			google.charts.setOnLoadCallback(getItensTime);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
	
	getFalhasColabMensal(ary);
}

// Gráfico de atividades executadas por dia
function drawColumn(){
	var auxMedia = [];
	
	var result = Object.keys(dados).map(function (key) {       
        return [String(key), dados[key]]; 
    }); 
    
	dados = result;
	
	dados.sort(function(a, b){
		if(a[0] > b[0]){
			return 1;
		}
		
		if(a[0] < b[0]){
			return -1;
		}
		
		return 0;
	});
	
	// Tratamento dos dados somente para os dias trabalhados pelo colaborador
	for(var i = 0; i < dados.length; i++){
    	var a = dados[i];
    	var b;
    	 
    	for(var j = 0; j < dadosMedia.length; j++){
    		b = dadosMedia[j];

    		if(a[0] === b[0]){
    			var aux = [];
    			aux[0] = a[0];
    			aux[1] = a[1];
    			aux[2] = b[1];
    			aux[3] = b[2];
    		
    			auxMedia.push(aux);
    		}
    	}
    }
	
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Topping');
	data.addColumn('number', 'Itens avalidados');
	data.addColumn('number', 'Média');
  
	for(var i = 0; i < result.length; i++){
		var a = result[i];
		var b = auxMedia[i];
		
  	  	data.addRow([a[0], a[1], b[3]]); 
	}

	var options = {
		  title:'Itens avaliados por dia',
		  seriesType: 'bars',
		  series: {1: {type: 'line'}},
		  hAxis: {
	          title: 'Data',
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
	var chart = new google.visualization.ComboChart(document.getElementById('chartAtividadeDia'));
	chart.draw(data, options); 
}

// Chamada dos dados para o gráfico de acordo com a média
function getMedias(){	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/media/dia",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			for(var i = 0; i < dataReturn.length; i++){
				var aux = [];
				var data = [];
				var strArray = [];
				
				aux = dataReturn[i];
				strArray = aux[0].toString().split(':');
				
				data[0] = strArray[0];
				data[1] = parseFloat(strArray[1]);
				data[2] = aux[1];
				data[3] = aux[2];
				
				dadosMedia.push(data);
			}
			
			google.charts.setOnLoadCallback(drawColumn);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

// Chamada dos dados para o gráfico pizza Itens
function getItensColab(){	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/itens/colaborador",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			var result = Object.keys(dataReturn).map(function (key) {       
		        return [String(key), dataReturn[key]]; 
		    });
			
			drawPieColab(result);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

// Gráfico pizza Itens
function drawPieColab(values){
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');
    
    var abonados, rejeitados, aprovados;
    var aux = [];
	var array = [];
	
	for(var i = 0; i < values.length; i++){
		var aux = values[i];
		var array = aux[0].split(':');
	}
	
	rejeitado = array[0];
	abonados = aux[1];
	aprovados = array[1];
    
	data.addRows([
      ['Aprovados', parseInt(aprovados)],
      ['Reprovados', parseInt(rejeitado)],
      ['Abonados', parseInt(abonados)]
    ]);

    var options = {title:'Itens trabalhados no mês - Colaborador', pieSliceText: 'value-and-percentage'};

    var chart = new google.visualization.PieChart(document.getElementById('colab_chart_div'));
    chart.draw(data, options);
}

//Chamada dos dados para o gráfico pizza Itens
function getItensTime(){	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/itens/time",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			var result = Object.keys(dataReturn).map(function (key) {       
		        return [String(key), dataReturn[key]]; 
		    });
			
			drawPieTime(result);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function drawPieTime(values){
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');
    
    var abonados, rejeitados, aprovados;
    var aux = [];
	var array = [];
	
	for(var i = 0; i < values.length; i++){
		var aux = values[i];
		var array = aux[0].split(':');
	}
	
	rejeitado = array[0];
	abonados = aux[1];
	aprovados = array[1];
	
    data.addRows([
      ['Aprovados', parseInt(aprovados)],
      ['Reprovados', parseInt(rejeitado)],
      ['Abonados', parseInt(abonados)]
    ]);

    var options = {title:'Itens trabalhados no mês - TIME', pieSliceText: 'value-and-percentage'};

    var chart = new google.visualization.PieChart(document.getElementById('time_chart_div'));
    chart.draw(data, options);
}

function getFalhasColabMensal(ary){
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/falha/mensal/individual",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			google.charts.setOnLoadCallback(drawChartFalhasMensal(dataReturn));
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function drawChartFalhasMensal(values){
	var aux = [];
	
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');
    
    for(var i = 0; i < values.length; i++){
    	aux = values[i];

    	data.addRow([aux[1], aux[0]]);
    }

    var options = {title:'Falhas totais no mês - Individual', pieSliceText: 'value-and-percentage'};

    var chart = new google.visualization.PieChart(document.getElementById('chartFalhasColabMensal'));
    chart.draw(data, options);
    
    getDataTableFalhas();
}

function getDataTableFalhas(){
	var dataTable = [];
	var arrayStr = [];
	
	let tabela = document.getElementById('tabelaFalhasColab');
	$("#tabelaFalhasColab tr").remove();
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/falha/mensal/individual/detail",
		data: JSON.stringify(ary),
		beforeSend: function(){
			$(".loader").show();
		},
		success: function(dataReturn){
			fillTableFalhasColab(dataReturn);
		},
		complete: function(data){
			$(".loader").hide();
		}
	});
}

function fillTableFalhasColab(values){
	for (var i = 0; i < values.length; i++){
		var aux = values[i];
		
		$('#tabelaFalhasColab').append('<tr>' +
				'<td>' + aux[0] + '</td>' +
				'<td>' + aux[1] + '</td>' +
				'<td>' + aux[2] + '</td>' +
				'<td>' + aux[3] + '</td>' +
				'</tr>');	
	}
}