export default class SurveyFormCreate {
    
    constructor() {
        $("#survey-form").submit(function() {
            $("#survey-schema").val($("#render").val());
        });
    }
    
}