package com.example.firbasemaster;

import androidx.annotation.Nullable;

public class PojoClasss {
    private String name;
    private String age;
    private String key;
    private String dob;
    private String education;
    private String address;
    private String childkey;


    public PojoClasss(String name, String age, String dob, String education, String address, String childkey) {
        this.name = name;
        this.age = age;
        this.dob = dob;
        this.education = education;
        this.address = address;
        this.childkey = childkey;
    }

    public String getChildkey() {
        return childkey;
    }

    public void setChildkey(String childkey) {
        this.childkey = childkey;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public PojoClasss() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof PojoClasss) {
            PojoClasss pojoClasss = (PojoClasss) obj;
            return this.key.equals(pojoClasss.getKey());
        } else
            return false;
    }
}
