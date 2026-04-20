angular.module('polaflixApp', ['ngRoute'])
    .config(['$locationProvider', '$routeProvider',
        function config($locationProvider, $routeProvider) {
            $locationProvider.hashPrefix('!');

            $routeProvider
                .when('/login', {
                    template: '<login-polaflix></login-polaflix>'
                })
                .when('/catalogo', {
                    template: '<catalogo-series></catalogo-series>'
                })
                .when('/series/:serieId', {
                    template: '<detalle-serie></detalle-serie>'
                })
                .when('/perfil', {
                    template: '<perfil-usuario></perfil-usuario>'
                })
                .when('/elenco', {
                    template: '<elenco-series></elenco-series>' 
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
}]);