package com.vidhya.vidyaacademy;

public class AddStudent_adapter {
    private String  name, address, parent_name, classname,image;



    public AddStudent_adapter( String name, String address, String parent_name) {


        this.name = name;
        this.address = address;
        this.parent_name = parent_name;
        this.classname = classname;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
