(function() {
    'use strict';

    angular
        .module('meetApp')
        .controller('MeetJudgeDetailController', MeetJudgeDetailController);

    MeetJudgeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'MeetJudge'];

    function MeetJudgeDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, MeetJudge) {
        var vm = this;

        vm.meetJudge = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('meetApp:meetJudgeUpdate', function(event, result) {
            vm.meetJudge = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
