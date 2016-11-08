(function() {
    'use strict';

    angular
        .module('stockpileApp')
        .controller('AgencyDialogController', AgencyDialogController);

    AgencyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Agency', 'Inventory'];

    function AgencyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Agency, Inventory) {
        var vm = this;

        vm.agency = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
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
            if (vm.agency.id !== null) {
                Agency.update(vm.agency, onSaveSuccess, onSaveError);
            } else {
                Agency.save(vm.agency, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('stockpileApp:agencyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setLogo = function ($file, agency) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        agency.logo = base64Data;
                        agency.logoContentType = $file.type;
                    });
                });
            }
        };

    }
})();
