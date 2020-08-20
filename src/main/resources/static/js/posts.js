function register(){
    var date = document.getElementById('date').value;
    var fName = document.getElementById('fName').value;
    var sName = document.getElementById('sName').value;
    var address1 = document.getElementById('address1').value;
    var address2 = document.getElementById('address2').value;
    var city = document.getElementById('city').value;
    var postCode = document.getElementById('postCode').value;
    var phoneNumber = document.getElementById('phoneNumber').value;
    var emergencyNumber = document.getElementById('emergencyNumber').value;
    var email = document.getElementById('email').value;
    if (document.getElementById('tenant1').checked){
        var tenant = "yes";
    }
    else if (document.getElementById('tenant2').checked){
        var tenant = "no";
    }
    else{
        var tenant = "";
    }
    if (document.getElementById('volunteered1').checked){
        var volunteered = "yes";
    }
    else if (document.getElementById('volunteered2').checked){
        var volunteered = "no";
    }
    else{
        var volunteered = "";
    }
    var volunteeredRole = document.getElementById('volunteeredRole').value;
    if (document.getElementById('disabilities1').checked){
        var disabilities = "yes";
    }
    else if (document.getElementById('disabilities2').checked){
        var disabilities = "no";
    }
    else{
        var disabilities = "";
    }
    var disabilityDetails = document.getElementById('disabilityDetails').value;
    if (document.getElementById('medications1').checked){
        var medications = "yes";
    }
    else if (document.getElementById('medications2').checked){
        var medications = "no";
    }
    else{
        var medications = "";
    }
    var medicationDetails = document.getElementById('medicationDetails').value;
    if (document.getElementById('allergies1').checked){
        var allergies = "yes";
    }
    else if (document.getElementById('allergies2').checked){
        var allergies = "no";
    }
    else{
        var allergies = "";
    }
    var allergyDetails = document.getElementById('allergyDetails').value;
    $.post("/register",
    {
        date : date,
        fName : fName,
        sName : sName,
        address1 : address1,
        address2 : address2,
        city : city,
        postCode : postCode,
        phoneNumber : phoneNumber,
        emergencyNumber : emergencyNumber,
        email : email,
        tenant : tenant,
        volunteered : volunteered,
        volunteeredRole : volunteeredRole,
        disabilities : disabilities,
        disabilityDetails : disabilityDetails,
        medications : medications,
        medicationDetails : medicationDetails,
        allergies : allergies,
        allergyDetails : allergyDetails
    },
    function(status)
    {
        if(status==="Success")
        {
            document.getElementById('form_result').innerHTML = '<div class="alert alert-success" role="alert">Logging in</div>';
            window.location.href = "/";
        }
        else if(status==="NoEmail")
        {
            document.getElementById('form_result').innerHTML = '<div class="alert alert-danger" role="alert">No email given</div>';
        }
        else if(status==="NoPassword")
        {
            document.getElementById('form_result').innerHTML = '<div class="alert alert-danger" role="alert">No password given</div>';
        }
        else
        {
            document.getElementById('form_result').innerHTML = '<div class="alert alert-danger" role="alert">Something went wrong!</div>';
        }
    }
);}