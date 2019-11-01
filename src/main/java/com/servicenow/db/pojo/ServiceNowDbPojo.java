package com.servicenow.db.pojo;

//   https://dev63641.service-now.com/api/now/v1/table/incident
//   http://pojo.sodhanalibrary.com/


import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ServiceNowDbPojo
{
    @ManyToOne
	private SimpleResult result;

    public SimpleResult getResult ()
    {
        return result;
    }

    public void setResult (SimpleResult result)
    {
        this.result = result;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [result = "+result+"]";
    }
}