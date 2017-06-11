(function() {
    'use strict';

    angular
        .module('meetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('meet-judge', {
            parent: 'entity',
            url: '/meet-judge',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'meetApp.meetJudge.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/meet-judge/meet-judges.html',
                    controller: 'MeetJudgeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('meetJudge');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('meet-judge-detail', {
            parent: 'meet-judge',
            url: '/meet-judge/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'meetApp.meetJudge.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/meet-judge/meet-judge-detail.html',
                    controller: 'MeetJudgeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('meetJudge');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MeetJudge', function($stateParams, MeetJudge) {
                    return MeetJudge.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'meet-judge',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('meet-judge-detail.edit', {
            parent: 'meet-judge-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/meet-judge/meet-judge-dialog.html',
                    controller: 'MeetJudgeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MeetJudge', function(MeetJudge) {
                            return MeetJudge.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('meet-judge.new', {
            parent: 'meet-judge',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/meet-judge/meet-judge-dialog.html',
                    controller: 'MeetJudgeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                image: null,
                                imageContentType: null,
                                score: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('meet-judge', null, { reload: 'meet-judge' });
                }, function() {
                    $state.go('meet-judge');
                });
            }]
        })
        .state('meet-judge.edit', {
            parent: 'meet-judge',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/meet-judge/meet-judge-dialog.html',
                    controller: 'MeetJudgeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MeetJudge', function(MeetJudge) {
                            return MeetJudge.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('meet-judge', null, { reload: 'meet-judge' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('meet-judge.delete', {
            parent: 'meet-judge',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/meet-judge/meet-judge-delete-dialog.html',
                    controller: 'MeetJudgeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MeetJudge', function(MeetJudge) {
                            return MeetJudge.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('meet-judge', null, { reload: 'meet-judge' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
