(function() {
    'use strict';

    angular
        .module('stockpileApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cluster', {
            parent: 'entity',
            url: '/cluster',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Clusters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cluster/clusters.html',
                    controller: 'ClusterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('cluster-detail', {
            parent: 'entity',
            url: '/cluster/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Cluster'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cluster/cluster-detail.html',
                    controller: 'ClusterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Cluster', function($stateParams, Cluster) {
                    return Cluster.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cluster',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cluster-detail.edit', {
            parent: 'cluster-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cluster/cluster-dialog.html',
                    controller: 'ClusterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cluster', function(Cluster) {
                            return Cluster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cluster.new', {
            parent: 'cluster',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cluster/cluster-dialog.html',
                    controller: 'ClusterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cluster', null, { reload: 'cluster' });
                }, function() {
                    $state.go('cluster');
                });
            }]
        })
        .state('cluster.edit', {
            parent: 'cluster',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cluster/cluster-dialog.html',
                    controller: 'ClusterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cluster', function(Cluster) {
                            return Cluster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cluster', null, { reload: 'cluster' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cluster.delete', {
            parent: 'cluster',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cluster/cluster-delete-dialog.html',
                    controller: 'ClusterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cluster', function(Cluster) {
                            return Cluster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cluster', null, { reload: 'cluster' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
