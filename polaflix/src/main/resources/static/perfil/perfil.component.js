angular.module('polaflixApp').component('perfilUsuario', {
    templateUrl: 'perfil/perfil.template.html',
    controller: ['PolaflixService', '$location', function(PolaflixService, $location) {
        var ctrl = this;
        ctrl.usuario = null;
        ctrl.username = sessionStorage.getItem('usuarioLogueado');

        ctrl.$onInit = function() {
            if (!ctrl.username) { $location.path('/login'); return; }
            PolaflixService.getUsuario(ctrl.username).then(function(data) {
                ctrl.usuario = data;
            });
        };
    }]
});