(function() {
    'use strict';

    angular
        .module('meetApp')
        .controller('MeetJudgeDialogController', MeetJudgeDialogController);

    MeetJudgeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'MeetJudge'];

    function MeetJudgeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, MeetJudge) {
        var vm = this;

        vm.meetJudge = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.meetJudge.id !== null) {
                MeetJudge.update(vm.meetJudge, onSaveSuccess, onSaveError);
            } else {
                MeetJudge.save(vm.meetJudge, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('meetApp:meetJudgeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImage = function ($file, meetJudge) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        meetJudge.image = base64Data;
                        meetJudge.imageContentType = $file.type;
                    });
                });
            }
        };

    }
})();
