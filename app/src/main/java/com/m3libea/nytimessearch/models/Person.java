
package com.m3libea.nytimessearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Person {

    @SerializedName("qualifier")
    @Expose
    public String qualifier;
    @SerializedName("firstname")
    @Expose
    public String firstname;
    @SerializedName("middlename")
    @Expose
    public String middlename;
    @SerializedName("lastname")
    @Expose
    public String lastname;
    @SerializedName("rank")
    @Expose
    public Integer rank;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("organization")
    @Expose
    public String organization;

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Person() {
    }
}
