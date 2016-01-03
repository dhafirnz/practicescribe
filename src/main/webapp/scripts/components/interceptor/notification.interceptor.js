 'use strict';

angular.module('practiceScribeApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-practiceScribeApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-practiceScribeApp-params')});
                }
                return response;
            }
        };
    });
