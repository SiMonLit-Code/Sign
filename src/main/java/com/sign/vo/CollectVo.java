package com.sign.vo;

import cn.hutool.core.lang.copier.SrcToDestCopier;
import com.sign.entity.Collect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CollectVo extends Collect {

    private String card ;
    private String soldier ;
    private String exam;

}
