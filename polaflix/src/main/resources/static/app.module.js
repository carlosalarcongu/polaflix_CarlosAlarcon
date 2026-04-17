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