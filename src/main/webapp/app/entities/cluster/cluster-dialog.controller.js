(function() {
    'use strict';

    angular
        .module('stockpileApp')
        .controller('ClusterDialogController', ClusterDialogController);

    ClusterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cluster', 'Inventory'];

    function ClusterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cluster, Inventory) {
        var vm = this;

        vm.cluster = entity;
        vm.clear = clear;
        vm.save = save;
        vm.inventories = Inventory.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cluster.id !== null) {
                Cluster.update(vm.cluster, onSaveSuccess, onSaveError);
            } else {
                Cluster.save(vm.cluster, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('stockpileApp:clusterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
