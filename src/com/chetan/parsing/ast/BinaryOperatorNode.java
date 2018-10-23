package com.chetan.parsing.ast;

import java.util.Objects;

import com.chetan.parsing.Token;

public class BinaryOperatorNode implements ASTNode {
	
	private ASTNode parent;
	private ASTNode leftChild;
	private ASTNode rightChild;
	private Token token;
	private long creationTimeStamp;

	
	public BinaryOperatorNode() {
		this.leftChild = null;
		this.rightChild = null;
		this.parent = null;
		this.creationTimeStamp = System.nanoTime();
	}
	
	public long getCreationTimeStamp() {
		
		return this.creationTimeStamp ;
	}
	
	@Override
	public Integer evaluate() {
		// TODO Auto-generated method stub
		
		switch (this.token.getTokenType()) {
		case MULTIPLY:
			return ( this.getLeftChild().evaluate() * this.getRightChild().evaluate() ); 
			
		
		case DIVIDE:
			return ( this.getLeftChild().evaluate() / this.getRightChild().evaluate() ); 
			
		case PLUS:			
			return ( this.getLeftChild().evaluate() + this.getRightChild().evaluate() ); 
			
		case MINUS:		
			return ( this.getLeftChild().evaluate() - this.getRightChild().evaluate() ); 
			
		default:
			break;
			
			
		}
		
		return null;
	}



	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return this.token.getActualTextValue() ;
	}

	@Override
	public ASTNode getParent() {
		// TODO Auto-generated method stub
		return this.parent;
	}

	@Override
	public ASTNode getLeftChild() {
		// TODO Auto-generated method stub
		return this.leftChild ;
	}

	@Override
	public ASTNode getRightChild() {
		// TODO Auto-generated method stub
		return this.rightChild ;
	}

	@Override
	public void setLeftChild(ASTNode node) {
		// TODO Auto-generated method stub
		this.leftChild = node;

	}

	@Override
	public void setRightChild(ASTNode node) {
		// TODO Auto-generated method stub
		this.rightChild = node;

	}

	@Override
	public void setParent(ASTNode node) {
		// TODO Auto-generated method stub
		this.parent = node;
		
	}

	@Override
	public void setToken(Token token) {
		// TODO Auto-generated method stub
		this.token = token;
		
	}

	@Override
	public Token getToken() {
		// TODO Auto-generated method stub
		return this.token;
		
	}
	
	@Override
	public boolean equals(Object o) {
		
		
		if(o == this) {
			return true;
		}
		
		if( !(o instanceof BinaryOperatorNode) ) {
			
			return false;
		}
		
		
		BinaryOperatorNode other = (BinaryOperatorNode) o ;
		return (other.getCreationTimeStamp() == this.creationTimeStamp 
				&& other.getToken().getActualTextValue().equals(this.getToken().getActualTextValue())  
				&& other.getToken().getTokenType().equals(this.getToken().getTokenType()) 
			   ) ;
	}
	
	@Override
	public int hashCode() {
		
		return Objects.hash(  this.creationTimeStamp, this.getToken().getActualTextValue() , this.getToken().getTokenType() );
	}
	
	
	

}
