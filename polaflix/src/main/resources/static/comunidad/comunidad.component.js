angular.module('polaflixApp').component('comunidadPolaflix', {
    templateUrl: 'comunidad/comunidad.template.html',
    controller: ['PolaflixService', '$q', function(PolaflixService, $q) {
        var ctrl = this;
        ctrl.searchUser = '';
        ctrl.userResult = null;
        ctrl.seriesDetalles = [];
        ctrl.error = null;

        ctrl.buscar = function() {
            ctrl.error = null;
            ctrl.userResult = null;
            ctrl.seriesDetalles = [];
            
            if (ctrl.searchUser) {
                $q.all([
                    PolaflixService.getUsuario(ctrl.searchUser.toLowerCase()),
                    PolaflixService.getSeries()
                ]).then(function(res) {
                    ctrl.userResult = res[0];
                    var catalogo = res[1];
                    
                    if(ctrl.userResult.estadoSeries) {
                        for (var idSerie in ctrl.userResult.estadoSeries) {
                            var serieInfo = catalogo.find(s => s.id === idSerie);
                            if(serieInfo) {
                                ctrl.seriesDetalles.push({
                                    titulo: serieInfo.titulo,
                                    estado: ctrl.userResult.estadoSeries[idSerie],
                                    poster: ctrl.getPosterGeneral(serieInfo.id)
                                });
                            }
                        }
                    }
                }).catch(function() {
                    ctrl.error = "No se ha encontrado a ningún usuario con ese nombre.";
                });
            }
        };

        ctrl.getPosterGeneral = function(idSerie) {
            if (idSerie === 1) return 'images/PeakyBlinders.png';
            if (idSerie === 2) return 'images/PrisonBreakS01.png';
            if (idSerie === 3) return 'images/LQSAS01.png';
            return 'images/polaflix-logo.png';
        };
    }]
});