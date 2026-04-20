angular.module('polaflixApp', ['ngRoute'])
    .config(['$locationProvider', '$routeProvider',
        function config($locationProvider, $routeProvider) {
            $locationProvider.hashPrefix('!');

            $routeProvider
                .when('/login', {
                    template: '<login-polaflix></login-polaflix>'
                })
                .when('/series/:serieId', {
                    // USO CORRECTO DE PARÁMETROS EN URL (:serieId)
                    template: '<detalle-serie></detalle-serie>'
                })
                .otherwise('/login');
        }
    ]);
    
angular.module('polaflixApp').controller('MainController', ['$scope', '$location', function($scope, $location) {
    $scope.mostrarMenu = function() {
        // Solo mostramos la barra lateral si no estamos en la pantalla de login
        return $location.path() !== '/login';
    };
}]);