angular.module('polaflixApp').component('detalleSerie', {
    templateUrl: 'serie/serie.template.html',
    controller: ['$routeParams', 'PolaflixService', function SerieController($routeParams, PolaflixService) {
        var ctrl = this;
        
        // Variables Observadas para la interfaz (Programación Reactiva)
        ctrl.serie = null;
        ctrl.cargando = true;
        ctrl.mensajeError = null;
        ctrl.mensajeExito = null;
        
        // Recuperar usuario logueado o poner uno por defecto
        ctrl.username = sessionStorage.getItem('usuarioLogueado') || 'cr7bicho'; 

        // Recuperar parámetro de la URL
        var serieId = $routeParams.serieId;

        // Llamada asíncrona al servicio al iniciar el componente
        ctrl.$onInit = function() {
            PolaflixService.getSerie(serieId).then(function(data) {
                ctrl.serie = data;
                ctrl.cargando = false;
            }).catch(function(error) {
                ctrl.mensajeError = error;
                ctrl.cargando = false;
            });
        };

        // Lógica para marcar capítulo visto
        ctrl.marcarVisto = function(idCapitulo) {
            ctrl.mensajeError = null;
            ctrl.mensajeExito = null;
            
            PolaflixService.verCapitulo(ctrl.username, idCapitulo).then(function(mensaje) {
                ctrl.mensajeExito = mensaje;
            }).catch(function(error) {
                ctrl.mensajeError = error;
            });
        };
    }]
});