(function() {
    'use strict';

    angular
        .module('stockpileApp')
        .controller('InventoryDetailController', InventoryDetailController);

    InventoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Inventory', 'Item', 'Country', 'Location', 'Agency', 'Cluster'];

    function InventoryDetailController($scope, $rootScope, $stateParams, previousState, entity, Inventory, Item, Country, Location, Agency, Cluster) {
        var vm = this;

        vm.inventory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('stockpileApp:inventoryUpdate', function(event, result) {
            vm.inventory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
