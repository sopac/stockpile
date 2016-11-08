(function() {
    'use strict';

    angular
        .module('stockpileApp')
        .controller('ClusterController', ClusterController);

    ClusterController.$inject = ['$scope', '$state', 'Cluster'];

    function ClusterController ($scope, $state, Cluster) {
        var vm = this;
        
        vm.clusters = [];

        loadAll();

        function loadAll() {
            Cluster.query(function(result) {
                vm.clusters = result;
            });
        }
    }
})();
