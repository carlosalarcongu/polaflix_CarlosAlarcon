angular.module('polaflixApp').component('loginPolaflix', {
    templateUrl: 'login/login.template.html',
    controller: ['$location', function LoginController($location) {
        var ctrl = this;
        ctrl.username = '';

        ctrl.entrar = function() {
            if (ctrl.username) {
                sessionStorage.setItem('usuarioLogueado', ctrl.username);
                $location.path('/series/S01'); 
            }
        };
    }]
});