angular.module('polaflixApp', ['ngRoute'])
    .config(['$locationProvider', '$routeProvider',
        function config($locationProvider, $routeProvider) {
            $locationProvider.hashPrefix('!');

            $routeProvider
                .when('/login', {
                    template: '<login-polaflix></login-polaflix>'
                })
                .when('/inicio', {
                    template: '<perfil-usuario></perfil-usuario>'
                })
                .when('/catalogo', {
                    template: '<catalogo-series></catalogo-series>'
                })
                .when('/series/:serieId', {
                    template: '<detalle-serie></detalle-serie>'
                })
                .when('/facturas', {
                    template: '<facturas-usuario></facturas-usuario>'
                })
                .when('/comunidad', {
                    template: '<comunidad-polaflix></comunidad-polaflix>' 
                })
                .otherwise('/login');
        }
    ]);
    
angular.module('polaflixApp').controller('MainController', ['$scope', '$location', function($scope, $location) {
    $scope.mostrarMenu = function() {
        return $location.path() !== '/login';
    };
    
    $scope.getUsername = function() {
        return sessionStorage.getItem('usuarioLogueado') || 'Invitado';
    };

    $scope.logout = function() {
        sessionStorage.removeItem('usuarioLogueado');
        $location.path('/login');
    };
}]);