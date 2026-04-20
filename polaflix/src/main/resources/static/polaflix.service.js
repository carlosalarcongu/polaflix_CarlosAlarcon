angular.module('polaflixApp').factory('PolaflixService', ['$http', '$q', function($http, $q) {
    return {
        // Recuperar todo el catálogo
        getSeries: function() {
            return $http.get('/series')
                .then(res => res.data)
                .catch(err => $q.reject("No se pudo cargar el catálogo."));
        },

        // Recuperar una serie por ID
        getSerie: function(id) {
            return $http.get('/series/' + id)
                .then(res => res.data)
                .catch(err => $q.reject("Serie no encontrada."));
        },

        // Marcar capítulo como visto (PUT)
        verCapitulo: function(user, capId) {
            return $http.put(`/usuarios/${user}/capitulos-vistos/${capId}`)
                .catch(err => $q.reject("Error al registrar visualización."));
        },

        // Agregar a pendientes (PUT) - NUEVO
        agregarAPendientes: function(user, serieId) {
            return $http.put(`/usuarios/${user}/series-pendientes/${serieId}`)
                .catch(err => $q.reject("No se pudo añadir a pendientes."));
        },

        // Añade este método dentro del 'return' de PolaflixService
        getUsuario: function(username) {
            return $http.get('/usuarios/' + username)
                .then(function(response) { return response.data; })
                .catch(function(error) { return $q.reject("No se pudo cargar el perfil."); });
        }
    };
}]);