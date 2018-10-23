package com.chetan.parsing;

public class Token {

	
	private TokenType tokenType;
	private String actualTextValue ;
	
	
	
	public Token(TokenType tokenType, String actualTextValue) {
		super();
		this.tokenType = tokenType;
		this.actualTextValue = actualTextValue;
	}
	
	
	public TokenType getTokenType() {
		return tokenType;
	}
	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}
	public String getActualTextValue() {
		return actualTextValue;
	}
	public void setActualTextValue(String actualTextValue) {
		this.actualTextValue = actualTextValue;
	}
	
	
	
}
