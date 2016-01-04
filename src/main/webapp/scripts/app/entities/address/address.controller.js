'use strict';

angular.module('practiceScribeApp')
    .controller('AddressController', function ($scope, $state, Address, AddressSearch) {

        $scope.addresss = [];
        $scope.loadAll = function() {
            Address.query(function(result) {
               $scope.addresss = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            AddressSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.addresss = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.address = {
                streetNum: null,
                streetName: null,
                poBoxNum: null,
                suburb: null,
                town: null,
                city: null,
                country: null,
                id: null
            };
        };
    });
