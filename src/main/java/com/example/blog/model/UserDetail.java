package com.example.blog.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="user")
public class UserDetail {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String passWord;

    @OneToMany(mappedBy="author", cascade=CascadeType.ALL)
    private List<BlogPost> blogPost;

    @OneToMany(mappedBy="userDetail", cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    @JsonBackReference
    private List<Comment> comment;

    public List<BlogPost> getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(List<BlogPost> blogPost) {
        this.blogPost = blogPost;
    }

    @ManyToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
                fetch=FetchType.LAZY)
    @JoinTable(name="user_role",
               joinColumns= @JoinColumn(name="user_id"),
               inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Roles> roles;

    public UserDetail(){

    }

    public UserDetail(String userName, String passWord, List<Roles> roles) {
        this.userName = userName;
        this.passWord = passWord;
        this.roles = roles;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDetails [id=" + id + ", userName=" + userName + ", email=" + email + ", passWord=" + passWord + "]";
    }

    
    

}
