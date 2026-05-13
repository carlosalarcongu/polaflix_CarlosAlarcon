angular.module('polaflixApp').component('catalogoSeries', {
    templateUrl: 'catalogo/catalogo.template.html',
    controller: ['PolaflixService', '$location', '$q', function(PolaflixService, $location, $q) {
        var ctrl = this;
        ctrl.series = [];
        ctrl.username = sessionStorage.getItem('usuarioLogueado');
        
        ctrl.abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0-9".split("");
        ctrl.letraSeleccionada = null;
        ctrl.searchQuery = "";

        ctrl.$onInit = function() {
            if (!ctrl.username) { $location.path('/login'); return; }
            ctrl.cargarCatalogo();
        };

        ctrl.cargarCatalogo = function(inicial, titulo) {
            $q.all([
                PolaflixService.getSeries(inicial, titulo),
                PolaflixService.getUsuario(ctrl.username)
            ]).then(function(res) {
                var catalogoData = res[0];
                var usuarioData = res[1];
                
                ctrl.series = catalogoData.map(function(serie) {
                    serie.estadoPersonal = usuarioData.estadoSeries[serie.id] || null;
                    return serie;
                });
            });
        };

        ctrl.filtrarPorLetra = function(letra) {
            ctrl.searchQuery = "";
            ctrl.letraSeleccionada = letra;
            if(letra === '0-9') {
                ctrl.cargarCatalogo('0', null);
            } else {
                ctrl.cargarCatalogo(letra, null);
            }
        };

        ctrl.buscar = function() {
            ctrl.letraSeleccionada = null;
            if(ctrl.searchQuery.trim().length > 0) {
                ctrl.cargarCatalogo(null, ctrl.searchQuery);
            } else {
                ctrl.cargarCatalogo();
            }
        };

        ctrl.addPendiente = function(idSerie) {
            PolaflixService.agregarAPendientes(ctrl.username, idSerie).then(function() {
                var serieAfectada = ctrl.series.find(s => s.id === idSerie);
                if (serieAfectada) serieAfectada.estadoPersonal = 'PENDIENTE';
            });
        };

        ctrl.getPosterGeneral = function(idSerie) {
            if (idSerie === 'S01') return 'images/PeakyBlinders.png';
            if (idSerie === 'S02') return 'images/PrisonBreakS01.png'; 
            if (idSerie === 'S03') return 'images/LQSAS01.png'; 
            return 'images/polaflix-logo.png'; 
        };
    }]
});