angular.module('polaflixApp').factory('PolaflixService', ['$http', '$q', function($http, $q) {
    return {
        
        getSeries: function() {
            return $http.get('/series')
                .then(res => res.data)
                .catch(err => $q.reject("No se pudo cargar el catálogo."));
        },

        
        getSerie: function(id) {
            return $http.get('/series/' + id)
                .then(res => res.data)
                .catch(err => $q.reject("Serie no encontrada."));
        },

        verCapitulo: function(user, capId) {
            return $http.put(`/usuarios/${user}/capitulos-vistos/${capId}`)
                .then(function(response) {
                    return "¡Capítulo marcado como visto con éxito!"; 
                })
                .catch(function(err) {
                    return $q.reject("Error al registrar visualización.");
                });
        },

        agregarAPendientes: function(user, serieId) {
            return $http.put(`/usuarios/${user}/series-pendientes/${serieId}`)
                .catch(err => $q.reject("No se pudo añadir a pendientes."));
        },

        
        getUsuario: function(username) {
            return $http.get('/usuarios/' + username)
                .then(function(response) { return response.data; })
                .catch(function(error) { return $q.reject("No se pudo cargar el perfil."); });
        }
    };
}]);