angular.module('polaflixApp').component('comunidadPolaflix', {
    templateUrl: 'comunidad/comunidad.template.html',
    controller: ['PolaflixService', function(PolaflixService) {
        var ctrl = this;
        ctrl.searchUser = '';
        ctrl.userResult = null;
        ctrl.error = null;

        ctrl.buscar = function() {
            ctrl.error = null;
            ctrl.userResult = null;
            if (ctrl.searchUser) {
                PolaflixService.getUsuario(ctrl.searchUser.toLowerCase()).then(function(data) {
                    ctrl.userResult = data;
                }).catch(function() {
                    ctrl.error = "No se ha encontrado a ningún usuario con ese nombre (Prueba con mrajoy, pepereina...).";
                });
            }
        };
    }]
});