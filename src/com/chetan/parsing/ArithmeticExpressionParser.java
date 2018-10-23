package com.chetan.parsing;

import com.chetan.parsing.ast.ASTNode;

/*
 * 
 * The full grammar:
 * 
 * Expression -> Term RestOfTheExpression
 * RestOfTheExpression -> PLUS Term RestOfTheExpression
 * RestOfTheExpression -> MINUS Term RestOfTheExpression
 * RestOfTheExpression -> EPSILON
 * Term -> Factor RestOfTheTerm
 * RestOfTheTerm -> MULTIPLY Factor RestOfTheTerm
 * RestOfTheTerm -> DIVIDE Factor RestOfTheTerm
 * RestOfTheTerm -> EPSILON
 * Factor -> IntegerNumber
 * Factor -> L_PAREN Expression R_Paren
 * 
 * IntegerNumber -> NonZeroDigit RestOfTheDigits
 * RestOfTheDigits -> Digit RestOfTheDigits
 * RestOfTheDigits -> EPSILON
 * Digit -> ZERO|ONE|TWO|...|NINE
 * NonZeroDigit -> ONE|TWO|THREE|..|NINE
 * 
 * 
 * 23x4
 */


/*for building a Parse tree, need to use the Shunting-Yard algorithm*/
public class ArithmeticExpressionParser {

	private Scanner scanner; 
	private Token currentToken ; 
	
	public void parse(String arithmeticExpr) {
		
		this.scanner = new Scanner(arithmeticExpr);
		this.currentToken = scanner.getNextToken() ; //point to the first token
		ASTNode exprNode = this.expression(); //trigger the parsing..
		
	}
	
	
	private ASTNode expression() {
		
		System.out.println("Expression -> Term RestOfTheExpression");
		/*call term() and then call RestOfTheExpression */
		term();
		restOfTheExpression();
		
		return null;
	}
	
	
	private  ASTNode term() {
		
		System.out.println("Term -> Factor RestOfTheTerm");
		
		factor();
		restOfTheTerm();
		
		return null;
		
	}
	
	
	private ASTNode restOfTheExpression() {
		TokenType tokenType = currentToken.getTokenType() ;
		if(tokenType == TokenType.PLUS) {
			System.out.println("RestOfTheExpression -> PLUS Term RestOfTheExpression");
			currentToken = scanner.getNextToken();
			term();
			restOfTheExpression();
			
		}else if(tokenType == TokenType.MINUS) {
			
			System.out.println("RestOfTheExpression -> MINUS Term RestOfTheExpression");
			currentToken = scanner.getNextToken();
			term();
			restOfTheExpression();
			
		}else  {

			System.out.println(" RestOfTheExpression -> EPSILON");
		}
		
		return null;
		
	}
	
	
	
	
	private ASTNode restOfTheTerm() {
		
		TokenType tokenType = currentToken.getTokenType() ;
		if(tokenType == TokenType.MULTIPLY) {
			
			System.out.println("RestOfTheTerm -> MULTIPLY Factor RestOfTheTerm");
			currentToken = scanner.getNextToken() ;
			factor();
			restOfTheTerm();
			
		}else if(tokenType == TokenType.DIVIDE) {
			
			System.out.println("RestOfTheTerm -> DIVIDE Factor RestOfTheTerm");
			currentToken = scanner.getNextToken() ;
			factor();
			restOfTheTerm();
			
		}else {
			System.out.println("RestOfTheTerm -> EPSILON");
			
		}
		
		return null;
		
	}
	
	
	private ASTNode factor() {
		
		if(currentToken.getTokenType() == TokenType.L_PAREN) {
			
			System.out.println(" Factor -> L_PAREN Expression R_Paren");
			leftParen();
			expression();
			rightParen();
			
		}else {
			System.out.println("Factor -> IntegerNumber");
			integerNumber();
		}
		
		return null;
		
	}
	
	
	private ASTNode integerNumber() {
		System.out.println("IntegerNumber -> NonZeroDigit RestOfTheDigits");
		nonzeroDigit();
		restOfTheDigits();
		
		
		return null;
	}
	

	private ASTNode digit() {
		
		System.out.println("Digit -> ZERO|ONE|TWO|...|NINE");
		
		TokenType currentTokenType = currentToken.getTokenType() ;
		boolean isValidToken = currentTokenType == TokenType.ZERO || currentTokenType == TokenType.ONE || currentTokenType == TokenType.TWO ||
				currentTokenType == TokenType.THREE || currentTokenType == TokenType.FOUR || currentTokenType == TokenType.FIVE || 
				currentTokenType == TokenType.SIX || currentTokenType == TokenType.SEVEN || currentTokenType == TokenType.EIGHT ||
						currentTokenType == TokenType.NINE ;
		
		if(!isValidToken) {
			throw new Error("Expected a digit.");
		}
		
		currentToken = scanner.getNextToken();
		
		return null;
	}
	

	private ASTNode nonzeroDigit() {
		
		System.out.println("NonZeroDigit -> ONE|TWO|THREE|..|NINE");
		
		TokenType currentTokenType = currentToken.getTokenType() ;
		boolean isValidToken = currentTokenType == TokenType.ONE || currentTokenType == TokenType.TWO ||
				currentTokenType == TokenType.THREE || currentTokenType == TokenType.FOUR || currentTokenType == TokenType.FIVE || 
				currentTokenType == TokenType.SIX || currentTokenType == TokenType.SEVEN || currentTokenType == TokenType.EIGHT ||
						currentTokenType == TokenType.NINE ;
		
		if(!isValidToken) {
			throw new Error("Expected a nonzero digit");
		}
		
		currentToken = scanner.getNextToken();
		
		return null;
	}
	
	
	private ASTNode restOfTheDigits() {
		

		TokenType currentTokenType = currentToken.getTokenType() ;
		boolean isValidToken = currentTokenType == TokenType.ZERO || currentTokenType == TokenType.ONE || currentTokenType == TokenType.TWO ||
				currentTokenType == TokenType.THREE || currentTokenType == TokenType.FOUR || currentTokenType == TokenType.FIVE || 
				currentTokenType == TokenType.SIX || currentTokenType == TokenType.SEVEN || currentTokenType == TokenType.EIGHT ||
						currentTokenType == TokenType.NINE ;
		
		
		if(!isValidToken) {
			System.out.println("RestOfTheDigits -> EPSILON");
		}else {
			
			System.out.println("RestOfTheDigits -> Digit RestOfTheDigits");
			digit();
			restOfTheDigits();
		}
		
		return null;
		
	}
	
	
	
	
	
	private ASTNode leftParen() {
		
		if(currentToken.getTokenType() != TokenType.L_PAREN) {
			throw new Error("Parsing Exception. Expected a left parenthesis");
		}
		
		//proceed further
		currentToken = scanner.getNextToken();
		
		return null;
	}
	
	
	private ASTNode rightParen() {
		
		if(currentToken.getTokenType() != TokenType.R_PAREN) {
			throw new Error("Parsing Exception. Expected a right parenthesis");
		}
		
		//proceed further
		currentToken = scanner.getNextToken();
		
		return null;
	}
	
	
	
}
