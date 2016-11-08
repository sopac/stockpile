(function() {
    'use strict';

    angular
        .module('stockpileApp')
        .controller('AgencyController', AgencyController);

    AgencyController.$inject = ['$scope', '$state', 'DataUtils', 'Agency'];

    function AgencyController ($scope, $state, DataUtils, Agency) {
        var vm = this;
        
        vm.agencies = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Agency.query(function(result) {
                vm.agencies = result;
            });
        }
    }
})();
