(function() {
    'use strict';

    angular
        .module('stockpileApp')
        .controller('InventoryDialogController', InventoryDialogController);

    InventoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Inventory', 'Item', 'Country', 'Location', 'Agency', 'Cluster'];

    function InventoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Inventory, Item, Country, Location, Agency, Cluster) {
        var vm = this;

        vm.inventory = entity;
        vm.clear = clear;
        vm.save = save;
        vm.items = Item.query();
        vm.countries = Country.query();
        vm.locations = Location.query();
        vm.agencies = Agency.query();
        vm.clusters = Cluster.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.inventory.id !== null) {
                Inventory.update(vm.inventory, onSaveSuccess, onSaveError);
            } else {
                Inventory.save(vm.inventory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('stockpileApp:inventoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
