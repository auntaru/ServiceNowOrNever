package com.servicenow.db.pojo;

public class Opened_by
{
    private String link;

    private String value;

    public String getLink ()
    {
        return link;
    }

    public void setLink (String link)
    {
        this.link = link;
    }

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [link = "+link+", value = "+value+"]";
    }
}