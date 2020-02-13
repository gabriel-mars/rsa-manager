var json_falhas = {
		"falhas": [{
			"primaria": "Site",
			"options": ["Equipamentos desinstalados em gabinete macro", "Terreno", "Material | Equipamento desinstalado e sobra de material", "Identificação do site"]},
			{"primaria": "Infra - Solo",
			 "options": ["Gradil Gabinete", "Gradil RRUs", "SKID", "Base de concreto", "Quadro de energia", "Costela de vaca", "Poste de RRUs", "Reforço estrutural"]},
			{"primaria": "Infra - Torre",
			 "options": ["Placa de passagem", "Costela de vaca", "Suporte simples", "Suporte bandeira", "Suporte cinta", "Suporte montante com pipe", "Suporte Y", "Cinta BAP"]},
			{"primaria": "Gabinete | Container | Sala",
			 "options": ["Gabinete - Fechado", "Gabinete - Aberto", "Aterramento", "Energia", "Baterias", "Trocador de calor", "Alarmes externos - Físico", "Alarmes externos - Lógico", "Alarmes externos - Testes", "RBS - DUW, DUG e BB", "Cabeamento | Disjuntores", "Encaminhamento de cabos", "Acomodação de FO", "Transmissão", "Antena GPS"]},
			{"primaria": "Esteiramento",
			 "options": ["Esteiramento horizontal", "Esteiramento vertical"]},
			{"primaria": "Elementos RF Down",
			 "options": ["Diplexer", "Triplexer", "Quadriplexer", "EHCU"]},
			{"primaria": "Bússola",
			 "options": ["Azimute"]},
			{"primaria": "Elementos RF Up",
			 "options": ["Diplexer", "Triplexer", "Quadriplexer", "EHCU", "Filtro"]},
			{"primaria": "EPI",
			 "options": ["CA e validade", "Colaborador equipado"]},
			{"primaria": "Sistema Irradiante",
			 "options": ["RRU - Master | Slave", "TMA | dTMA | RIU", "RET", "Antena", "Visada", "Tilt M.", "Suporte RRU", "Suporte Antena"]},
			{"primaria": "Geral",
			 "options": ["PPI"]}]
};

function buscarFalhas(e){{	
	document.querySelector("#falha").innerHTML = '';
    var falha_select = document.querySelector("#falha");
    var num_options = json_falhas.falhas.length;
    var j_index = -1;

    // aqui eu pego o index da falha dentro do JSON
    for(var x = 0; x < num_options; x++){
        if(json_falhas.falhas[x].primaria === e){
            j_index = x;
        }
    }
    
    if(j_index !== -1){
        json_falhas.falhas[j_index].options.forEach(function(option){
           var fal_opts = document.createElement('option');
           fal_opts.setAttribute('value',option);
           fal_opts.innerHTML = option;
           falha_select.appendChild(fal_opts);
           $('select').formSelect();
        });
    }else{
        document.querySelector("#falha").innerHTML = '';
    }
}}