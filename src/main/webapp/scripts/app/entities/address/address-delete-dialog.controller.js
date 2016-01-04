'use strict';

angular.module('practiceScribeApp')
	.controller('AddressDeleteController', function($scope, $uibModalInstance, entity, Address) {

        $scope.address = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Address.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
