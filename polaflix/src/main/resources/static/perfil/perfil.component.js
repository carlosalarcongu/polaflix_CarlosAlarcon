angular.module('polaflixApp').component('perfilUsuario', {
    templateUrl: 'perfil/perfil.template.html',
    controller: ['PolaflixService', '$location', '$q', function(PolaflixService, $location, $q) {
        var ctrl = this;
        ctrl.usuario = null;
        ctrl.username = sessionStorage.getItem('usuarioLogueado');
        
        ctrl.editando = false;
        ctrl.form = {};

        ctrl.cambiandoPass = false;
        ctrl.passData = { actual: '', nueva1: '', nueva2: '' };
        ctrl.passError = '';
        ctrl.passExito = '';

        ctrl.seriesUsuario = [];

        ctrl.$onInit = function() {
            if (!ctrl.username) { $location.path('/login'); return; }
            ctrl.cargarDatos();
        };

        ctrl.cargarDatos = function() {
            $q.all([
                PolaflixService.getUsuario(ctrl.username),
                PolaflixService.getSeries()
            ]).then(function(res) {
                ctrl.usuario = res[0];
                var catalogo = res[1];

                ctrl.form.iban = ctrl.usuario.iban.numeroCuenta;
                ctrl.form.esVip = ctrl.usuario.planSuscripcion.tarifaPlana;

                ctrl.seriesUsuario = [];
                if(ctrl.usuario.estadoSeries) {
                    for (var idSerie in ctrl.usuario.estadoSeries) {
                        var serieInfo = catalogo.find(s => s.id === idSerie);
                        if(serieInfo) {
                            ctrl.seriesUsuario.push({
                                id: serieInfo.id,
                                titulo: serieInfo.titulo,
                                estado: ctrl.usuario.estadoSeries[idSerie],
                                poster: ctrl.getPosterGeneral(serieInfo.id)
                            });
                        }
                    }
                }
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
            if(confirm("¿Estás seguro de borrar tu cuenta?")) {
                PolaflixService.borrarUsuario(ctrl.username).then(function() {
                    sessionStorage.removeItem('usuarioLogueado');
                    $location.path('/login');
                });
            }
        };

        ctrl.quitarPendiente = function(idSerie) {
            PolaflixService.quitarDePendientes(ctrl.username, idSerie).then(function() {
                ctrl.cargarDatos();
            });
        };

        ctrl.getPosterGeneral = function(idSerie) {
            if (idSerie === 1) return 'images/PeakyBlinders.png';
            if (idSerie === 2) return 'images/PrisonBreakS01.png';
            if (idSerie === 3) return 'images/LQSAS01.png';
            return 'images/polaflix-logo.png';
        };
    }]
});