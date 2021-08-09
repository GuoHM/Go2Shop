package com.go2shop.authentication.model;

public class Authority {
//
//	  @Id
//	    @Column(name = "ID")
	private Long id;

//	    @Column(name = "AUTHORITYNAME", length = 50)
//	    @NotNull
//	    @Enumerated(EnumType.STRING)
	private AuthorityName name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AuthorityName getName() {
		return name;
	}

	public void setName(AuthorityName name) {
		this.name = name;
	}

}
