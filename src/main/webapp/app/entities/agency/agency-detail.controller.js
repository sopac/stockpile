(function() {
    'use strict';

    angular
        .module('stockpileApp')
        .controller('AgencyDetailController', AgencyDetailController);

    AgencyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Agency', 'Inventory'];

    function AgencyDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Agency, Inventory) {
        var vm = this;

        vm.agency = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('stockpileApp:agencyUpdate', function(event, result) {
            vm.agency = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
