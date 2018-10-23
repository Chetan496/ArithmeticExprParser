package com.chetan.parsing;

import java.util.Iterator;
import java.util.stream.Stream;

/*This is going to navigate the given input string.
 * It keeps track of the current char from input string which is being navigated. */


public class Scanner {

	
	private String stringToParse ; 
	private Stream<Character> characterStream ;
	private Iterator<Character> charIterator;
	
	
	public Scanner(String stringToParse) {
		super();
		this.stringToParse = stringToParse.replaceAll("\\s+","");  /*we are replacing all whitespaces .. consider this as preprocessing */
		this.characterStream = this.stringToParse.chars()
				  .mapToObj(c -> (char) c);
		
		
		this.charIterator = characterStream.iterator();
		
	}


	public Token getNextToken() {
		if(!charIterator.hasNext()) {
			
			return new Token(TokenType.EPSILON, null);
		}
		
		final Character nextCharacter = charIterator.next();
		final Token token = determineTokenTypeForChar(nextCharacter);
		return token;
	}
	
	

	
	private Token determineTokenTypeForChar(Character c) {
		
		TokenType tokenType ;
		if(c.equals('0')) {
			tokenType = TokenType.ZERO;
		}else if(c.equals('1')) {
			tokenType = TokenType.ONE;	
		}else if(c.equals('2')) {
			tokenType = TokenType.TWO;	
		}else if(c.equals('3')) {
			tokenType = TokenType.THREE;	
		}else if(c.equals('4')) {
			tokenType = TokenType.FOUR;	
		}else if(c.equals('5')) {
			tokenType = TokenType.FIVE;	
		}else if(c.equals('6')) {
			tokenType = TokenType.SIX;	
		}else if(c.equals('7')) {
			tokenType = TokenType.SEVEN;	
		}else if(c.equals('8')) {
			tokenType = TokenType.EIGHT;	
		}else if(c.equals('9')) {
			tokenType = TokenType.NINE;	
		}else if(c.equals('+')) {
			tokenType = TokenType.PLUS;	
		}else if(c.equals('-')) {
			tokenType = TokenType.MINUS;	
		}else if(c.equals('x')) {
			tokenType = TokenType.MULTIPLY;	
		}else if(c.equals('/')) {
			tokenType = TokenType.DIVIDE;	
		}else if(c.equals('(')) {
			tokenType = TokenType.L_PAREN;	
		}else if(c.equals(')')) {
			tokenType = TokenType.R_PAREN;	
		}else {
			throw new IllegalArgumentException("The character "+c+" is not valid.");
		}
		
		Token token = new Token(tokenType, c.toString() ) ;
		
		
		return token;
		
	}
	
	
	
	
}
