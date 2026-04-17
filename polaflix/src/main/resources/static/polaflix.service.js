angular.module('polaflixApp').factory('PolaflixService', ['$http', '$q', function($http, $q) {
    return {
        // Recuperar una serie completa
        getSerie: function(idSerie) {
            return $http.get('/series/' + idSerie)
                .then(function(response) {
                    return response.data; // 200 OK
                })
                .catch(function(error) {
                    return $q.reject("Error al cargar la serie. Código: " + error.status); // 404 u otros
                });
        },
        
        // Marcar capítulo como visto (PUT)
        verCapitulo: function(username, idCapitulo) {
            return $http.put('/usuarios/' + username + '/capitulos-vistos/' + idCapitulo)
                .then(function(response) {
                    return "Capítulo marcado como visto con éxito.";
                })
                .catch(function(error) {
                    return $q.reject("No se pudo registrar la visualización. Verifica el usuario/capítulo.");
                });
        }
    };
}]);