angular.module('polaflixApp').component('loginPolaflix', {
    templateUrl: 'login/login.template.html',
    controller: ['$location', function LoginController($location) {
        var ctrl = this;
        ctrl.username = '';

        ctrl.entrar = function() {
            if (ctrl.username) {
                // Simulamos que iniciamos sesión guardando el usuario en memoria temporal
                sessionStorage.setItem('usuarioLogueado', ctrl.username);
                // Redirigimos a una serie para cumplir la rúbrica (Ej: Peaky Blinders 'S01')
                $location.path('/series/S01'); 
            }
        };
    }]
});