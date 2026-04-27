angular.module('polaflixApp').component('detalleSerie', {
    templateUrl: 'serie/serie.template.html',
    controller: ['$routeParams', 'PolaflixService', function SerieController($routeParams, PolaflixService) {
        var ctrl = this;
        
        ctrl.serie = null;
        ctrl.cargando = true;
        ctrl.mensajeError = null;
        ctrl.mensajeExito = null;
        //Por si no le atino a un ususario logueado
        ctrl.username = sessionStorage.getItem('usuarioLogueado') || 'cr7bicho'; 

        var serieId = $routeParams.serieId;

        ctrl.$onInit = function() {
            PolaflixService.getSerie(serieId).then(function(data) {
                ctrl.serie = data;
                ctrl.cargando = false;
            }).catch(function(error) {
                ctrl.mensajeError = error;
                ctrl.cargando = false;
            });
        };

        ctrl.marcarVisto = function(idCapitulo) {
            ctrl.mensajeError = null;
            ctrl.mensajeExito = null;
            
            PolaflixService.verCapitulo(ctrl.username, idCapitulo).then(function(mensaje) {
                ctrl.mensajeExito = mensaje;
            }).catch(function(error) {
                ctrl.mensajeError = error;
            });
        };


        ctrl.getPosterGeneral = function(idSerie) {
            if (idSerie === 'S01') return 'images/PeakyBlinders.png';
            if (idSerie === 'S02') return 'images/PrisonBreakS01.png';
            if (idSerie === 'S03') return 'images/LQSAS01.png';
            return 'images/polaflix-logo.png';
        };

        ctrl.getPosterTemporada = function(idSerie, numTemporada) {
            var safeNum = numTemporada > 4 ? 1 : numTemporada;
            var sufijo = 'S0' + safeNum + '.png';

            if (idSerie === 'S01') return 'images/PeakyBlinders' + sufijo;
            if (idSerie === 'S02') return 'images/PrisonBreak' + sufijo;
            if (idSerie === 'S03') return 'images/LQSA' + sufijo;
            
            return 'images/polaflix-logo.png';
        };
    }]
});