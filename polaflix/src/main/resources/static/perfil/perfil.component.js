angular.module('polaflixApp').component('perfilUsuario', {
    templateUrl: 'perfil/perfil.template.html',
    controller: ['PolaflixService', '$location', function(PolaflixService, $location) {
        var ctrl = this;
        ctrl.usuario = null;
        ctrl.username = sessionStorage.getItem('usuarioLogueado');
        ctrl.editando = false;
        ctrl.form = {};

        ctrl.$onInit = function() {
            if (!ctrl.username) { $location.path('/login'); return; }
            ctrl.cargarDatos();
        };

        ctrl.cargarDatos = function() {
            PolaflixService.getUsuario(ctrl.username).then(function(data) {
                ctrl.usuario = data;
                ctrl.form.iban = data.iban.numeroCuenta;
                ctrl.form.esVip = data.planSuscripcion.tarifaPlana;
                ctrl.form.password = ''; 
            });
        };

        ctrl.guardarCambios = function() {
            var cuota = ctrl.form.esVip ? 20.0 : 0.0;
            PolaflixService.guardarUsuario(ctrl.username, ctrl.form.password, ctrl.form.iban, ctrl.form.esVip, cuota)
                .then(function() {
                    ctrl.editando = false;
                    ctrl.cargarDatos(); 
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