angular.module('polaflixApp').component('facturasUsuario', {
    templateUrl: 'facturas/facturas.template.html',
    controller: ['PolaflixService', '$location', function(PolaflixService, $location) {
        var ctrl = this;
        ctrl.username = sessionStorage.getItem('usuarioLogueado');
        ctrl.facturas = [];

        ctrl.$onInit = function() {
            if (!ctrl.username) { $location.path('/login'); return; }
            PolaflixService.getUsuario(ctrl.username).then(function(u) {
                if (u.facturas && u.facturas.length > 0) {
                    ctrl.facturas = u.facturas.sort((a,b) => b.anio - a.anio || b.mes - a.mes);
                }
            });
        };

        ctrl.getNombreMes = function(mesNum) {
            const meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
            return meses[mesNum - 1] || "Mes";
        };
        
        ctrl.calcularTotal = function(factura) {
            if(!factura || !factura.lineas) return 0;
            return factura.lineas.reduce((total, l) => total + l.cargo, 0).toFixed(2);
        };
    }]
});