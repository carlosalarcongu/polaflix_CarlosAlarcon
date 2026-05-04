angular.module('polaflixApp').component('perfilUsuario', {
    templateUrl: 'perfil/perfil.template.html',
    controller: ['PolaflixService', '$location', function(PolaflixService, $location) {
        var ctrl = this;
        ctrl.usuario = null;
        ctrl.username = sessionStorage.getItem('usuarioLogueado');
        
        ctrl.editando = false;
        ctrl.form = {};

        ctrl.cambiandoPass = false;
        ctrl.passData = { actual: '', nueva1: '', nueva2: '' };
        ctrl.passError = '';
        ctrl.passExito = '';

        ctrl.$onInit = function() {
            if (!ctrl.username) { $location.path('/login'); return; }
            ctrl.cargarDatos();
        };

        ctrl.cargarDatos = function() {
            PolaflixService.getUsuario(ctrl.username).then(function(data) {
                ctrl.usuario = data;
                ctrl.form.iban = data.iban.numeroCuenta;
                ctrl.form.esVip = data.planSuscripcion.tarifaPlana;
            });
        };

        ctrl.guardarCambios = function() {
            var cuota = ctrl.form.esVip ? 20.0 : 0.0;
            PolaflixService.guardarUsuario(ctrl.username, null, ctrl.form.iban, ctrl.form.esVip, cuota)
                .then(function() {
                    ctrl.editando = false;
                    ctrl.cargarDatos(); 
                });
        };

        ctrl.iniciarCambioPass = function() {
            ctrl.cambiandoPass = !ctrl.cambiandoPass;
            ctrl.passData = { actual: '', nueva1: '', nueva2: '' };
            ctrl.passError = '';
            ctrl.passExito = '';
            ctrl.editando = false;
        };

        ctrl.confirmarCambioPass = function() {
            ctrl.passError = '';
            ctrl.passExito = '';
            
            if (!ctrl.passData.actual || !ctrl.passData.nueva1 || !ctrl.passData.nueva2) {
                ctrl.passError = "Rellena todos los campos.";
                return;
            }
            if (ctrl.passData.nueva1 !== ctrl.passData.nueva2) {
                ctrl.passError = "Las nuevas contraseñas no coinciden.";
                return;
            }

            PolaflixService.cambiarContrasena(ctrl.username, ctrl.passData.actual, ctrl.passData.nueva1)
                .then(function() {
                    ctrl.passExito = "Contraseña actualizada con éxito.";
                    ctrl.cambiandoPass = false;
                })
                .catch(function(err) {
                    ctrl.passError = err;
                });
        };

        ctrl.borrarCuenta = function() {
            if(confirm("¿Estás completamente seguro de querer borrar tu cuenta? Esta acción no se puede deshacer.")) {
                PolaflixService.borrarUsuario(ctrl.username).then(function() {
                    sessionStorage.removeItem('usuarioLogueado');
                    $location.path('/login');
                });
            }
        };

        ctrl.quitarPendiente = function(tituloSerie) {
            PolaflixService.getSeries().then(function(catalogo) {
                var serie = catalogo.find(s => s.titulo === tituloSerie);
                if (serie) {
                    PolaflixService.quitarDePendientes(ctrl.username, serie.id).then(function() {
                        ctrl.cargarDatos(); // Recargamos la lista
                    });
                }
            });
        };
    }]
});