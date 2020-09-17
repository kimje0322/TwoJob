package com.blocker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class User {
   @Id
   @Column(name = "user_id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private String userId;

//   @JsonIgnore
   @Column(name = "user_pwd")
   private String userPwd;
   @Column(name = "user_email")
   private String userEmail;

   @Column(name = "user_joindate", insertable = false, updatable = false)
   private String userJoindate;
//    private LocalDateTime createDate;

   @Column(name = "user_name")
   private String userName;
   @Column(name = "user_nickname")
   private String userNickname;
   @Column(name = "user_phone")
   private String userPhone;
   @Column(name = "user_comment")
   private String userComment;
   @Column(name = "user_img")
   private String userImg;

   public String getUserImg() {
      return userImg;
   }

   public void setUserImg(String userImg) {
      this.userImg = userImg;
   }
   @Builder
   public User(String userPwd, String userEmail, String userName, String userNickname, String userPhone, String userComment) {
      super();
      this.userPwd = userPwd;
      this.userEmail = userEmail;
      this.userName = userName;
      this.userNickname = userNickname;
      this.userPhone = userPhone;
      this.userComment = userComment;
   }
   
   @Builder
   public User(String userPwd, String userEmail,String userNickname,String userComment) {
      super();
      this.userPwd = userPwd;
      this.userEmail = userEmail;
      this.userNickname = userNickname;
      this.userComment = userComment;
   }

   @Override
   public String toString() {
      return "User [userId=" + userId + ", userPwd=" + userPwd + ", userEmail=" + userEmail + ", userJoindate="
            + userJoindate + ", userName=" + userName + ", userNickname=" + userNickname + ", userPhone="
            + userPhone + ", usercomment=" + userComment + "]";
   }
   
}