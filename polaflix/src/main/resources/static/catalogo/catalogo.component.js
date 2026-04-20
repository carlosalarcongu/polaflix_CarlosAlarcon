angular.module('polaflixApp').component('catalogoSeries', {
    templateUrl: 'catalogo/catalogo.template.html',
    controller: ['PolaflixService', '$location', '$q', function(PolaflixService, $location, $q) {
        var ctrl = this;
        ctrl.series = [];
        ctrl.username = sessionStorage.getItem('usuarioLogueado');

        ctrl.$onInit = function() {
            if (!ctrl.username) { $location.path('/login'); return; }
            
            $q.all([
                PolaflixService.getSeries(),
                PolaflixService.getUsuario(ctrl.username)
            ]).then(function(respuestas) {
                var catalogoData = respuestas[0];
                var usuarioData = respuestas[1];
                
                ctrl.series = catalogoData.map(function(serie) {
                    serie.estadoPersonal = usuarioData.estadoSeries[serie.titulo] || null;
                    return serie;
                });
            });
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