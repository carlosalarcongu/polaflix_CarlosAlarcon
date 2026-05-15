angular.module('polaflixApp').component('detalleSerie', {
    templateUrl: 'serie/serie.template.html',
    controller: ['$routeParams', 'PolaflixService', '$q', function($routeParams, PolaflixService, $q) {
        var ctrl = this;
        ctrl.serie = null;
        ctrl.usuario = null;
        ctrl.username = sessionStorage.getItem('usuarioLogueado'); 
        var serieId = parseInt($routeParams.serieId, 10);

        ctrl.temporadaExpandida = null;

        ctrl.$onInit = function() {
            $q.all([
                PolaflixService.getSerie(serieId),
                PolaflixService.getUsuario(ctrl.username)
            ]).then(function(res) {
                ctrl.serie = res[0];
                ctrl.usuario = res[1];
                if (ctrl.serie.temporadas && ctrl.serie.temporadas.length > 0) {
                    ctrl.temporadaExpandida = ctrl.serie.temporadas[0].id;
                }
            });
        };

        ctrl.toggleTemporada = function(idTemporada) {
            if (ctrl.temporadaExpandida === idTemporada) {
                ctrl.temporadaExpandida = null;
            } else {
                ctrl.temporadaExpandida = idTemporada;
            }
        };

        ctrl.esVisto = function(idCapitulo) {
            if(!ctrl.usuario || !ctrl.usuario.capitulosVistos) return false;
            return ctrl.usuario.capitulosVistos.some(c => c.id === idCapitulo);
        };

        ctrl.marcarVisto = function(idCapitulo) {
            PolaflixService.verCapitulo(ctrl.username, idCapitulo).then(function() {
                PolaflixService.getUsuario(ctrl.username).then(u => ctrl.usuario = u);
            });
        };

        ctrl.getPosterGeneral = function(idSerie) {
            if (idSerie === 1) return 'images/PeakyBlinders.png';
            if (idSerie === 2) return 'images/PrisonBreakS01.png';
            if (idSerie === 3) return 'images/LQSAS01.png';
            return 'images/polaflix-logo.png';
        };

        ctrl.getPosterTemporada = function(idSerie, numTemporada) {
            var safeNum = numTemporada > 4 ? 1 : numTemporada;
            var sufijo = 'S0' + safeNum + '.png';
            if (idSerie === 1) return 'images/PeakyBlinders' + sufijo;
            if (idSerie === 2) return 'images/PrisonBreak' + sufijo;
            if (idSerie === 3) return 'images/LQSA' + sufijo;
            return 'images/polaflix-logo.png';
        };
    }]
});