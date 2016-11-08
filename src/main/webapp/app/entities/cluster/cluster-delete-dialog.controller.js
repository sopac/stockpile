(function() {
    'use strict';

    angular
        .module('stockpileApp')
        .controller('ClusterDeleteController',ClusterDeleteController);

    ClusterDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cluster'];

    function ClusterDeleteController($uibModalInstance, entity, Cluster) {
        var vm = this;

        vm.cluster = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cluster.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
