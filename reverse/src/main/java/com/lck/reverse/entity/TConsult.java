package com.lck.reverse.entity;

import java.io.Serializable;

public class TConsult implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_consult.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_consult.userName
     *
     * @mbggenerated
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_consult.telPhone
     *
     * @mbggenerated
     */
    private String telphone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_consult.address
     *
     * @mbggenerated
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_consult.position
     *
     * @mbggenerated
     */
    private String position;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_consult.email
     *
     * @mbggenerated
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_consult.consultConent
     *
     * @mbggenerated
     */
    private String consultconent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_consult
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_consult.id
     *
     * @return the value of t_consult.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_consult.id
     *
     * @param id the value for t_consult.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_consult.userName
     *
     * @return the value of t_consult.userName
     *
     * @mbggenerated
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_consult.userName
     *
     * @param username the value for t_consult.userName
     *
     * @mbggenerated
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_consult.telPhone
     *
     * @return the value of t_consult.telPhone
     *
     * @mbggenerated
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_consult.telPhone
     *
     * @param telphone the value for t_consult.telPhone
     *
     * @mbggenerated
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_consult.address
     *
     * @return the value of t_consult.address
     *
     * @mbggenerated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_consult.address
     *
     * @param address the value for t_consult.address
     *
     * @mbggenerated
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_consult.position
     *
     * @return the value of t_consult.position
     *
     * @mbggenerated
     */
    public String getPosition() {
        return position;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_consult.position
     *
     * @param position the value for t_consult.position
     *
     * @mbggenerated
     */
    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_consult.email
     *
     * @return the value of t_consult.email
     *
     * @mbggenerated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_consult.email
     *
     * @param email the value for t_consult.email
     *
     * @mbggenerated
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_consult.consultConent
     *
     * @return the value of t_consult.consultConent
     *
     * @mbggenerated
     */
    public String getConsultconent() {
        return consultconent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_consult.consultConent
     *
     * @param consultconent the value for t_consult.consultConent
     *
     * @mbggenerated
     */
    public void setConsultconent(String consultconent) {
        this.consultconent = consultconent == null ? null : consultconent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_consult
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", telphone=").append(telphone);
        sb.append(", address=").append(address);
        sb.append(", position=").append(position);
        sb.append(", email=").append(email);
        sb.append(", consultconent=").append(consultconent);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}