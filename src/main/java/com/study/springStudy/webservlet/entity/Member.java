package com.study.springStudy.webservlet.entity;

import java.util.Objects;

public class Member {
    private String account;
    private String password;
    private String name;

    public Member(String account, String password, String name) {
        this.account = account;
        this.password = password;
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(account, member.account) && Objects.equals(password, member.password) && Objects.equals(name, member.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, password, name);
    }

    @Override
    public String toString() {
        return "Member{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
