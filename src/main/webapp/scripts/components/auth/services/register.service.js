'use strict';

angular.module('practiceScribeApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


