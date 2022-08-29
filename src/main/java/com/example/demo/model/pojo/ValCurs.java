//root
package com.example.demo.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "ValCurs")
public class ValCurs {

    @JacksonXmlElementWrapper(useWrapping = false)

    private List<ValType> listValType;


    @XmlElement(name = "ValType")
    public List<ValType> getListValType() {
        return listValType;
    }

    public void setListValType(List<ValType> listValType) {
        this.listValType = listValType;
    }
}
