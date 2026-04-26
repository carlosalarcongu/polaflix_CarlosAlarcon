angular.module('polaflixApp').component('loginPolaflix', {
    templateUrl: 'login/login.template.html',
    controller: ['$location', 'PolaflixService', function LoginController($location, PolaflixService) {
        var ctrl = this;
        ctrl.modoRegistro = false; 
        
        // Formulario
        ctrl.username = '';
        ctrl.password = '';
        ctrl.iban = '';
        ctrl.esVip = false;
        
        ctrl.mensaje = '';

        ctrl.entrar = function() {
            if (ctrl.username) {
                PolaflixService.getUsuario(ctrl.username).then(function() {
                    sessionStorage.setItem('usuarioLogueado', ctrl.username);
                    $location.path('/catalogo'); 
                }).catch(function() {
                    ctrl.mensaje = "El usuario no existe. Regístrate primero.";
                });
            }
        };

        ctrl.registrar = function() {
            if (ctrl.username && ctrl.password && ctrl.iban) {
                var cuota = ctrl.esVip ? 20.0 : 0.0;
                PolaflixService.guardarUsuario(ctrl.username, ctrl.password, ctrl.iban, ctrl.esVip, cuota)
                    .then(function() {
                        sessionStorage.setItem('usuarioLogueado', ctrl.username);
                        $location.path('/catalogo');
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