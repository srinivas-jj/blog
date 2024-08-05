package com.example.blog.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "role")
public class Roles {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name ="id")
    private int id;

    @Column(name ="name")
    private String roleName;

     @ManyToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
                fetch=FetchType.LAZY)
    @JoinTable(name="user_role",
               joinColumns= @JoinColumn(name="role_id"),
               inverseJoinColumns = @JoinColumn(name="user_id"))
    private List<UserDetail> userDetails;


    public Roles(){

    }


    public Roles(String roleName) {
        this.roleName = roleName;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getRoleName() {
        return roleName;
    }


    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public List<UserDetail> getUserDetails() {
        return userDetails;
    }


    public void setUserDetails(List<UserDetail> userDetails) {
        this.userDetails = userDetails;
    }


    @Override
    public String toString() {
        return "Roles [id=" + id + ", roleName=" + roleName + "]";
    }
    
    
    
}
