'use strict';

angular.module('practiceScribeApp')
    .config(function ($stateProvider) {

        $stateProvider
            .state('product', {
                parent: 'site',
                url: '/product',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Modules are here'
                },
                views: {
                    'content@': {
                    templateUrl: 'scripts/app/product/product.html'
                    }
                }
            })
    });
