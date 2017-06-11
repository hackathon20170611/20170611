(function() {
    'use strict';

    angular
        .module('meetApp')
        .controller('MeetJudgeDeleteController',MeetJudgeDeleteController);

    MeetJudgeDeleteController.$inject = ['$uibModalInstance', 'entity', 'MeetJudge'];

    function MeetJudgeDeleteController($uibModalInstance, entity, MeetJudge) {
        var vm = this;

        vm.meetJudge = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MeetJudge.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
