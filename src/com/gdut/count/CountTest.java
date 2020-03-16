package com.gdut.count;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.Test;

public class CountTest {

	@Test
	public void testWordsCount() {
		int test = new Count().wordsCount("D:\\Desktop\\test.c");
		assertEquals(7,test);
	}

	@Test
	public void testLinesCount() {
		int test = new Count().linesCount("D:\\Desktop\\test.c");
		assertEquals(5,test);
	}

	@Test
	public void testCharsCount() {
		int test = new Count().charsCount("D:\\Desktop\\test.c");
		assertEquals(18,test);
	}




}
