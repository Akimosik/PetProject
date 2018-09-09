
package com.forum.example.ForumExample.Controller.form;


import javax.validation.constraints.Pattern;



public class UserEditForm {
    
    @Pattern(regexp = "^\\p{IsAlphabetic}*$", message = "{Pattern.User.name.validation}")
    private String name;
    
    @Pattern(regexp = "^\\p{IsAlphabetic}*$", message = "{Pattern.User.name.validation}")
    private String lastName;
    

}
