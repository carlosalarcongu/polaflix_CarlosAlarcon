angular.module('polaflixApp').component('loginPolaflix', {
    templateUrl: 'login/login.template.html',
    controller: ['$location', 'PolaflixService', function LoginController($location, PolaflixService) {
        var ctrl = this;
        ctrl.modoRegistro = false; 
        
        ctrl.username = '';
        ctrl.password = '';
        ctrl.iban = '';
        ctrl.esVip = false;
        
        ctrl.mensaje = '';

        ctrl.entrar = function() {
            if (ctrl.username && ctrl.password) {
                PolaflixService.login(ctrl.username, ctrl.password).then(function() {
                    sessionStorage.setItem('usuarioLogueado', ctrl.username);
                    $location.path('/inicio'); 
                }).catch(function(error) {
                    ctrl.mensaje = error;
                });
            } else {
                ctrl.mensaje = "Introduce tu usuario y contraseña.";
            }
        };

        ctrl.registrar = function() {
            if (ctrl.username && ctrl.password && ctrl.iban) {
                var cuota = ctrl.esVip ? 20.0 : 0.0;
                PolaflixService.guardarUsuario(ctrl.username, ctrl.password, ctrl.iban, ctrl.esVip, cuota)
                    .then(function() {
                        sessionStorage.setItem('usuarioLogueado', ctrl.username);
                        $location.path('/inicio');
                    }).catch(function() {
                        ctrl.mensaje = "Hubo un error al crear la cuenta.";
                    });
            } else {
                ctrl.mensaje = "Rellena todos los campos obligatorios.";
            }
        };

        ctrl.toggleModo = function() {
            ctrl.modoRegistro = !ctrl.modoRegistro;
            ctrl.mensaje = '';
        };
    }]
});