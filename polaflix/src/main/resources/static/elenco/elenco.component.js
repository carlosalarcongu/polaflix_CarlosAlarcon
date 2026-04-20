angular.module('polaflixApp').component('elencoSeries', {
    templateUrl: 'elenco/elenco.template.html',
    controller: ['PolaflixService', '$q', function(PolaflixService, $q) {
        var ctrl = this;
        ctrl.actoresMap = {};
        ctrl.cargando = true;

        ctrl.$onInit = function() {
            PolaflixService.getSeries().then(function(seriesResumidas) {
                
                var promesasDetalle = seriesResumidas.map(s => PolaflixService.getSerie(s.id));
                
                $q.all(promesasDetalle).then(function(seriesDetalladas) {
                    seriesDetalladas.forEach(function(serie) {
                        if (serie && serie.actores) {
                            serie.actores.forEach(function(actor) {
                                if (!ctrl.actoresMap[actor.nombre]) {
                                    ctrl.actoresMap[actor.nombre] = [];
                                }
                                ctrl.actoresMap[actor.nombre].push(serie.titulo);
                            });
                        }
                    });
                    ctrl.cargando = false;
                });
            });
        };
    }]
});