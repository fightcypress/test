package com.gdut.count;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Count {
	public static void main(String[] args) {
		System.out.println("输入程序参数列表：");
		System.out.println("支持 -c（字符数）");
		System.out.println("支持 -w（单词数）");
		System.out.println("支持 -l（行数）");
		System.out.println("支持 -s（递归处理文件夹）");
		System.out.println("支持 -a（空白注释行）");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));						//用户输入
		String line = null;
		String[] commands = null;																		//输入命令格式：wc.exe -c D:\\Desktop\test.c
		while (true) {
			try {
				if ((line = br.readLine()) != null) {
					commands = line.split(" ");															//空格隔开
					if ("wc.exe".equals(commands[0])) {													//对输入命令进行检查
						System.out.println("运行wc.exe程序");
						if ("-s".equals(commands[1])) {													//递归处理文件
							List<String> s = getfolders(commands[2]);
//							System.out.println(s);
							 System.out.println("请选择计算类型：");	 
							 Scanner scanner = new Scanner(System.in);
							 String command = scanner.nextLine();
							switch (command) {
							case "-c":																		//计算字符数
								for(String name:s) {
									charsCount(name);
								}		
								break;
							case "-w":																		//计算单词数
								for(String name:s) {
									wordsCount(name);
								}	
								break;
							case "-l":																		//计算行数
								for(String name:s) {
									linesCount(name);
								}	
								break;
							case "-a":																		//计算注释空白行数
								for(String name:s) {
									extendCount(name);
								}	
								break;
							}
							
							
							
						}
						switch (commands[1]) {
						case "-c":																		//计算字符数
							charsCount(commands[2]);
							break;
						case "-w":																		//计算单词数
							wordsCount(commands[2]);
							break;
						case "-l":																		//计算行数
							linesCount(commands[2]);
							break;
							/*
							 * case "-s": getfolders(commands[2]);
							 */
						case "-a":																		//计算注释空白行数
							extendCount(commands[2]);
							break;
						}
					}
				}
			} catch (FileNotFoundException e) {															//其余情况都认为文件异常
				System.out.println("文件异常");
			} catch (IOException e) {
				System.out.println("读取失败");
			}
		}

	}

	// 递归获取目录下指定后缀的文件
	public static List<String> getfolders(String filepath) {
		File file = new File(filepath); 																// 创建对象
		File[] folders = file.listFiles();																 // 获取文件夹列表
		List<File> result = new ArrayList<File>();
		List<String> s = new ArrayList<String>();

		if (folders.length == 0) { 																		// 判断文件夹列表(为空)
			return null;
		} else { 																						// 不为空
			for (File f : folders) {
				if (f.isDirectory()) { 																	// 存在子目录（递归文件夹获取）
					getfolders(f.getAbsolutePath());
				} else if (f.getName().endsWith(".c")) {
					result.add(f);
					
				}
			}
		}
		
		  for (File command : result) { 
			  System.out.println(command);
			  s.add(command.getAbsolutePath());
			  }
		  return s;
	}

	// 计算单词个数
	public static int wordsCount(String filepath) {
		int num = 0;
		File file = new File(filepath);
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(file));												//读取文件
			String line = "";
			while ((line = bf.readLine()) != null) {
				String[] wordline = line.split("\\W+");													//空格隔开单词
				for (String word : wordline) {
					if(!word.equals(""))																//空行单词数不添加
					num++;																				//遍历计算单词个数
				}
			}
			bf.close();																					//关闭流
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("文件异常！");
		}
		System.out.println("文件："+filepath+" 单词数：" + num);
		return num;
	}

	//计算文件行数
	public static int linesCount(String filepath) {
		int num = 0;
		File file = new File(filepath);
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(file));												//读取文件
			while (bf.readLine() != null) {
				num++;																					//计算行数
			}
			bf.close();																					//关闭流
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("文件异常！");
		}
		System.out.println("文件："+filepath+" 行数：" + num);
		return num;
	}

			//计算文件字符数
	public static int charsCount(String filepath) {
		int num = 0;
		File file = new File(filepath);
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(file));												//读取文件
			String line1, line2;
			while ((line1 = bf.readLine()) != null) {
				line2 = line1.replaceAll("\\s+", "");													//将空白符替换掉（删去）
				num = num + line2.length();
			}
			bf.close();																					//关闭流
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("文件异常！");
		}
		System.out.println("文件："+filepath+" 字符数：" + num);
		return num;
	}
	
	//计算空白注释行数
	public static void extendCount(String filepath) {
		int emptyCount = 0;
		int commentCount = 0;
		int codeCount = 0;
		File file = new File(filepath);
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(file));
			String line;
			while((line=bf.readLine())!=null) {
				if(line.length()==0||line.length()==1) {
					emptyCount++;
				}else if(line.contains("//")) {
					commentCount++;
				}else {
					codeCount++;
				}
			}
			bf.close();
			System.out.println("文件："+filepath+" 空白行数："+emptyCount);
			System.out.println("文件："+filepath+" 注释行数："+commentCount);
			System.out.println("文件："+filepath+" 代码行数："+codeCount);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}

}
