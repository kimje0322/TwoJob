package com.blocker.dto;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 			// PK, 사용자 ID

    @Column(nullable=false, unique=true, length=30)
    private String username; 	// 닉네임

    @Column(nullable=false, unique=true, length=50)
    private String email;		// 메일주소
    
    @Column(length = 15)
	private String birthDay;	// 생일
    
    private String password;	// 비밀번호

    @CreationTimestamp
    private Date regdate;		// 등록일(회원가입일)

    @UpdateTimestamp
    private Date updatedate;	// 수정일

    private String role;		// 권한관리
    
	public Account() {
		super();
	}

	public Account(Long id, String username, String email, String birthDay, String password, Date regdate,
			Date updatedate, String role) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.birthDay = birthDay;
		this.password = password;
		this.regdate = regdate;
		this.updatedate = updatedate;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}

