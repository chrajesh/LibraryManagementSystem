
$(document).ready(function(){
    // alert("loaded") ;
    try{
        $(".CreateOrEditClerk").click(function(){
            //alert("he clieck on new clerk page");
            var name=$.trim($("#name").val());
            var qua=$.trim($("#qualification").val());
            var address=$.trim($("#address").val());
            var emailId=$.trim($("#emailId").val());
            var contactNo=$.trim($("#contactNo").val());
            var txtUsername=$.trim($("#txtUsername").val());
            var txtPassword=$.trim($("#txtPassword").val());
            var txtConfirmPassword=$.trim($("#txtConfirmPassword").val());
            var gender1=$("#gender1:checked").val();
            var gender2=$("#gender2:checked").val();
        
            var pwdcheck=0;
            if(txtPassword.length!=0 && txtConfirmPassword.length!=0 && (txtPassword==txtConfirmPassword)){
                $("#PasswordEL").html("");
                pwdcheck=1;
            }else{
                $("#PasswordEL").html("Password and confirm password are not matched");
            }
            var checkGender=0;
            // alert("gender1..."+gender1 +  "\ngender2...."+gender2);
            if(gender1!=undefined && gender2==undefined){
                $("#GenderEL").html("");
                checkGender=1;
            }
            if(gender1==undefined && gender2!=undefined){
                $("#GenderEL").html("");
                checkGender=1;
            }
            if(gender1==undefined && gender2==undefined){
                $("#GenderEL").html("Please select the gender");
            }
            if(name!="" && qua!="" && address!="" && address!="" && emailId!="" && contactNo!="" && txtUsername!="" && txtPassword!="" && txtConfirmPassword!="" && checkGender==1 && pwdcheck==1){
                return true;
            }else{
                $("#ClerkEL").html("Please Fill All Fields in Form");
                return false;
            }
        });
        $(".CreateOrEditStudent").click(function(){
            //alert("he clieck on new clerk page");
            var name=$.trim($("#name").val());
            var branch=$.trim($("#branch").val());
            var batch=$.trim($("#batch").val());
            var contact=$.trim($("#contact").val());
            var emailId=$.trim($("#emailId").val());
            var admNo=$.trim($("#admNo").val());
            var address=$.trim($("#address").val());
            var dob=$.trim($("#dob").val());
            var gender1=$("#gender1:checked").val();
            var gender2=$("#gender2:checked").val();
        
            
            var checkGender=0;
            // alert("gender1..."+gender1 +  "\ngender2...."+gender2);
            if(gender1!=undefined && gender2==undefined){
                $("#GenderEL").html("");
                checkGender=1;
            }
            if(gender1==undefined && gender2!=undefined){
                $("#GenderEL").html("");
                checkGender=1;
            }
            if(gender1==undefined && gender2==undefined){
                $("#GenderEL").html("Please select the gender");
            }
            if(name!="" && branch!="" && batch!="" && contact!="" && emailId!="" && admNo!="" && address!=""  && dob!="" && checkGender==1){
                return true;
            }else{
                $("#StudentEL").html("Please Fill All Fields in Form");
                return false;
            }
        });
        $(".CreateOrEditStaff").click(function(){
            //alert("he clieck on new clerk page");
            var name=$.trim($("#name").val());
            var desig=$.trim($("#desig").val());
            var department=$.trim($("#department").val());
            var contact=$.trim($("#contact").val());
            var emailId=$.trim($("#emailId").val());
            var doj=$.trim($("#doj").val());
            var address=$.trim($("#address").val());
            var dob=$.trim($("#dob").val());
            var gender1=$("#gender1:checked").val();
            var gender2=$("#gender2:checked").val();
        
            
            var checkGender=0;
            // alert("gender1..."+gender1 +  "\ngender2...."+gender2);
            if(gender1!=undefined && gender2==undefined){
                $("#GenderEL").html("");
                checkGender=1;
            }
            if(gender1==undefined && gender2!=undefined){
                $("#GenderEL").html("");
                checkGender=1;
            }
            if(gender1==undefined && gender2==undefined){
                $("#GenderEL").html("Please select the gender");
            }
            if(name!="" && desig!="" && department!="" && contact!="" && emailId!="" && doj!="" && address!=""  && dob!="" && checkGender==1){
                return true;
            }else{
                $("#StaffEL").html("Please Fill All Fields in Form");
                return false;
            }
        });
        $(".CreateOrEditBook").click(function(){
            //alert("he clieck on new clerk page");
            var title=$.trim($("#title").val());
            var author=$.trim($("#author").val());
            var subject=$.trim($("#subject").val());
            var edition=$.trim($("#edition").val());
            var publication=$.trim($("#publication").val());
            var price=$.trim($("#price").val());
            var isbn=$.trim($("#isbn").val());
            var totalNoOfBooks=$.trim($("#totalNoOfBooks").val());
           
            if(title!="" && author!="" && subject!="" && edition!="" && publication!="" && price!="" && isbn!=""  && totalNoOfBooks!="" && totalNoOfBooks!=0){
                return true;
            }else{
                $("#BookEL").html("Please Fill All Fields in Form");
                return false;
            }
        });
        $(".IssueBook").click(function(){
            //alert("he clieck on new clerk page");
            var mcode=$.trim($("#mcode").val());
            var accno=$.trim($("#accno").val());
            var personType=$.trim($("#personType").val());
     
           
            if(mcode!="" && accno!="" && accno!="none" && personType!="none"){
                return true;
            }else{
                $("#IssueBookEL").html("Please Fill All Fields in Form");
                return false;
            }
        });
        $(".ReturnBook").click(function(){
            //alert("he clieck on new clerk page");
            var mcode=$.trim($("#mcode").val());
            var accno=$.trim($("#accno").val());
            var personType=$.trim($("#personType").val());
     
           
            if(mcode!="" && accno!="" && personType!="none"){
                return true;
            }else{
                $("#ReturnBookEL").html("Please Fill All Fields in Form");
                return false;
            }
        });
      
        
        
        $(".DeleteClerk").click(function(){
            var v1=prompt("Do You Really Want To Delete This Clerk?");
            //alert("value....."+v1);
            if(v1==""){
                return true;
            }else{
                return false;
            }
        });
        $(".DeleteStudent").click(function(){
            var v1=prompt("Do You Really Want To Delete This Student?");
            //alert("value....."+v1);
            if(v1==""){
                return true;
            }else{
                return false;
            }
           
        });
        $(".DeleteStaff").click(function(){
            var v1=prompt("Do You Really Want To Delete This Staff Member?");
            //alert("value....."+v1);
            if(v1==""){
                return true;
            }else{
                return false;
            }
           
        });
        $(".DeleteBook").click(function(){
            var v1=prompt("Do You Really Want To Delete This Book?");
            //alert("value....."+v1);
            if(v1==""){
                return true;
            }else{
                return false;
            }
           
        });
        $(".GetReports").click(function(){
            var gender1=$(".report:checked").val();
            //alert("gender1..."+gender1)
            if(gender1!="" && gender1!= undefined  && gender1!=null){
                if(gender1=="5"){
                    var fdate=$("#fromDate").val();
                    var tdate=$("#toDate").val();
                    
                    if((fdate!="" && fdate!="YYYY-MM-DD" && fdate!=undefined && fdate!=null) && (tdate!="" && tdate!="YYYY-MM-DD"  && tdate!=undefined && tdate!=null)){
                        return true;
                    }else{
                        $("#ReportsEL").html("Please specify the dates");
                        return false;
                    }
                }else{
                    $("#fromDate").val("YYYY-MM-DD");
                    $("#toDate").val("YYYY-MM-DD");
                }
            }else{
                $("#ReportsEL").html("Please select atleast one report type");
                return false;
            }
           
           
        });
        
        $(".betweenDates").click(function(){
            $("#fromDate").val("").removeAttr("disabled");
            $("#toDate").val("").removeAttr("disabled");
            
        });
        $(".otherRadio").click(function(){
            $("#fromDate").val("YYYY-MM-DD").attr("disabled",true);
            $("#toDate").val("YYYY-MM-DD").attr("disabled",true);
            
            
        });
    
    }
    catch(e){
        alert("Error::"+e);
    }
});

