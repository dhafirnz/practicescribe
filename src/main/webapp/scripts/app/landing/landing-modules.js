'use strict';

angular.module('practiceScribeApp')
    .config(function ($stateProvider) {

        $stateProvider
            .state('modules', {
                parent: 'site',
                url: '/modules',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Modules are here'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/landing/landing-modules.html'
                    }
                }
            })
    });
