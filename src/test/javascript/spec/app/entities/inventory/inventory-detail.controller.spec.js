'use strict';

describe('Controller Tests', function() {

    describe('Inventory Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockInventory, MockItem, MockCountry, MockLocation, MockAgency, MockCluster;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockInventory = jasmine.createSpy('MockInventory');
            MockItem = jasmine.createSpy('MockItem');
            MockCountry = jasmine.createSpy('MockCountry');
            MockLocation = jasmine.createSpy('MockLocation');
            MockAgency = jasmine.createSpy('MockAgency');
            MockCluster = jasmine.createSpy('MockCluster');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Inventory': MockInventory,
                'Item': MockItem,
                'Country': MockCountry,
                'Location': MockLocation,
                'Agency': MockAgency,
                'Cluster': MockCluster
            };
            createController = function() {
                $injector.get('$controller')("InventoryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'stockpileApp:inventoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
