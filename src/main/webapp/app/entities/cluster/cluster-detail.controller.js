(function() {
    'use strict';

    angular
        .module('stockpileApp')
        .controller('ClusterDetailController', ClusterDetailController);

    ClusterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cluster', 'Inventory'];

    function ClusterDetailController($scope, $rootScope, $stateParams, previousState, entity, Cluster, Inventory) {
        var vm = this;

        vm.cluster = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('stockpileApp:clusterUpdate', function(event, result) {
            vm.cluster = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
