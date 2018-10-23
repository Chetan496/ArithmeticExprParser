package com.chetan.parsing.ast;

import com.chetan.parsing.Token;

public interface ASTNode {
	
	public Integer evaluate() ;
	
	public void setToken(Token token);
	
	public Token getToken();
		
	public String getValue();
	
	public ASTNode getParent();
	
	public void setParent(ASTNode node);
	
	public ASTNode getLeftChild();  //to get left subtree
	
	public ASTNode getRightChild(); //to get right subtree
	
	public void setLeftChild(ASTNode node);
	
	public void setRightChild(ASTNode node);
	
	
	
}
