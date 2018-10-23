package com.chetan.parsing.ast;

import static guru.nidi.graphviz.model.Factory.*;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

import com.chetan.parsing.Token;
import com.chetan.parsing.TokenType;

import guru.nidi.graphviz.attribute.MapAttributes;
import guru.nidi.graphviz.attribute.RankDir;
import guru.nidi.graphviz.attribute.Records;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.model.Node;
import guru.nidi.graphviz.model.Serializer;
import static guru.nidi.graphviz.attribute.Records.*;
import static guru.nidi.graphviz.model.Compass.*;

public class TestExpressionEvaluator {

	/*
	 * The goal is to write sample test code which takes in AST as input - walks
	 * through it and returns the evaluation results.
	 */

	public static void main(String[] args) {
		// lets construct AST manually for the expr: 3+(2x6)

		ASTNode node3 = new IntegerNode();
		node3.setToken(new Token(TokenType.INTEGER, "3"));

		ASTNode node2 = new IntegerNode();
		node2.setToken(new Token(TokenType.INTEGER, "2"));

		ASTNode node5 = new IntegerNode();
		node5.setToken(new Token(TokenType.INTEGER, "6"));

		ASTNode multNode = new BinaryOperatorNode();
		multNode.setToken(new Token(TokenType.MULTIPLY, "x"));
		multNode.setLeftChild(node2);
		node2.setParent(multNode);
		multNode.setRightChild(node5);
		node5.setParent(multNode);

		ASTNode addNode = new BinaryOperatorNode();
		addNode.setToken(new Token(TokenType.PLUS, "+"));
		addNode.setLeftChild(node3);
		node3.setParent(addNode);
		addNode.setRightChild(multNode);
		multNode.setParent(addNode);

		// we have built the AST..now evaluate it
		Integer result = addNode.evaluate();
		System.out.println("evaluated expr, result is " + result);

		/* This requires that graphviz executable is in the Windows PATH.. */
		MutableGraph g = mutGraph("ast-tree");
		g.setDirected(true);

		g = addASTNodeToGraph(addNode, g);

		try {
			Graphviz.fromGraph(g).width(400).height(600).render(Format.PNG).toFile(new File("E:\\ast.png"));
			// System.out.println(serializer.serialize());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TestGraphGen();
	}

	private static void TestGraphGen() {
		Node node0 = node("node0")
				.with(Records.of(rec("f0", ""), rec("f1", ""), rec("f2", ""), rec("f3", ""), rec("f4", ""))),
				node1 = node("node1").with(Records.of(turn(rec("n4"), rec("v", "719"), rec("")))),
				node2 = node("node2").with(Records.of(turn(rec("a1"), rec("805"), rec("p", "")))),
				node3 = node("node3").with(Records.of(turn(rec("i9"), rec("718"), rec("")))),
				node4 = node("node4").with(Records.of(turn(rec("e5"), rec("989"), rec("p", "")))),
				node5 = node("node5").with(Records.of(turn(rec("t2"), rec("v", "959"), rec("")))),
				node6 = node("node6").with(Records.of(turn(rec("o1"), rec("794"), rec("")))),
				node7 = node("node7").with(Records.of(turn(rec("s7"), rec("659"), rec(""))));
		
		Graph g = graph("example3").directed().graphAttr().with(RankDir.LEFT_TO_RIGHT)
				.with(node0.link(between(port("f0"), node1.port("v", SOUTH)), between(port("f1"), node2.port(WEST)),
						between(port("f2"), node3.port(WEST)), between(port("f3"), node4.port(WEST)),
						between(port("f4"), node5.port("v", NORTH))),
						node2.link(between(port("p"), node6.port(NORTH_WEST))),
						node4.link(between(port("p"), node7.port(SOUTH_WEST))));
		try {
			Graphviz.fromGraph(g).width(900).render(Format.PNG).toFile(new File("E:\\ex3.png"));
			Serializer serializer = new Serializer((MutableGraph)g);
			System.out.println(serializer.serialize());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static MutableNode getAlreadyExistingNode(MutableGraph graph, MutableNode otherNode) {

		for (MutableNode mNode : graph.nodes()) {

			if (mNode.equals(otherNode)) {
				System.out.println("found other node " + otherNode);
				return mNode;

			}
		}

		return null;

	}

	/* we are just walking over the AST nodes. we are not modifying AST tree */
	public static MutableGraph addASTNodeToGraph(ASTNode astNode, MutableGraph graph) {

		if (astNode == null) {
			return graph;
		}

		graph = addASTNodeToGraph(astNode.getLeftChild(), graph);

		if (astNode.getParent() != null) {

			MutableNode currNode = (MutableNode) node(astNode.getValue());
			currNode.add("label", astNode.getValue());
			currNode.add("shape", "record");

			MutableNode tempNode = getAlreadyExistingNode(graph, currNode);
			if (tempNode == null) {
				graph.add(currNode);
			} else {
				currNode = tempNode;
			}

			MutableNode parNode = (MutableNode) node(astNode.getParent().getValue());

			parNode.add("label", astNode.getParent().getValue());
			parNode.add("shape", "record");
			MutableNode tempNode1 = getAlreadyExistingNode(graph, parNode);
			if (tempNode1 == null) {
				graph.add(parNode);

				parNode.addLink(astNode.getValue());

			} else {
				parNode = tempNode1;
				// graph.add(parNode);
				parNode.addLink(astNode.getValue());

				// graph.addLink(currNode.linkTo());

			}

		} else {
			// means this is the root node..
			MutableNode currNode = (MutableNode) node(astNode.getValue());
			currNode.add("label", astNode.getValue());
			currNode.add("shape", "record");

			if (getAlreadyExistingNode(graph, currNode) == null) {
				graph.add(currNode);

			}

		}

		graph = addASTNodeToGraph(astNode.getRightChild(), graph);

		Serializer serializer = new Serializer(graph);
		System.out.println(serializer.serialize());

		return graph;
	}

}
