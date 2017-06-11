(function() {
    'use strict';

    angular
        .module('meetApp')
        .controller('MeetJudgeController', MeetJudgeController);

    MeetJudgeController.$inject = ['DataUtils', 'MeetJudge'];

    function MeetJudgeController(DataUtils, MeetJudge) {

        var vm = this;

        vm.meetJudges = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            MeetJudge.query(function(result) {
                vm.meetJudges = result;
                vm.searchQuery = null;
            });
        }
    }
})();
