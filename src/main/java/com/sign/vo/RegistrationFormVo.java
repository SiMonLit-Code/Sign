package com.sign.vo;

import com.sign.entity.RegistrationForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegistrationFormVo extends RegistrationForm {

    private String card ;
    private String soldier ;
    private String exam;

}
