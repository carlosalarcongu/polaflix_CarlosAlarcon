angular.module('polaflixApp').component('catalogoSeries', {
    templateUrl: 'catalogo/catalogo.template.html',
    controller: ['PolaflixService', '$location', '$q', function(PolaflixService, $location, $q) {
        var ctrl = this;
        ctrl.series = [];
        ctrl.username = sessionStorage.getItem('usuarioLogueado');

        ctrl.$onInit = function() {
            if (!ctrl.username) { $location.path('/login'); return; }
            
            // $q.all espera a que el catálogo y el perfil lleguen para procesarlos juntos
            $q.all([
                PolaflixService.getSeries(),
                PolaflixService.getUsuario(ctrl.username)
            ]).then(function(respuestas) {
                var catalogoData = respuestas[0];
                var usuarioData = respuestas[1];
                
                // Mapeamos cada serie para inyectarle el estado que tiene para este usuario
                ctrl.series = catalogoData.map(function(serie) {
                    // El JSON de usuario tiene: "estadoSeries": { "Peaky Blinders": "EMPEZADA" }
                    serie.estadoPersonal = usuarioData.estadoSeries[serie.titulo] || null;
                    return serie;
                });
            });
        };

        ctrl.addPendiente = function(idSerie) {
            PolaflixService.agregarAPendientes(ctrl.username, idSerie).then(function() {
                // Actualizamos la vista localmente sin recargar la página entera
                var serieAfectada = ctrl.series.find(s => s.id === idSerie);
                if (serieAfectada) serieAfectada.estadoPersonal = 'PENDIENTE';
            });
        };
    }]
});