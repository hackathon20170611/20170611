(function() {
    'use strict';
    angular
        .module('meetApp')
        .factory('MeetJudge', MeetJudge);

    MeetJudge.$inject = ['$resource'];

    function MeetJudge ($resource) {
        var resourceUrl =  'api/meet-judges/:id';

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
