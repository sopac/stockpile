(function() {
    'use strict';
    angular
        .module('stockpileApp')
        .factory('Cluster', Cluster);

    Cluster.$inject = ['$resource'];

    function Cluster ($resource) {
        var resourceUrl =  'api/clusters/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
